$(document).ready(function () {
    var config = {
        baseUrl: "http://localhost:8080",
        pageCount: 0
    };

    var manager = null;
    //设置登录信息
    if (!sessionStorage.manager) {
        $.get(config.baseUrl + '/manager/getUser', function (data, status) {
            sessionStorage.manager = JSON.stringify(data);
            manager = JSON.parse(sessionStorage.manager);
            $('#userName').html(data.name);
        });
    } else {
        manager = JSON.parse(sessionStorage.manager);
        $('#userName').html(manager.name);
    }

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

    //获取某页数据函数
    function getPageData(data) {
        console.log(data);
        var dataArr = data;
        var contentHtml = ``;
        for (var index in dataArr) {
            var dataItem = dataArr[index];
            contentHtml += `
                <tr>
                    <td>${parseInt(index) + 1}</td>
                    <td>${dataItem.id}</td>
                    <td class="question-title">${dataItem.name}</td>
                    <td>${dataItem.subject}</td>
                    <td>${dataItem.grade}</td>
                    <td>
                        <a class="delete-usr" data-id="${dataItem.id}">
                            删除
                        </a>
                    </td>
                </tr>`;
        }
        $("#userContent").html(contentHtml);

        //为记录绑定点击删除事件
        $('.delete-usr').click(function () {
            var deleteId = $(this).data('id');
            $.get(`${config.baseUrl}/teacher/deleteTeacher/${deleteId}`, function (data, status) {
                location.reload();
            });

        });

    };


    //获取选择题的数量，生成页码
    $.get(`${config.baseUrl}/teacher/getCount`, function (data, status) {
        console.log(data);
        config.pageCount = Math.ceil(data / 7);

        for (var i = config.pageCount - 1; i >= 0; i--) {
            if (i == 0) {
                $("#previous").after(`<li id="firstPageControl" class="active page-control"><a href="javascript:;">${i + 1}</a></li>`);
            } else {
                $("#previous").after(`<li class="page-control"><a href="javascript:;">${i + 1}</a></li>`);
            }
        }

        //默认获取所有数据
        $.get(`${config.baseUrl}/teacher/getTeachers`, function (totalData, status) {
            var data = totalData;

            //显示首页数据
            var start = 0,
                end = 7 > totalData.length ? totalData.length : 7;
            getPageData(data.slice(start, end));

            //为每个页码绑定请求事件
            $(".page-control").click(function () {
                var pageNumber = $(this).text() - 1;
                $("#pageControl li.active").removeClass("active");
                $(this).addClass("active");
                //清空页面数据
                $("#questionContent").html("");
                //默认获取该页数据，生成信息
                var start = pageNumber * 7,
                    end = (pageNumber + 1) * 7 > totalData.length ? totalData.length : (pageNumber + 1) * 7;
                getPageData(data.slice(start, end));
            });

            //回到首页
            $("#first").click(function () {
                var pageNumber = $(".active.page-control").children().text() - 1;
                if (pageNumber > 0) {
                    $("#pageControl li.active").removeClass("active");
                    $(this).next().next().addClass("active");
                    //清空页面数据
                    $("#questionContent").html("");
                    var start = pageNumber * 7,
                        end = (pageNumber + 1) * 7 > totalData.length ? totalData.length : (pageNumber + 1) * 7;
                    getPageData(data.slice(start, end));
                }
            });

            //转到尾页
            $("#last").click(function () {
                var pageNumber = $(".active.page-control").children().text() - 1;
                if (pageNumber < config.pageCount - 1) {
                    $("#pageControl li.active").removeClass("active");
                    $(this).prev().prev().addClass("active");
                    //清空页面数据
                    $("#questionContent").html("");
                    var start = pageNumber * 7,
                        end = (pageNumber + 1) * 7 > totalData.length ? totalData.length : (pageNumber + 1) * 7;
                    getPageData(data.slice(start, end));
                }
            });

            //跳转到下一页
            $("#next").click(function () {
                var pageNumber = $(".active.page-control").children().text() - 1;
                if (pageNumber < config.pageCount - 1) {
                    var currentActivePageControl = $("#pageControl li.active");
                    currentActivePageControl.removeClass("active");
                    currentActivePageControl.next().addClass("active");
                    //清空页面数据
                    $("#questionContent").html("");
                    var start = pageNumber * 7,
                        end = (pageNumber + 1) * 7 > totalData.length ? totalData.length : (pageNumber + 1) * 7;
                    getPageData(data.slice(start, end));
                }
            });

            //跳转到上一页
            $("#previous").click(function () {
                var pageNumber = $(".active.page-control").children().text() - 1;
                if (pageNumber > 0) {
                    var currentActivePageControl = $("#pageControl li.active");
                    currentActivePageControl.removeClass("active");
                    currentActivePageControl.prev().addClass("active");
                    //清空页面数据
                    $("#questionContent").html("");
                    var start = pageNumber * 7,
                        end = (pageNumber + 1) * 7 > totalData.length ? totalData.length : (pageNumber + 1) * 7;
                    getPageData(data.slice(start, end));
                }
            });
        });
    });

    //提交新教师信息
    $("#teacherSubmit").click(function () {
        var teacher = {
            id: $('#id').val(),
            name: $('#name').val(),
            grade: $('#grade').val(),
            subject: $('#subject').val()
        };

        $.ajax({
            url: `${config.baseUrl}/teacher/add`,
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(teacher),
            dataType: "text",
            beforeSend: function () {
                console.dir(teacher);
            },
            error: function () {
                console.log("失败");
            },
            complete: function () {
                console.log("完成");
            },
            success: function (data) {
                alert("录入成功");
            }
        });
    });

});
