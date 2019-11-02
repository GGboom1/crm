function searchSaleChances() {
    $("#dg").datagrid("load",{
        createMan:$("#createMan").val(),
        customerName:$("#customerName").val(),
        createDate:$("#createDate").datebox("getValue"),
        state:$("#state").combobox("getValue")
    });
}

function formatterState(val) {
    if (val==0){
        return "未分配";
    }else if(val==1){
        return "已分配";
    }else {
        return "未定义";
    }
}

function saveChance() {
    var id = $("#id").val();
    var url = ctx+"/sale_chance/insert";
    if(!isEmpty(id)){
        url = ctx+"/sale_chance/update";
    }
    $("#fm").form("submit", {
        url: url,
        onSubmit: function (params) {
            params.createMan = $.cookie("trueName");
            return $("#fm").form("validate");
        },
        success: function (data) {
            data = JSON.parse(data);
            if(data.code = 200){
                $.messager.alert("系统提示", data.msg, "info");
                closeDialog();
                searchSaleChances();
            }else {
                $.messager.alert("系统提示", data.msg, "error");
            }
        }
    });
}

function openAddChanceDialog() {
    $("#fm").form("clear");
    $("#dlg").dialog("setTitle", "添加营销机会").dialog("open");
}

function closeDialog() {
    $("#dlg").dialog("close");
}

function openModifyChanceDialog() {
    var data = $("#dg").datagrid("getSelections");
    if(data.length != 1){
        $.messager.alert("系统提示", "只能选中一条数据进行修改", "warning");
        return;
    }
    $("#fm").form("clear").form("load", data[0]);
    $("#dlg").dialog("setTitle", "修改营销机会").dialog("open");
}

function deleteChance() {
    var data = $("#dg").datagrid("getSelections");
    if (data.length < 1){
        $.messager.alert("系统提示", "请至少选中一条数据", "warning");
        return;
    }

    var ids = "";

    for(var i = 0; i < data.length; i++){
        if(i > 0){
            ids += "&ids="+data[i]["id"];
        }else {
            ids += "ids="+data[i]["id"];
        }
    }
    $.messager.confirm("系统提示", "你确定要删除此条记录吗", function (r) {
        $.ajax({
            method:"post",
            url:ctx+"/sale_chance/delete",
            data:ids,
            success:function () {
                if(data.code = 200){
                    $.messager.alert("系统提示", data.result.msg, "info");
                    closeDialog();
                    searchSaleChances();
                }else {
                    $.messager.alert("系统提示", data.result.msg, "error");
                }
            },
            type: "json"
        });
    });
}

