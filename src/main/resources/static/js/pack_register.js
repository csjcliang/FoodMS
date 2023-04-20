function $(obj){return document.getElementById(obj);}
function show(objid) {document.getElementById(objid).style.display='block';}
function hidden(objid) {document.getElementById(objid).style.display='none';}
function select_pk(){
    var sel_val=document.getElementById('select_pk_index').value;
    if (sel_val==="") {hidden('pk_delete1');hidden('pk_delete2');hidden('pk_delete3');}
    if (sel_val==="pk_id") {show('pk_delete1');hidden('pk_delete2');hidden('pk_delete3');}
    if (sel_val==="pk_name") {hidden('pk_delete1');show('pk_delete2');hidden('pk_delete3');}
    if (sel_val==="pk_staff") {hidden('pk_delete1');hidden('pk_delete2');show('pk_delete3');}
}
function select_pk2(){
    var sel_val2=document.getElementById('select_pk_index2').value;
    if (sel_val2==="") {hidden('pk_query1');hidden('pk_query2');hidden('pk_query3');hidden('pk_query4');}
    if (sel_val2==="pk_id2") {show('pk_query1');hidden('pk_query2');hidden('pk_query3');hidden('pk_query4');}
    if (sel_val2==="pk_name2") {hidden('pk_query1');show('pk_query2');hidden('pk_query3');hidden('pk_query4');}
    if (sel_val2==="pk_qualified2") {hidden('pk_query1');hidden('pk_query2');show('pk_query3');hidden('pk_query4');}
    if (sel_val2==="pk_staff2") {hidden('pk_query1');hidden('pk_query2');hidden('pk_query3');show('pk_query4');}
}
//增加记录的提示
function add_tips() {
    var identity = document.getElementById("idid").innerText;
    if(identity=="material registrar"||identity=="additive registrar"){
        alert("你不能增加该模块的记录!");
        return 0;
    }
    var tmn=document.getElementById("add_pk_name").value;
    var tmc=document.getElementById("add_pk_check").value;
    var smq=document.getElementById("select_pk_qualified").value;
    if(tmn==""||tmc==""||smq==""){
        alert("请输入完整信息");
        return 0;
    }
    $.getJSON("/pk_add",{type_pk_name:tmn,type_pk_check:tmc,select_pk_qualified:smq},function (result) {
        addflag(result);
    });
}
function addflag(result) {
    var state = result.stat;
    state.toString();
    if(state=="add_ok"){
        alert("增加成功");
    }
    else{
        alert("增加失败");
        return 0;
    }
}
//删除记录的提示
function delete_tips() {
    var identity = document.getElementById("idid").innerText;
    if(identity=="material registrar"||identity=="additive registrar"){
        alert("你不能删除该模块的记录!");
        return 0;
    }
    var smi=document.getElementById("select_pk_index").value;
    var md1=document.getElementById("pk_delete1").value;
    var md2=document.getElementById("pk_delete2").value;
    var md3=document.getElementById("pk_delete3").value;
    if(smi==""){
        alert("请选择索引");
        return 0;
    }
    else if(md1==""&&md2==""&&md3==""){
        alert("请输入信息");
        return 0;
    }
    $.getJSON("/pk_delete",{select_pk_index:smi,pk_delete1:md1,pk_delete2:md2,pk_delete3:md3},function (result) {
        deleteflag(result);
    });
}
function deleteflag(result) {
    var state = result.stat;
    state.toString();
    if(state=="delete_ok"){
        alert("删除成功");
    }
    else{
        alert("删除失败");
        return 0;
    }
}
//修改前查询的提示
function check_tips() {
    var identity = document.getElementById("idid").innerText;
    if(identity=="material registrar"||identity=="additive registrar"){
        alert("你不能修改该模块的记录!");
        return 0;
    }
    var umi = document.getElementById("update_pk_id").value;
    //console.log(umi);
    if(umi==""){
        alert("请输入序号");
        return 0;
    }
    $.getJSON("/pk_update",{UMI:umi},function (result) {
        updateflag(result);
    });
}
//修改的提示
function update_tips() {
    var identity = document.getElementById("idid").innerText;
    if(identity=="material registrar"||identity=="additive registrar"){
        alert("你不能修改该模块的记录!");
        return 0;
    }
    var pk1 = document.getElementById("update_pk_id").value;
    var pk2 = document.getElementById("update_pk_name").value;
    var pk3 = document.getElementById("update_pk_check").value;
    var pk4 = document.getElementById("update_pk_qualified").value;
    var pk5 = document.getElementById("update_pk_staff").value;
    //console.log(pk1,pk2,pk3,pk4,pk5);
    $.getJSON("/pk_update",{get_pk_id:pk1,update_pk_name:pk2,update_pk_check:pk3,update_pk_qualified:pk4,update_pk_staff:pk5,UMI:null},function (result) {
        updateflag(result);
    });
}
function updateflag(result) {
    var state = result.stat;
    state.toString();
    if(state=="update_blank")
    {
        alert("请输入序号");
        return 0;
    }
    else if(state=="update_wrong")
    {
        alert("查询不到数据");
        return 0;
    }
    else if(state=="check_ok"){
        alert("查找到记录");
        hidden("please_input")
        hidden("update_pk_id");
        hidden("pk_update_query");
        //window.location.href="pack_register";
        show("pk_update_submit");
        show("update_show_id");
        show("update_pk_name");
        show("update_pk_check");
        show("update_pk_qualified");
        show("update_pk_staff");
        document.getElementById("update_pk_name").value = result.update_pkname;
        document.getElementById("update_pk_check").value = result.update_pkcheck;
        document.getElementById("update_pk_qualified").value = result.update_pkqualified;
        document.getElementById("update_pk_staff").value = result.update_pkstaff;
        document.getElementById("update_show_id").innerText = "查找到序号"+result.update_pkid+"的记录如下";
        document.getElementById("update_pk_id").value = result.update_pkid;
    }
    else if(state=="update_ok"){
        alert("修改成功");
        hidden("update_contents");
        window.location.href="pack_register";
    }
}
function query_tips(){
    var smi2=document.getElementById("select_pk_index2").value;
    var mq1=document.getElementById("pk_query1").value;
    var mq2=document.getElementById("pk_query2").value;
    var mq3=document.getElementById("pk_query3").value;
    var mq4=document.getElementById("pk_query4").value;
    if(smi2==""){
        alert("请选择索引");
        return 0;
    }
    else if(mq1==""&&mq2==""&&mq3==""&&mq4==""){
        alert("请输入信息");
        return 0;
    }
    $.getJSON("/pk_query",{select_pk_index2:smi2,pk_query1:mq1,pk_query2:mq2,pk_query3:mq3,pk_query4:mq4},function (result) {
        queryflag(result);
    });
}
function queryflag(result){
    var state = result.stat;
    state.toString();
    if(state=="query_wrong"){
        alert("查询不到数据");
        return 0;
    }else{
        window.location.href="pack_register";
    }
}
function log_out() { //登出，清空所有session值并返回登录页面
    sessionStorage.clear();
    window.location.href="login";
}