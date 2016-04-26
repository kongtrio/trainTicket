<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>系统登录</title>
    <link href="${basePath}/css/login.css" rel="stylesheet" rev="stylesheet" type="text/css" media="all"/>
    <link href="${basePath}/css/normalize.css" rel="stylesheet" rev="stylesheet" type="text/css" media="all"/>
    <script type="text/javascript" src="${basePath}/js/jquery1.42.min.js"></script>
    <script type="text/javascript" src="${basePath}/js/jquery.SuperSlide.js"></script>
<#--文档网址   validform   http://validform.rjboy.cn/document.html-->
    <script type="text/javascript" src="${basePath}/js/Validform_v5.3.2_min.js"></script>
    <script type="text/javascript" src="${basePath}/js/jquery.modal.js"></script>

    <style type="text/css">

    </style>
    <script>
        $(function () {
            $('.modalLink').modal({
                trigger: '#register',          // id or class of link or button to trigger modal
                olay: 'div.overlay',             // id or class of overlay
                modals: 'div.modal',             // id or class of modal
                background: '000000',           // hexidecimal color code - DONT USE #
                openOnLoad: false,              // open modal on page load | true or false | default=false
                resizeWindow: true,             // move modal when window is resized | true or false | default=false
                close: '#cancelPop'               // id or class of close button
            });

            $(".loginform").Validform({
                tiptype: function (msg, o, cssctl) {
                    var objtip = $(".error-box");
                    cssctl(objtip, o.type);
                    objtip.text(msg);
                }
            });

            $(".registerform").Validform({
                tiptype: function (msg, o, cssctl) {
                    var objtip = $(".register-error-box");
                    cssctl(objtip, o.type);
                    objtip.text(msg);
                }, beforeSubmit: function (curform) {
                    //在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
                    //这里明确return false的话表单将不会提交;
                    var username = $("#r_username").val();
                    var flag = false;
//                    http://www.cnblogs.com/jayleke/archive/2012/08/10/2633174.html
                    $.ajax({
                        type: 'POST',
                        url: '${basePath}/genal/checkUsernameExist',
                        dataType: 'json',
                        data: {username: username},
                        async: false,
                        success: function (data) {
                            if (data == true || data == "true") {
                                flag = true;
                            } else {
                                $(".register-error-box").html("用户名已存在");
                            }
                        }
                    });
                    return flag;
                }
            });

        });
    </script>
</head>

<body>


<div class="header">
    <h1 class="headerLogo"><a target="_blank" href="http://cec.jmu.edu.cn/"><img alt="logo" style="height: 70px;"
                                                                                 src="${basePath}/image/login_back.gif"></a>
    </h1>

    <div class="headerNav">
        <a target="_blank" href="http://cec.jmu.edu.cn/">集美计算机工程学院</a>
        <a target="_blank" href="http://www.jmu.edu.cn/">集大官网</a>
        <a href="${basePath}/tourist">网站首页</a>
        <a href="${basePath}/advice">意见反馈</a>
    </div>
</div>

