layui.use(['form', 'layer', 'jquery'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer
    $ = layui.jquery;

    // $(".loginBody .seraph").click(function(){
    //     layer.msg("这只是做个样式，至于功能，你见过哪个后台能这样登录的？还是老老实实的找管理员去注册吧",{
    //         time:5000
    //     });
    // })

    //登录按钮
    form.on("submit(login)", function (data) {
        var btn = $(this);
        btn.text("登录中...").attr("disabled", "disabled").addClass("layui-disabled");
        $.post("/login/login", data.field, function (obj) {
            btn.text("登录").attr("disabled", false).removeClass("layui-disabled");
            layer.msg(obj.msg);
            if (obj.code == 1) { //登陆成功跳转首页
                location.href = "/sys/index";
            }
        });
        return false;
    })

    //表单输入效果
    $(".loginBody .input-item").click(function (e) {
        e.stopPropagation();
        $(this).addClass("layui-input-focus").find(".layui-input").focus();
    })
    $(".loginBody .layui-form-item .layui-input").focus(function () {
        $(this).parent().addClass("layui-input-focus");
    })
    $(".loginBody .layui-form-item .layui-input").blur(function () {
        $(this).parent().removeClass("layui-input-focus");
        if ($(this).val() != '') {
            $(this).parent().addClass("layui-input-active");
        } else {
            $(this).parent().removeClass("layui-input-active");
        }
    })
})
