function $(obj){return document.getElementById(obj);}
function show(objid) {document.getElementById(objid).style.display='block';}
function hidden(objid) {document.getElementById(objid).style.display='none';}
function select_mt(){
    var sel_val=document.getElementById('select_mt_index').value;
    if (sel_val==="") {hidden('mt_delete1');hidden('mt_delete2');hidden('mt_delete3');}
    if (sel_val==="mt_id") {show('mt_delete1');hidden('mt_delete2');hidden('mt_delete3');}
    if (sel_val==="mt_name") {hidden('mt_delete1');show('mt_delete2');hidden('mt_delete3');}
    if (sel_val==="mt_staff") {hidden('mt_delete1');hidden('mt_delete2');show('mt_delete3');}
}
function select_mt2(){
    var sel_val2=document.getElementById('select_mt_index2').value;
    if (sel_val2==="") {hidden('mt_query1');hidden('mt_query2');hidden('mt_query3');hidden('mt_query4');}
    if (sel_val2==="mt_id2") {show('mt_query1');hidden('mt_query2');hidden('mt_query3');hidden('mt_query4');}
    if (sel_val2==="mt_name2") {hidden('mt_query1');show('mt_query2');hidden('mt_query3');hidden('mt_query4');}
    if (sel_val2==="mt_qualified2") {hidden('mt_query1');hidden('mt_query2');show('mt_query3');hidden('mt_query4');}
    if (sel_val2==="mt_staff2") {hidden('mt_query1');hidden('mt_query2');hidden('mt_query3');show('mt_query4');}
}
//增加记录的提示
function add_tips() {
    var identity = document.getElementById("idid").innerText;
    if(identity=="additive registrar"||identity=="pack registrar"){
        alert("你不能增加该模块的记录!");
        return 0;
    }
    var tmn=document.getElementById("add_mt_name").value;
    var tmc=document.getElementById("add_mt_check").value;
    var smq=document.getElementById("select_mt_qualified").value;
    if(tmn==""||tmc==""||smq==""){
        alert("请输入完整信息");
        return 0;
    }
    $.getJSON("/mt_add",{type_mt_name:tmn,type_mt_check:tmc,select_mt_qualified:smq},function (result) {
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
    if(identity=="additive registrar"||identity=="pack registrar"){
        alert("你不能删除该模块的记录!");
        return 0;
    }
    var smi=document.getElementById("select_mt_index").value;
    var md1=document.getElementById("mt_delete1").value;
    var md2=document.getElementById("mt_delete2").value;
    var md3=document.getElementById("mt_delete3").value;
    if(smi==""){
        alert("请选择索引");
        return 0;
    }
    else if(md1==""&&md2==""&&md3==""){
        alert("请输入信息");
        return 0;
    }
    $.getJSON("/mt_delete",{select_mt_index:smi,mt_delete1:md1,mt_delete2:md2,mt_delete3:md3},function (result) {
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
    if(identity=="additive registrar"||identity=="pack registrar"){
        alert("你不能修改该模块的记录!");
        return 0;
    }
    var umi = document.getElementById("update_mt_id").value;
    //console.log(umi);
    if(umi==""){
        alert("请输入序号");
        return 0;
    }
    $.getJSON("/mt_update",{UMI:umi},function (result) {
        updateflag(result);
    });
}
//修改的提示
function update_tips() {
    var identity = document.getElementById("idid").innerText;
    if(identity=="additive registrar"||identity=="pack registrar"){
        alert("你不能修改该模块的记录!");
        return 0;
    }
    var mt1 = document.getElementById("update_mt_id").value;
    var mt2 = document.getElementById("update_mt_name").value;
    var mt3 = document.getElementById("update_mt_check").value;
    var mt4 = document.getElementById("update_mt_qualified").value;
    var mt5 = document.getElementById("update_mt_staff").value;
    //console.log(mt1,mt2,mt3,mt4,mt5);
    $.getJSON("/mt_update",{get_mt_id:mt1,update_mt_name:mt2,update_mt_check:mt3,update_mt_qualified:mt4,update_mt_staff:mt5,UMI:null},function (result) {
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
        hidden("update_mt_id");
        hidden("mt_update_query");
        //window.location.href="material_register";
        show("mt_update_submit");
        show("update_show_id");
        show("update_mt_name");
        show("update_mt_check");
        show("update_mt_qualified");
        show("update_mt_staff");
        document.getElementById("update_mt_name").value = result.update_mtname;
        document.getElementById("update_mt_check").value = result.update_mtcheck;
        document.getElementById("update_mt_qualified").value = result.update_mtqualified;
        document.getElementById("update_mt_staff").value = result.update_mtstaff;
        document.getElementById("update_show_id").innerText = "查找到序号"+result.update_mtid+"的记录如下";
        document.getElementById("update_mt_id").value = result.update_mtid;
    }
    else if(state=="update_ok"){
        alert("修改成功");
        hidden("update_contents");
        window.location.href="material_register";
    }
}
function query_tips(){
    var smi2=document.getElementById("select_mt_index2").value;
    var mq1=document.getElementById("mt_query1").value;
    var mq2=document.getElementById("mt_query2").value;
    var mq3=document.getElementById("mt_query3").value;
    var mq4=document.getElementById("mt_query4").value;
    if(smi2==""){
        alert("请选择索引");
        return 0;
    }
    else if(mq1==""&&mq2==""&&mq3==""&&mq4==""){
        alert("请输入信息");
        return 0;
    }
    $.getJSON("/mt_query",{select_mt_index2:smi2,mt_query1:mq1,mt_query2:mq2,mt_query3:mq3,mt_query4:mq4},function (result) {
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
        window.location.href="material_register";
    }
}
function log_out() { //登出，清空所有session值并返回登录页面
    sessionStorage.clear();
    window.location.href="login";
}

