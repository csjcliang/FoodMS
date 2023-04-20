function $(obj){return document.getElementById(obj);}
function register_user() {
    var re_username = document.getElementById("re_username").value;
    var re_password = document.getElementById("re_password").value;
    var re_password2 = document.getElementById("re_password2").value;
    var re_identity = document.getElementById("select_identity").value;
    if(re_username==""){
        document.getElementById("wrong_register").innerText="请输入用户名";
        return 0;
    }
    if(re_password==""&&re_password2==""){
        document.getElementById("wrong_register").innerText="请输入密码";
        return 0;
    }
    $.getJSON("/toRegister",{re_username:re_username,re_password:re_password,re_password2:re_password2,re_identity:re_identity},function (result) {
        flag(result);
    });
}
function flag(result) {
    var state = result.stat;
    state.toString();
    if(state=="register_ok") {
        document.getElementById("wrong_register").innerText="注册成功";
    }
    else{
        document.getElementById("wrong_register").innerText="两次密码不相同";
    }
}