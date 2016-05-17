<form id="alterForm" action="${basePath}/user/alterTel" method="post">
    <fieldset>
        <label>新的电话号码</label>
        <input type="text" datatype="s11-11" name="telphone"
               errormsg="电话号码必须11位"/>

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

