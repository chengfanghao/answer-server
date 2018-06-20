$(document).ready(function () {
    config = {
        baseUrl: "http://172.16.30.73:8080",
        postPath: {
            "选择题": "singleChoice",
            "填空题": "blank",
            "判断题": "judge",
            "问答题": "essay"
        },
        pageCount: 0,
        paperInfo: {
            paperName: "",
            time: 120
        },
        questionType: {
            name: "选择",
            url: "singleChoice"
        },
        pageCount: -1,
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
    $('#loginOut').click(function () {
        $.get((config.baseUrl + "/loginOut"), function (data, status) {
            window.location.href = "login.html";
            sessionStorage.clear();
        });
    });
});