
$(function () {
    var devresult = $("#devResult").val();

    if(devresult==2||devresult==3){
        $("#toolbar").remove();
    }
    var saleChanceId = $("#saleChanceId").val();
    $('#dg').edatagrid({
        url: ctx+'/cus_dev_plan/queryCusDevPlans?saleChanceId='+saleChanceId,
        saveUrl: ctx+"/cus_dev_plan/saveCusDevPlan?saleChanceId="+saleChanceId,
        updateUrl: ctx+"/cus_dev_plan/updateCusDevPlan?saleChanceId="+saleChanceId,
    });
});

function saveCusDevPlan() {
    $("#dg").edatagrid("saveRow");
    $("#dg").edatagrid("load");
}

function delCusDevPlan() {
    var edatagrid = $("#dg").edatagrid("getSelected");
    if(edatagrid == null){
        $.messager.alert("系统提示", "请选中一条数据", "warning");
        return ;
    }
    $.messager.confirm("系统提示", "您确定要删除此条记录吗", function (r) {
        if(r){
            $.ajax({
                method: "post",
                url: ctx+'/cus_dev_plan/deleteCusDevPlan',
                data: {
                    id: edatagrid.id
                },
                type: "json",
                success: function (data) {
                    if(data.code == 200){
                        $.messager.alert("系统提示", data.msg, "info");
                        $("#dg").edatagrid("load");
                    }else{
                        $.messager.alert("系统提示", data.msg, "error");
                    }
                }
            });
        }
    });
}

function updateCusDevPlan() {
    $("#dg").edatagrid("saveRow");
}

function updateSaleChanceDevResult(devResult) {
    $.messager.confirm("来自crm系统","确定执行该操作",function (r) {
        if(r){
            $.ajax({
                type:"post",
                url:ctx+"/sale_chance/updateSaleChanceDevResult",
                data:"devResult="+devResult + "&saleChanceId=" + $("#saleChanceId").val(),
                dataType:"json",
                success:function (data) {
                    $.messager.alert("来自crm",data.msg,"info");
                    if(data.code==200){
                        $("#toolbar").remove();
                    }
                }
            })
        }
    })
}
