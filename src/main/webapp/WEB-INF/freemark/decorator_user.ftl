<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>火车票订票系统</title>
    <link rel="stylesheet" href="${basePath}/bootstrap-3.3.5-dist/css/bootstrap-combined.min.css">
    <link rel="stylesheet" href="${basePath}/css/tourist.css">
    <script src="${basePath}/js/jquery.js"></script>
    <script src="${basePath}/js/market/common.js"></script>
    <script type="text/javascript" src="${basePath}/js/Validform_v5.3.2_min.js"></script>
    <script src="${basePath}/bootstrap-3.3.5-dist/js/bootstrap.js"></script>
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
<div class="container">
    <div class="row">
        <div class="span12">
        <#include "header.ftl">
            <div class="row-fluid">
                <div class="span3">
                    <ul class="nav nav-list well">
                        <li class="nav-header">
                            个人信息
                        </li>
                        <li <#if fullpath?contains("user")
                        && !fullpath?contains("order")
                        && !fullpath?contains("contact")>class="active"</#if>>
                            <a href="${basePath}/user">个人信息</a>
                        </li>
                        <li <#if fullpath?contains("user/contact/list")>class="active"</#if>>
                            <a href="${basePath}/user/contact/list">常用联系人</a>
                        </li>
                        <li <#if fullpath?contains("user/contact/addContact")>class="active"</#if>>
                            <a href="${basePath}/user/contact/addContact">添加联系人</a>
                        </li>
                        <li class="nav-header">
                            订单管理
                        </li>
                        <li <#if fullpath?contains("order/unFinished")>class="active"</#if>>
                            <a href="${basePath}/user/order/unFinished">未完成订单</a>
                        </li>
                        <li <#if fullpath?contains("order/finished")>class="active"</#if>>
                            <a href="${basePath}/user/order/finished">已完成订单</a>
                        </li>
                        <li class="divider">
                        </li>
                        <li>
                            <a href="#">投诉建议</a>
                        </li>
                    </ul>
                </div>
                <div class="span9">
                ${body}
                </div>
            </div>
        <#include "foot.ftl">
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var notifyMsg = '${notifyMsg!""}';
    if (notifyMsg.trim() != "") {
        console.log(notifyMsg);
        alert(notifyMsg);
    }
</script>
</html>
