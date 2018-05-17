$(document).ready(function () {
    var config = {
        questionType: {
            name: "选择",
            url: "singleChoice"
        },
        pageCount: -1,
        baseUrl: "http://localhost:8080",
        questionTypeUrl: {
            "选择": "singleChoice",
            "填空": "blank",
            "判断": "judge",
            "问答": "essay"
        },
        questionDetail: {
            "questionLevel": "试题难度",
            "testSubject": "试题科目",
            "questionSource": "试题来源",
            "questionStatus": "试题状态",
            "grade": "年级",
            "subjectPersonId": "出题人",
            "titleName": "试题题干",
            "blankAnswer": "试题答案",
            "questionAnalysis": "试题解析",
            "judgeAnswer": "试题答案",
            "choiceAnswer": "试题答案",
            "choiceA": "A",
            "choiceB": "B",
            "choiceC": "C",
            "choiceD": "D"
        }
    };

    var teacher = null;
    //设置登录信息
    if (!sessionStorage.teacher) {
        $.get(config.baseUrl + '/teacher/getUser', function (data, status) {
            sessionStorage.teacher = JSON.stringify(data);
            teacher = JSON.parse(sessionStorage.teacher);
            $('#userName').html(teacher.name);
        });
    } else {
        teacher = JSON.parse(sessionStorage.teacher);
        $('#userName').html(teacher.name);
    }

    //获取localstorage对象
    function getLocalStorage() {
        if (typeof localStorage == "object") {
            return localStorage;
        } else if (typeof globalStorage == "object") {
            return globalStorage[location.host];
        } else {
            throw new Error("Local storage not available.");
        }
    }

    //初始化清空本地缓存
    var storage = getLocalStorage();
    storage.clear();

    //获取某页数据
    function getPageData(data) {
        console.log(data);
        var dataArr = data.content;
        var contentHtml = ``;
        for (var index in dataArr) {
            var dataItem = dataArr[index];
            contentHtml += `
                <tr>
                    <td>${config.questionType.name}</td>
                    <td>${dataItem.questionLevel}</td>
                    <td class="question-title" title="${dataItem.titleName}">${dataItem.titleName}</td>
                    <td>
                        <input type="checkbox" data-id="${dataItem.id}" data-type="${config.questionType.name}">&nbsp;&nbsp;&nbsp;
                        <a class="question-info" data-id="${dataItem.id}" data-toggle="modal" data-target="#questionDetailModal">
                            查看
                        </a>
                    </td>
                </tr>`;
        }
        $("#questionContent").html(contentHtml);

        //查看试题详情
        $(".question-info").click(function () {
            var id = $(this).data("id");
            $.get(`${config.baseUrl}/${config.questionType.url}/getById/${id}`, function (data, status) {
                console.dir(data);
                var innerHtml = '',
                    firstEle = true;
                for (var key in data) {
                    if (firstEle) {
                        firstEle = false;
                        continue;
                    }

                    innerHtml += `<tr><td>${config.questionDetail[key]}：&nbsp;&nbsp;</td><td>${data[key]}</td></tr>`;
                }
                $("#questionDetailContent").html(innerHtml);
            });
        });
    }

    function writeLocalStorage() {
        //获取所有的checkbox，添加选择的，删除未选择的
        $.each($('input:checkbox'), function () {
            if (!$(this).prop("checked") && storage.getItem($(this).data("id"))) {
                storage.removeItem($(this).data("id"));
            }

            if ($(this).prop("checked") && !storage.getItem($(this).data("id"))) {
                storage.setItem($(this).data("id"), $(this).data("type"));
            }
        });
    }

    function readLocalStorage() {
        //判断是否选择过
        $.each($('input:checkbox'), function () {
            if (storage.getItem($(this).data("id"))) {
                $(this).prop("checked", true);
            }
        });
    }

    //获取选择题的数量，生成页码
    $.get(`${config.baseUrl}/${config.questionType.url}/getCount`, function (data, status) {
        console.log(data);
        config.pageCount = Math.ceil(data / 7);

        for (var i = config.pageCount - 1; i >= 0; i--) {
            if (i == 0) {
                $("#previous").after(`<li id="firstPageControl" class="active page-control"><a href="javascript:;">${i + 1}</a></li>`);
            } else {
                $("#previous").after(`<li class="page-control"><a href="javascript:;">${i + 1}</a></li>`);
            }
        }

        //为每个页码绑定请求事件
        $(".page-control").click(function () {
            //首选缓存本页选择数据
            writeLocalStorage();

            var pageNumber = $(this).text() - 1;
            $("#pageControl li.active").removeClass("active");
            $(this).addClass("active");
            //清空页面数据
            $("#questionContent").html("");
            //默认获取该页数据，生成信息
            $.get(`${config.baseUrl}/${config.questionType.url}/getPage${pageNumber}`, function (data, status) {
                getPageData(data);

                readLocalStorage();
            });

        });

        //回到首页
        $("#first").click(function () {
            writeLocalStorage();

            var pageNumber = $(".active.page-control").children().text() - 1;
            if (pageNumber > 0) {
                $("#pageControl li.active").removeClass("active");
                $(this).next().next().addClass("active");
                //清空页面数据
                $("#questionContent").html("");
                $.get(`${config.baseUrl}/${config.questionType.url}/getPage0`, function (data, status) {
                    getPageData(data);
                });
            }

            readLocalStorage();
        });

        //转到尾页
        $("#last").click(function () {
            writeLocalStorage();

            var pageNumber = $(".active.page-control").children().text() - 1;
            if (pageNumber < config.pageCount - 1) {
                $("#pageControl li.active").removeClass("active");
                $(this).prev().prev().addClass("active");
                //清空页面数据
                $("#questionContent").html("");
                $.get(`${config.baseUrl}/${config.questionType.url}/getPage${config.pageCount - 1}`, function (data, status) {
                    getPageData(data);
                });
            }

            readLocalStorage();
        });

        //跳转到下一页
        $("#next").click(function () {
            writeLocalStorage();

            var pageNumber = $(".active.page-control").children().text() - 1;
            if (pageNumber < config.pageCount - 1) {
                var currentActivePageControl = $("#pageControl li.active");
                currentActivePageControl.removeClass("active");
                currentActivePageControl.next().addClass("active");
                //清空页面数据
                $("#questionContent").html("");
                $.get(`${config.baseUrl}/${config.questionType.url}/getPage${pageNumber + 1}`, function (data, status) {
                    getPageData(data);
                });
            }

            readLocalStorage();
        });

        //跳转到上一页
        $("#previous").click(function () {
            writeLocalStorage();

            var pageNumber = $(".active.page-control").children().text() - 1;
            if (pageNumber > 0) {
                var currentActivePageControl = $("#pageControl li.active");
                currentActivePageControl.removeClass("active");
                currentActivePageControl.prev().addClass("active");
                //清空页面数据
                $("#questionContent").html("");
                $.get(`${config.baseUrl}/${config.questionType.url}/getPage${pageNumber - 1}`, function (data, status) {
                    getPageData(data);
                });
            }

            readLocalStorage();
        });

    });

    //默认获取首页数据，生成信息
    $.get(`${config.baseUrl}/${config.questionType.url}/getPage0`, function (data, status) {
        getPageData(data);
    });

    //为表格中题目类型下拉框绑定事件
    $("#questionType").change(function () {
        writeLocalStorage();

        config.questionType.name = $(this).val();
        config.questionType.url = config.questionTypeUrl[$(this).val()];
        //更新页码
        $(".page-control").remove();
        $.get(`${config.baseUrl}/${config.questionType.url}/getCount`, function (data, status) {
            console.log(data);
            config.pageCount = Math.ceil(data / 7);
            for (var i = config.pageCount - 1; i >= 0; i--) {
                if (i == 0) {
                    $("#previous").after(`<li id="firstPageControl" class="active page-control"><a href="javascript:;">${i + 1}</a></li>`);
                } else {
                    $("#previous").after(`<li class="page-control"><a href="javascript:;">${i + 1}</a></li>`);
                }
            }

            //为每个页码绑定请求事件
            $(".page-control").click(function () {
                var pageNumber = $(this).text() - 1;
                $("#pageControl li.active").removeClass("active");
                $(this).addClass("active");
                //清空页面数据
                $("#questionContent").html("");
                //默认获取该页数据，生成信息
                $.get(`${config.baseUrl}/${config.questionType.url}/getPage${pageNumber}`, function (data, status) {
                    console.log(`${config.baseUrl}/${config.questionType.url}/getPage${pageNumber}`);
                    getPageData(data);

                    readLocalStorage();
                });
            });

            //加载相应题目类型的首页
            $("#firstPageControl").click();
        });

    });

    //初始化菜单
    $('.navMenu li a').on('click', function () {
        //获取当前页签的父级的父级
        var parent = $(this).parent().parent();
        var labeUl = $(this).parent("li").find(">ul");
        if ($(this).parent().hasClass('open') == false) {
            //展开未展开
            //parent.find('ul').slideUp(300);
            //parent.find("li").removeClass("open");
            //parent.find('li a').removeClass("active").find(".arrow").removeClass("open");
            $(this).parent("li").addClass("open").find(labeUl).slideDown(300);
            $(this).addClass("active").find(".arrow").addClass("open");
        } else {
            $(this).parent("li").removeClass("open").find(labeUl).slideUp(300);
            if ($(this).parent().find("ul").length > 0) {
                $(this).removeClass("active").find(".arrow").removeClass("open");
            } else {
                $(this).addClass("active");
            }
        }
    });

    //点击生成试卷
    $("#genernateTest").click(function () {
        writeLocalStorage();
        //持久化缓存到数据库
        var examinationPaper = [],
            examinationName = $("#examinationName").val(),
            examinationTime = $("#examinationTime").val();

        for (var i = 0, len = storage.length; i < len; i++) {
            var question = {};
            var key = storage.key(i);
            question.id = key;
            question.questionType = storage.getItem(key);
            question.examinationName = examinationName;
            question.examinationTime = examinationTime;
            question.subject = teacher.subject;
            question.grade = teacher.grade;
            examinationPaper.push(question);
        }

        $.ajax({
            type: "POST",
            url: config.baseUrl + "/examinationPaper/saveExaminationPaper",
            contentType: "application/json",
            data: JSON.stringify(examinationPaper)
        });

        //清空缓存
        $('input:checkbox').prop("checked", false);
        storage.clear();

        alert("生成试卷成功！");
    });

    //点击生成定制化试卷
    $("#generateCustomTest").click(function () {
        //alert($("#selectLevel1").val());
        var customTest = {};
        customTest.examinationName = $("#examinationName").val();
        customTest.examinationTime = $("#examinationTime").val();

        customTest.selectLevel1 = parseInt($("#selectLevel1").val());
        customTest.selectLevel2 = parseInt($("#selectLevel2").val());
        customTest.selectLevel3 = parseInt($("#selectLevel3").val());

        customTest.blankLevel1 = parseInt($("#blankLevel1").val());
        customTest.blankLevel2 = parseInt($("#blankLevel2").val());
        customTest.blankLevel3 = parseInt($("#blankLevel3").val());

        customTest.judgeLevel1 = parseInt($("#judgeLevel1").val());
        customTest.judgeLevel2 = parseInt($("#judgeLevel2").val());
        customTest.judgeLevel3 = parseInt($("#judgeLevel3").val());

        customTest.essayLevel1 = parseInt($("#essayLevel1").val());
        customTest.essayLevel2 = parseInt($("#essayLevel2").val());
        customTest.essayLevel3 = parseInt($("#essayLevel3").val());

        $.ajax({
            type: "POST",
            url: config.baseUrl + "/examinationPaper/saveCustomPaper",
            contentType: "application/json",
            data: JSON.stringify(customTest)
        });

        alert("生成试卷成功！");
    });
});
