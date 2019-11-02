function openCustomerOtherInfo(title,type) {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("crm","请先选中一个客户","info");
        return;
    }
    if(rows.length>1){
        $.messager.alert("crm","只能选中一个客户","info");
        return;
    }
    window.parent.openTab(title,ctx+"/customer/openCustomerOtherInfo/"+type+"/"+rows[0].id);
}
function searchCustomer() {
    $("#dg").datagrid("load", {
        "khno": $("#s_khno").val(),
        "name": $("#s_name").val()
    });
}

function openCustomerAddDialog() {
    $("#dlg").dialog("open");
}

function openCustomerModifyDialog() {
    var datagrid = $("#dg").datagrid("getSelections");
    if(datagrid.length != 1){
        $.messager.alert("系统提示", "请选择一条数据", "error");
        return;
    }
    $("#fm").form("load", datagrid[0]);
    $("#dlg").dialog("open").dialog("setTitle","修改营销机会信息");
}

function saveOrUpdateCustomer() {
    var id = $("#id").val();
    var url = ctx+"/customer/insert";
    if(id != null && id != ""){
        url = ctx+"/customer/update";
    }
    $("#fm").form("submit", {
        url:url,
        onSubmit:function () {
            return $("#fm").form("validate");
        },
        success:function (data) {
            data=JSON.parse(data);
            $.messager.alert("crm",data.msg,"info");
            if(data.code==200){
                $("#dlg").dialog("close");
                initFormData();
                searchCustomer();
            }
        }
    });
}

function deleteCustomer() {
    var datagrid = $("#dg").datagrid("getSelections");
    if(datagrid.length < 1){
        $.messager.alert("系统提示", "请至少选择一条数据", "error");
        return;
    }
    var data = "";
    for (var i = 0; i < datagrid.length; i++){
        if(i < datagrid.length - 1){
            data += "ids="+datagrid[i]["id"]+"&";
        }else {
            data += "ids="+datagrid[i]["id"];
        }
    }
    $.messager.confirm("系统提示", "您确定要删除这些数据吗", function (r) {
       if(r){
           $.ajax({
               type: "post",
               url: ctx + "/customer/delete",
               data: data,
               dateType: "json",
               success: function (data) {
                   $.messager.alert("来自crm系统", data.msg, "info");
                   if (data.code == 200) {
                       $("#dg").datagrid("reload");
                   }
               }
           })
       }
    });
}

function initFormData() {
    $("#id").val("");
    $("#name").val("");
    $("#area").combobox("clear");
    $("#cusManager").combobox("clear");
    $("#level").combobox("clear");
    $("#myd").combobox("clear");
    $("#xyd").combobox("clear");
    $("#postCode").val("");
    $("#phone").val("");
    $("#fax").val("");
    $("#webSite").val("");
    $("#address").val("");
    $("#yyzzzch").val("");
    $("#fr").val("");
    $("#zczj").val("");
    $("#nyye").val("");
    $("#khyh").val("");
    $("#khzh").val("");
    $("#dsdjh").val("");
    $("#gsdjh").val("");
}

function closeCustomerDialog() {
    $("#dlg").dialog("close");
}