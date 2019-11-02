function searchRoles() {
    $("#dg").datagrid("load", {
        roleName: $("#roleName").val()
    });
}

function openAddRoleDialog() {
    $("#fm").form("clear");
    $("#dlg").dialog("open");
}

function closeDialog() {
    $("#dlg").dialog("close");
}

function openModifyRoleDialog() {
    var datagrid = $("#dg").datagrid("getSelected");
    if(datagrid == null){
        $.messager.alert("系统提示", "请选中一条数据", "warning");
        return;
    }
    $("#fm").form("load", datagrid);
    $("#dlg").dialog("open");
}

function saveOrUpdateRole() {
    var id = $("#id").val();
    var url = ctx+"/role/update";
    if(isEmpty(id)){
        url = ctx+"/role/insert";
    }
    $("#fm").form("submit", {
        url: url,
        onSubmit: function () {
            return $("#fm").form("validate");
        },
        success: function (data) {
            data = JSON.parse(data);
            $.messager.alert("系统提示", data.msg, "info");
            if(data.code == 200){
                $("#dg").datagrid("load");
                $("#dlg").dialog("close");
            }
        }
    })
}

function addPermission() {
    $.ajax({
        type: "post",
        url: ctx + "/role/addPermission",
        data: "rid="+$("#rid").val()+"&"+$("#moduleIds").val(),
        dataType: "json",
        success: function (data) {
            if(data.code==200){
                $.messager.alert("来自crm",data.msg,"info");
                $("#moduleIds").val("");
                $("#rid").val("");
                closeDialog02();
            }else {
                $.messager.alert("来自crm",data.msg,"error");
            }
        }
    })
}

function openRelatePermissionDlg() {
    var selected = $("#dg").datagrid("getSelected");
    if(selected == null){
        $.messager.alert("系统提示", "请选中一条数据", "warning");
        return;
    }
    $("#rid").val(selected.id);
    loadModuleData();
    $("#dlg02").dialog("open");
}

var ztreeObj;
function loadModuleData() {
    $.ajax({
        type: "post",
        url: ctx + "/module/queryAllModuleDtos",
        data: {
          rid: $("#rid").val()
        },
        dataType: "json",
        success: function (data) {
            var setting = {
                check: {
                    enable: true,
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback: {
                    onCheck: zTreeOnCheck
                }
            };
            ztreeObj = $.fn.zTree.init($("#treeDemo"), setting, data);
        }
    })
}

function closeDialog02() {
    $("#dlg02").dialog("close");
}

function zTreeOnCheck() {
    var znodes=ztreeObj.getCheckedNodes(true);
    var moduleIds = "moduleIds=";
    if(znodes.length > 0){
        for (var i = 0; i< znodes.length ; i++){
            if(i < znodes.length - 1){
                moduleIds += znodes[i].id + "&moduleIds=";
            }else {
                moduleIds += znodes[i].id;
            }
        }
    }
    $("#moduleIds").val(moduleIds);
}