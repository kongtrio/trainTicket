<form id="addForm" action="${basePath}/user/contact/addContact" method="post">
    <input id="id" type="hidden" name="contactId" value="<#if contact??>${contact.id}</#if>"/>
    <fieldset>
        <legend><#if contact??>修改联系人<#else>添加联系人</#if></legend>
        <label>姓名</label>
        <input type="text" name="name" datatype="s2-5" value="<#if contact??>${contact.name}</#if>"
               errormsg="联系人名字至少2个字符,最多5个字符！"/>

        <label>电话号码</label>
        <input type="text" name="telphone" datatype="s11-11" value="<#if contact??>${contact.telphone}</#if>"
               errormsg="电话必须是11位的号码！"/>

        <label>身份证号码</label>
        <input type="text" name="identityCard" datatype="s18-18" value="<#if contact??>${contact.identityCard}</#if>"
               errormsg="身份格式不正确！"/>

        <span id="notify_pop" class="help-block" style="color: red"></span>

        <button type="submit" class="btn">提交</button>
    </fieldset>
</form>
<script type="text/javascript">
    if ($("#id").val() != "") {
        $("#addForm").attr("action","${basePath}/user/contact/updateContact");
    }
    $("#addForm").Validform({
        tiptype: function (msg, o, cssctl) {
            var objtip = $("#notify_pop");
            cssctl(objtip, o.type);
            objtip.text(msg);
        }
    });
</script>

