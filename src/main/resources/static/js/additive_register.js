function $(obj){return document.getElementById(obj);}
function show(objid) {document.getElementById(objid).style.display='block';}
function hidden(objid) {document.getElementById(objid).style.display='none';}
function select_ad(){
    var sel_val=document.getElementById('select_ad_index').value;
    if (sel_val==="") {hidden('ad_delete1');hidden('ad_delete2');hidden('ad_delete3');}
    if (sel_val==="ad_id") {show('ad_delete1');hidden('ad_delete2');hidden('ad_delete3');}
    if (sel_val==="ad_name") {hidden('ad_delete1');show('ad_delete2');hidden('ad_delete3');}
    if (sel_val==="ad_staff") {hidden('ad_delete1');hidden('ad_delete2');show('ad_delete3');}
}
function select_ad2(){
    var sel_val2=document.getElementById('select_ad_index2').value;
    if (sel_val2==="") {hidden('ad_query1');hidden('ad_query2');hidden('ad_query3');hidden('ad_query4');}
    if (sel_val2==="ad_id2") {show('ad_query1');hidden('ad_query2');hidden('ad_query3');hidden('ad_query4');}
    if (sel_val2==="ad_name2") {hidden('ad_query1');show('ad_query2');hidden('ad_query3');hidden('ad_query4');}
    if (sel_val2==="ad_qualified2") {hidden('ad_query1');hidden('ad_query2');show('ad_query3');hidden('ad_query4');}
    if (sel_val2==="ad_staff2") {hidden('ad_query1');hidden('ad_query2');hidden('ad_query3');show('ad_query4');}
}
//增加记录的提示
function add_tips() {
    var identity = document.getElementById("idid").innerText;
    if(identity=="material registrar"||identity=="pack registrar"){
        alert("你不能增加该模块的记录!");
        return 0;
    }
    var tmn=document.getElementById("add_ad_name").value;
    var tmc=document.getElementById("add_ad_check").value;
    var smq=document.getElementById("select_ad_qualified").value;
    if(tmn==""||tmc==""||smq==""){
        alert("请输入完整信息");
        return 0;
    }
    $.getJSON("/ad_add",{type_ad_name:tmn,type_ad_check:tmc,select_ad_qualified:smq},function (result) {
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
    if(identity=="material registrar"||identity=="pack registrar"){
        alert("你不能删除该模块的记录!");
        return 0;
    }
    var smi=document.getElementById("select_ad_index").value;
    var md1=document.getElementById("ad_delete1").value;
    var md2=document.getElementById("ad_delete2").value;
    var md3=document.getElementById("ad_delete3").value;
    if(smi==""){
        alert("请选择索引");
        return 0;
    }
    else if(md1==""&&md2==""&&md3==""){
        alert("请输入信息");
        return 0;
    }
    $.getJSON("/ad_delete",{select_ad_index:smi,ad_delete1:md1,ad_delete2:md2,ad_delete3:md3},function (result) {
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
    if(identity=="material registrar"||identity=="pack registrar"){
        alert("你不能修改该模块的记录!");
        return 0;
    }
    var umi = document.getElementById("update_ad_id").value;
    //console.log(umi);
    if(umi==""){
        alert("请输入序号");
        return 0;
    }
    $.getJSON("/ad_update",{UMI:umi},function (result) {
        updateflag(result);
    });
}
//修改的提示
function update_tips() {
    var identity = document.getElementById("idid").innerText;
    if(identity=="material registrar"||identity=="pack registrar"){
        alert("你不能修改该模块的记录!");
        return 0;
    }
    var ad1 = document.getElementById("update_ad_id").value;
    var ad2 = document.getElementById("update_ad_name").value;
    var ad3 = document.getElementById("update_ad_check").value;
    var ad4 = document.getElementById("update_ad_qualified").value;
    var ad5 = document.getElementById("update_ad_staff").value;
    //console.log(ad1,ad2,ad3,ad4,ad5);
    $.getJSON("/ad_update",{get_ad_id:ad1,update_ad_name:ad2,update_ad_check:ad3,update_ad_qualified:ad4,update_ad_staff:ad5,UMI:null},function (result) {
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
        hidden("update_ad_id");
        hidden("ad_update_query");
        //window.location.href="additive_register";
        show("ad_update_submit");
        show("update_show_id");
        show("update_ad_name");
        show("update_ad_check");
        show("update_ad_qualified");
        show("update_ad_staff");
        document.getElementById("update_ad_name").value = result.update_adname;
        document.getElementById("update_ad_check").value = result.update_adcheck;
        document.getElementById("update_ad_qualified").value = result.update_adqualified;
        document.getElementById("update_ad_staff").value = result.update_adstaff;
        document.getElementById("update_show_id").innerText = "查找到序号"+result.update_adid+"的记录如下";
        document.getElementById("update_ad_id").value = result.update_adid;
    }
    else if(state=="update_ok"){
        alert("修改成功");
        hidden("update_contents");
        window.location.href="additive_register";
    }
}
function query_tips(){
    var smi2=document.getElementById("select_ad_index2").value;
    var mq1=document.getElementById("ad_query1").value;
    var mq2=document.getElementById("ad_query2").value;
    var mq3=document.getElementById("ad_query3").value;
    var mq4=document.getElementById("ad_query4").value;
    if(smi2==""){
        alert("请选择索引");
        return 0;
    }
    else if(mq1==""&&mq2==""&&mq3==""&&mq4==""){
        alert("请输入信息");
        return 0;
    }
    $.getJSON("/ad_query",{select_ad_index2:smi2,ad_query1:mq1,ad_query2:mq2,ad_query3:mq3,ad_query4:mq4},function (result) {
        queryflag(result);
    });
}
function queryflag(result) {
    var state = result.stat;
    state.toString();
    if(state=="query_wrong"){
        alert("查询不到数据");
        return 0;
    }else{
        window.location.href="additive_register";
    }
}
function log_out() { //登出，清空所有session值并返回登录页面
    sessionStorage.clear();
    window.location.href="login";
}

