<div>
    欢迎光临。。。请登录
    <form action="${basePath}/j_spring_security_check" method="post">
        username:<input name="j_username"/><br/>
        password:<input type="password" name="j_password"/><br/>
        code:<input type="text" name="code"/><br/>
        <input type="submit" value="login"/>
    </form>
</div>