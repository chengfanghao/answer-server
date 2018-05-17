$(document).ready(function () {
    var config = {
        baseUrl: "http://localhost:8080"
    };

    //注销
    $('#loginOut').click(function () {
        $.get(`${config.baseUrl}/loginOut`, function (data, status) {
            window.location.href = `login.html`;
            sessionStorage.clear();
        });
    });
});