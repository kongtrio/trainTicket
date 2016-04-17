<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>火车票订票系统</title>
    <link rel="stylesheet" href="${basePath}/bootstrap-3.3.5-dist/css/bootstrap-combined.min.css">
    <link rel="stylesheet" href="${basePath}/css/tourist.css">
    <link rel="stylesheet" type="text/css" href="${basePath}/css/jquery-ui/jquery-ui.css">
    <script src="${basePath}/js/jquery.js"></script>
    <script src="${basePath}/js/market/common.js"></script>
    <script type="text/javascript" src="${basePath}/js/Validform_v5.3.2_min.js"></script>
    <script src="${basePath}/bootstrap-3.3.5-dist/js/bootstrap.js"></script>
    <script src="${basePath}/js/jquery-ui/jquery-ui.js"></script>
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
            ${body}
              <#include "foot.ftl">
        </div>
    </div>
</div>
<#--提示-->
<div id="dialog-message" style="display: none" title="提示">
    <p>
        <span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
        <span id="msg_content"></span>
    </p>
</div>
</body>
<script type="text/javascript">
    var notifyMsg = '${msg!""}';
    console.log("notify==" + notifyMsg);
    if (notifyMsg.trim() != "") {
        console.log(notifyMsg);
        $("#msg_content").html(notifyMsg);
        $( "#dialog-message" ).dialog({
            height: "auto",
            modal: true,
            buttons: {
                Ok: function() {
                    $( this ).dialog( "close" );
                }
            }
        });
    }
</script>
</html>
