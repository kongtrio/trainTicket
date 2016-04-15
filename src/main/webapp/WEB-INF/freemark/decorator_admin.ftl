<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>火车票订票系统</title>
    <meta name="author" content="DeathGhost"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/css/style.css"/>
    <!--[if lt IE 9]>
    <script src="${basePath}/js/html5.js"></script>
    <![endif]-->
    <script src="${basePath}/js/jquery.js"></script>
    <script src="${basePath}/js/jquery.mCustomScrollbar.concat.min.js"></script>
    <script src="${basePath}/js/market/common.js"></script>
    <script type="text/javascript" src="${basePath}/js/Validform_v5.3.2_min.js"></script>
    <script>
        (function ($) {
            $(window).load(function () {

                $("a[rel='load-content']").click(function (e) {
                    e.preventDefault();
                    var url = $(this).attr("href");
                    $.get(url, function (data) {
                        $(".content .mCSB_container").append(data); //load new content inside .mCSB_container
                        //scroll-to appended content
                        $(".content").mCustomScrollbar("scrollTo", "h2:last");
                    });
                });

                $(".content").delegate("a[href='top']", "click", function (e) {
                    e.preventDefault();
                    $(".content").mCustomScrollbar("scrollTo", $(this).attr("href"));
                });

            });
        })(jQuery);
    </script>
</head>
<body>
<!--header-->
<header>
    <h1><img src="${basePath}/image/admin_logo.png"/></h1>
    <ul class="rt_nav">
        <li><a href="${basePath}/"class="website_icon">站点首页</a></li>
        <li><a href="#" class="admin_icon">DeathGhost</a></li>
        <li><a href="#" class="set_icon">账号设置</a></li>
        <li><a href="${basePath}/j_spring_security_logout" class="quit_icon">安全退出</a></li>
    </ul>
</header>

<!--aside nav-->
<aside class="lt_aside_nav content mCustomScrollbar">
    <h2><a href="index.php">起始页</a></h2>
    <ul>
        <li>
            <dl>
                <dt>用户信息</dt>
                <!--当前链接则添加class:active-->
                <dd><a href="${basePath}/admin/users"
                       <#if fullpath?contains("admin/users")>class="active"</#if>>用户列表</a>
                </dd>
                <dd><a href="${basePath}/admin/contact" <#if fullpath?contains("admin/contact")>class="active"</#if>>常用联系人</a>
                </dd>
            </dl>
        </li>
        <li>
            <dl>
                <dt>列车信息</dt>
                <dd><a href="${basePath}/admin/sites"
                       <#if fullpath?contains("admin/sites")>class="active"</#if>>站点信息</a></dd>
                <dd><a href="${basePath}/admin/addTrain" <#if fullpath?contains("admin/addTrain")>class="active"</#if>>添加一班列车</a>
                </dd>
                <dd><a href="${basePath}/admin/train" <#if fullpath?contains("admin/train")>class="active"</#if>>列车管理</a></dd>
            </dl>
        </li>
        <li>
            <dl>
                <dt>其他</dt>
                <dd><a href="${basePath}/admin/notify"
                       <#if fullpath?contains("admin/notify")>class="active"</#if>>公告信息</a></dd>
            </dl>
        </li>
    </ul>
</aside>

<section class="rt_wrap content mCustomScrollbar">
    <div class="rt_content">
    ${body}
    </div>
</section>
</body>
<script type="text/javascript">
    var notifyMsg = '${notifyMsg!""}';
    console.log("notify==" + notifyMsg);
    if (notifyMsg.trim() != "") {
        console.log(notifyMsg);
        alert(notifyMsg);
    }
</script>
</html>