<div class="banner">

    <div class="login-aside">
        <div id="o-box-up"></div>
        <div id="o-box-down" style="table-layout:fixed;">
            <div class="error-box">
            ${msg!""}
            </div>

            <form class="loginform" action="${basePath}/j_spring_security_check" method="post">
                <div class="fm-item">
                    <label for="logonId" class="form-label">MISS系统登陆：</label>
                    <input type="text" maxlength="100" id="username" class="i-text" name="j_username"
                           placeholder="请输入账号" datatype="s4-18" errormsg="用户名至少4个字符,最多18个字符！">

                    <div class="ui-form-explain"></div>
                </div>

                <div class="fm-item">
                    <label for="logonId" class="form-label">登陆密码：</label>
                    <input type="password" maxlength="100" id="password" class="i-text" datatype="*4-16"
                           name="j_password" placeholder="请输入密码" errormsg="密码范围在4~16位之间！">

                    <div class="ui-form-explain"></div>
                </div>

                <div class="fm-item pos-r">
                    <label for="logonId" class="form-label">验证码</label>
                    <input type="text" placeholder="输入验证码" maxlength="100" id="yzm" class="i-text yzm"
                           name="validateCode" datatype="s4-4" errormsg="验证码的长度必须为4位！"
                           nullmsg="请输入验证码！">

                    <div class="ui-form-explain"><img src="${basePath}/jsp/image.jsp" id="randImage"
                                                      onclick="javascript:document.getElementById('randImage').src = '${basePath}/jsp/image.jsp?' + Math.random();"
                                                      class="yzm-img"/></div>
                </div>

                <div class="fm-item">
                    <label for="logonId" class="form-label"></label>
                    <input type="submit" value="" tabindex="4" id="send-btn" class="btn-login">

                    <div class="ui-form-explain"></div>
                </div>

            </form>
            <div style="margin-top:10px">
                <a id="register" href="#modal1" style="color:wheat;cursor: hand">还没用户?注册一个</a>
            </div>
        </div>

    </div>

    <div class="bd">
        <ul>
            <li style="background:url(${basePath}/image/background.png) #BCE0FF center 0 no-repeat;">
                <a href="${basePath}/tourist"></a>
            </li>
        </ul>
    </div>

    <div class="hd">
        <ul></ul>
    </div>
</div>

<div class="overlay"></div>
<div id="modal1" class="modal">
    <h2 style="">用户注册</h2>

    <form class="registerform" action="${basePath}/genal/register" method="post">
        <div class="register-error-box" style="color:red;margin-bottom: 5px;">
        </div>
        <div class="fm-item" style="margin: 5px 0;">
            <label for="logonId" class="form-label regitster-form-label">用户名：&nbsp;</label>
            <input type="text" maxlength="100" id="r_username" class="i-text" datatype="s4-16"
                   name="userName" placeholder="请输入用户名" errormsg="用户名范围在4~16位之间！"
                   nullmsg="请输入用户名!">

            <div class="ui-form-explain"></div>
        </div>
        <div class="fm-item" style="margin: 5px 0;">
            <label for="logonId" class="form-label regitster-form-label">密&nbsp;&nbsp;码：</label>
            <input type="password" maxlength="100" id="r_password" class="i-text" datatype="*4-16"
                   name="password" placeholder="请输入密码" nullmsg="请输入密码！" errormsg="密码范围在4~16位之间！">

            <div class="ui-form-explain"></div>
        </div>
        <div class="fm-item" style="margin: 5px 0;">
            <label for="logonId" class="form-label regitster-form-label">确认密码：</label>
            <input type="password" maxlength="100" id="r_again_password" recheck="password" class="i-text"
                   datatype="*4-16" placeholder="请输入密码" errormsg="两次输入的密码不一致!">

            <div class="ui-form-explain"></div>
        </div>
        <div class="fm-item" style="margin: 5px 0;">
            <label for="logonId" class="form-label regitster-form-label">手&nbsp;&nbsp;机：</label>
            <input type="text" maxlength="100" id="password" class="i-text" datatype="n11-11"
                   name="telphone" placeholder="请输入11位的手机号码" errormsg="手机号只能是11位的数字">

            <div class="ui-form-explain"></div>
        </div>
        <div class="fm-item">
            <label for="logonId" class="form-label"></label>
            <input type="submit" value="注册" tabindex="4">
            <input type="button" value="取消" id="cancelPop" tabindex="4">

            <div class="ui-form-explain"></div>
        </div>
    </form>
</div>


<script type="text/javascript">jQuery(".banner").slide({
    titCell: ".hd ul",
    mainCell: ".bd ul",
    effect: "fold",
    autoPlay: true,
    autoPage: true,
    trigger: "click"
});</script>


<div class="banner-shadow"></div>

<div class="footer">
    <p>Copyright &copy; 2014.Company name All rights reserved.</p>
</div>
<#--<div style="display:none">-->
<#--<script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script>-->
<#--</div>-->
</body>
</html>
