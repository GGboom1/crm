function searchModules() {
    $("#dg").datagrid("load", {
        moduleName: $("#moduleName").val(),
        optValue: $("#optValue").val(),
        parentModuleName: $("#parentModuleName").val()
    });
}

function formatterGrade(val) {
    if(val == 0){
        return "根级";
    }else if(val == 1){
        return "一级";
    }else if(val == 2){
        return "二级";
    }
}

$(function () {
    $("#parentMenu").hide();

    $("#grade").combobox({
        onChange:function(grade){
            if(grade==1||grade==2){
                $("#parentMenu").show();
            }
            if(grade==0){
                $("#parentMenu").hide();
            }
            loadParentModules(grade-1);
        }
    });
});

function openAddModuleDialog() {
    $("#fm").form("clear");
    $("#dlg").dialog("open");
}

function openModifyModuleDialog() {
    var selected = $("#dg").datagrid("getSelections");
    if(selected == null || selected.length != 1){
        $.messager.alert("来自系统", "请选择一条数据", "warning");
        return;
    }
    $("#fm").form("load", selected[0]);
    var grade=selected[0].grade;

    if(grade!=0){
        loadParentModules(grade-1);
        $("#parentMenu").show();
        $("#parentId").combobox("setValue",selected[0].parentId);
    }else{
        $("#grade").combobox("setValue",grade);
    }

    $("#dlg").dialog("open").dialog("setTitle","修改资源");
}

function closeDialog() {
    $("#dlg").dialog("close");
}

function loadParentModules(grade) {
    $("#parentId").combobox("clear");
    $("#parentId").combobox({
        url:ctx+"/module/queryModulesByGrade?grade="+grade,
        valueField:'id',
        textField:'moduleName'
    });
}

function saveOrUpdateModule(){

    var id=$("#id").val();
    var url=ctx+"/module/insert";
    if(!isEmpty(id)){
        url=ctx+"/module/update";
    }

    $("#fm").form("submit",  {
        url:url,

        onSubmit:function () {
            return $("#fm").form("validate");
        },
        success:function (data) {
            data = JSON.parse(data);
            if(data.code==200){
                $.messager.alert("来自crm系统",data.msg,"info")
                $("#fm").form("clear");
                closeDialog();
                searchModules();
            }else{
                $.messager.alert("来自crm系统",data.msg,"info")
            }
        }
    });
}

function deleteModule(){

    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选中待删除记录!","info");
        return;
    }

    // ids=1&ids=2&ids=3
    var ids="ids=";
    for(var i=0;i<rows.length;i++){
        if(i<=rows.length-2){
            ids=ids+rows[i].id+"&ids=";
            continue;
        }
        ids=ids+rows[i].id;
    }
    $.messager.confirm("来自crm","确定删除选中的"+rows.length+"条记录?",function(r){
        if(r){
            $.ajax({
                type:"post",
                url:ctx+"/module/delete",
                data:ids,
                dataType:"json",
                success:function(data){
                    $.messager.alert("来自crm",data.msg,"info");
                    if(data.code==200){
                        closeDialog();
                        searchModules();
                    }
                }
            });
        }
    });
}