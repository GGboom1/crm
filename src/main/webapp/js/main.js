function openTab(text, url, iconCls){
    if($("#tabs").tabs("exists",text)){
        $("#tabs").tabs("select",text);
    }else{
        var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='" + url + "'></iframe>";
        $("#tabs").tabs("add",{
            title:text,
            iconCls:iconCls,
            closable:true,
            content:content
        });
    }
}

function logout() {
    $.messager.confirm('系统提示','您确定退出吗？',function(r){
        if (r){
            $.removeCookie("userName");
            $.removeCookie("trueName");
            $.removeCookie("userId");
            window.location.href = "index";
        }
    });
}

function openPasswordModifyDialog () {
    $("#dlg").dialog("open");

}

function closePasswordModifyDialog() {
    $("#dlg").dialog("close");
}

function modifyPassword() {
    $('#fm').form("submit", {
        url:ctx+"/user/updatePwd",
        onSubmit: function(){
            return $(this).form("validate");
        },
        success:function(data){
            data=JSON.parse(data);
            if(data.code == 200){
                $.messager.confirm('系统提示', '修改密码成功，点击跳转主页', function(r){
                    $.removeCookie("userName");
                    $.removeCookie("trueName");
                    $.removeCookie("userId");
                    window.location.href = "index";
                }).panel({closable:false});
            }else {
                $.messager.alert("系统提示", data.msg, "error");
            }
        }
    });
}

