function $(obj){return document.getElementById(obj);}
function check_user() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    $.getJSON("/loginVerify",{username:username,password:password},function (result) {
        flag(result);
    });
}
function flag(result) {
    var state = result.stat;
    state.toString();
    if(state=="login_ok") {
        window.location.href="welcome";
    }
    else{
        document.getElementById("wrong_login").innerText="用户名或密码错误";
    }
}