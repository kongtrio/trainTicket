<form action="${basePath}/advice" method="POST" id="query_form">
    您的姓名:
    <input class="input-medium search-query" type="text" datatype="s1-18" errormsg="用户名至少1个字符,最多18个字符！"
           id="username" name="username" placeholder="姓名"/>&nbsp;&nbsp;&nbsp;&nbsp;
    请留下您的邮箱,方便我们联系您
    <input class="input-medium search-query" type="text" datatype="s1-18" errormsg="邮箱至少1个字符,最多36个字符！"
           id="email" name="email" placeholder="邮箱"/><br/>
    <br/><br/>
    <textarea style="width: 630px;" rows="5" name="content"  nullmsg="请输入内容!"></textarea>
    <input type="submit" class="btn" value="提交" style="    margin-top: 65px;"/>
</form>

<script type="text/javascript">
    $("#query_form").Validform({
        tiptype: function (msg, o, cssctl) {
//            alert(msg);
//            var objtip = $(".error-box");
//            cssctl(objtip, o.type);
//            objtip.text(msg);
        }
    });
</script>