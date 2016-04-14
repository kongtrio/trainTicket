<form id="alterForm" action="${basePath}/user/alterPassword" method="post">
    <fieldset>
        <legend>修改密码</legend>
        <label>旧密码</label>
        <input type="password" name="oldPassword" datatype="s4-16"
               errormsg="密码长度必须为4-16位！"/>

        <label>新密码</label>
        <input type="password" name="password" datatype="s4-16"
               errormsg="密码长度必须为4-16位！"/>

        <label>确认密码</label>
        <input type="password" datatype="s4-16"
               errormsg="两次输入的密码不一致" recheck="password"/>

        <span id="notify_pop" class="help-block" style="color: red"></span>

        <button type="submit" class="btn">提交</button>
    </fieldset>
</form>
<script type="text/javascript">
    $("#alterForm").Validform({
        tiptype: function (msg, o, cssctl) {
            var objtip = $("#notify_pop");
            cssctl(objtip, o.type);
            objtip.text(msg);
        }
    });
</script>

