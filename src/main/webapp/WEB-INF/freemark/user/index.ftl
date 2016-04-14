<dl>
    <dt>
        用户名
    </dt>
    <dd>
    ${user.userName}
    </dd>
    <dt>
        电话号码
    </dt>
    <dd>
    ${user.telphone!""}
    </dd>
    <dt>
        注册时间
    </dt>
    <dd>
    ${user.registerTime!""}
    </dd>
    <button type="button" class="btn" onclick="toAlterPassword()">修改密码</button>
</dl>
<script type="text/javascript">
    function toAlterPassword() {
        window.location = "${basePath}/user/alterPassword";
    }
</script>