$(function() {
    $(".content .con_right .left").click(function(e) {
        $(this).css({
            "color": "#333333",
            "border-bottom": "2px solid #2e558e"
        });
        $(".content .con_right .right").css({
            "color": "#999999",
            "border-bottom": "2px solid #dedede"
        });
        $(".content .con_right ul .con_r_left").css("display", "block");
        $(".content .con_right ul .con_r_right").css("display", "none");
    });
    $(".content .con_right .right").click(function(e) {
        $(this).css({
            "color": "#333333",
            "border-bottom": "2px solid #2e558e"
        });
        $(".content .con_right .left").css({
            "color": "#999999",
            "border-bottom": "2px solid #dedede"
        });
        $(".content .con_right ul .con_r_right").css("display", "block");
        $(".content .con_right ul .con_r_left").css("display", "none");
    });
    $('#teacherLogin').click(function() {
        var teacher = {
            id: $('#tid').val(),
            password: hex_md5($('#tpwd').val())
        };
        $.ajax({
            url: (config.baseUrl + "/teacher/login"),
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(teacher),
            dataType: "text",
            error: function() {
                console.log("失败");
            },
            complete: function() {
                console.log("完成");
            },
            success: function(data) {
                if (data == 'fail') {
                    alert("您输入用户名/密码不正确，请重新输入！");
                } else {
                    window.location.href = (data + ".html");
                }
            }
        });
    });

    $('#managerLogin').click(function() {
        var manager = {
            id: $('#mid').val(),
            password: hex_md5($('#mpwd').val())
        };
        $.ajax({
            url: (config.baseUrl + "/manager/login"),
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(manager),
            dataType: "text",
            error: function() {
                console.log("失败");
            },
            complete: function() {
                console.log("完成");
            },
            success: function(data) {
                if (data == 'fail') {
                    alert("您输入用户名/密码不正确，请重新输入！");
                } else {
                    window.location.href = (data + ".html");
                }
            }
        });
    });
});