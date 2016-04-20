<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>404错误</title>
    <link rel="stylesheet" href="<%=basePath%>/bootstrap-3.3.5-dist/css/bootstrap-combined.min.css">
    <link rel="stylesheet" href="<%=basePath%>/css/tourist.css">
    <script src="<%=basePath%>/js/jquery.js"></script>
    <script src="<%=basePath%>/js/market/common.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/Validform_v5.3.2_min.js"></script>
    <script src="<%=basePath%>/bootstrap-3.3.5-dist/js/bootstrap.js"></script>
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
            <div class="carousel slide" id="carousel-726949">
                <ol class="carousel-indicators">
                    <li data-slide-to="0" data-target="#carousel-726949">
                    </li>
                    <li data-slide-to="1" data-target="#carousel-726949" class="active">
                    </li>
                    <li data-slide-to="2" data-target="#carousel-726949">
                    </li>
                </ol>
                <div class="carousel-inner">
                    <div class="item">
                        <img alt="" src="<%=basePath%>/image/background.png"/>

                        <div class="carousel-caption">
                            <h4>
                                棒球
                            </h4>

                            <p>
                                棒球运动是一种以棒打球为主要特点，集体性、对抗性很强的球类运动项目，在美国、日本尤为盛行。
                            </p>
                        </div>
                    </div>
                    <div class="item active">
                        <img alt="" src="<%=basePath%>/image/background.png"/>

                        <div class="carousel-caption">
                            <h4>
                                冲浪
                            </h4>

                            <p>
                                冲浪是以海浪为动力，利用自身的高超技巧和平衡能力，搏击海浪的一项运动。运动员站立在冲浪板上，或利用腹板、跪板、充气的橡皮垫、划艇、皮艇等驾驭海浪的一项水上运动。
                            </p>
                        </div>
                    </div>
                    <div class="item">
                        <img alt="" src="<%=basePath%>/image/background.png"/>

                        <div class="carousel-caption">
                            <h4>
                                自行车
                            </h4>

                            <p>
                                以自行车为工具比赛骑行速度的体育运动。1896年第一届奥林匹克运动会上被列为正式比赛项目。环法赛为最著名的世界自行车锦标赛。
                            </p>
                        </div>
                    </div>
                </div>
                <a data-slide="prev" href="#carousel-726949" class="left carousel-control">‹</a> <a data-slide="next"
                                                                                                    href="#carousel-726949"
                                                                                                    class="right carousel-control">›</a>
            </div>
            <div class="navbar navbar-inverse">
                <div class="navbar-inner">
                    <div class="container-fluid">
                        <a data-target=".navbar-responsive-collapse" data-toggle="collapse" class="btn btn-navbar"><span
                                class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></a>
                        <a href="<%=basePath%>/tourist" class="brand">火车票订票系统</a>

                        <div class="nav-collapse collapse navbar-responsive-collapse">
                            <ul class="nav">
                                <li>
                                    <a href="<%=basePath%>/tourist">购票</a>
                                </li>
                                <li>
                                    <a href="<%=basePath%>/user">用户中心</a>
                                </li>
                                <li>
                                    <a href="<%=basePath%>/notify">通知公告</a>
                                </li>
                                <li>
                                    <a href="<%=basePath%>/advice">投诉建议</a>
                                </li>
                            </ul>
                            <ul class="nav pull-right">

                                <li>
                                    <a href="<%=basePath%>/genal/login">登录</a>
                                </li>

                                <li class="divider-vertical">
                                </li>
                                <li class="dropdown">
                                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">改签退票<strong
                                            class="caret"></strong></a>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a href="<%=basePath%>/user/order/unFinished">改签</a>
                                        </li>
                                        <li>
                                            <a href="<%=basePath%>/user/order/unFinished">退票</a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </div>

                    </div>
                </div>
            </div>

            <div class="hero-unit">
                <h1>
                    404 NOT FOUND!
                </h1>
            </div>

            <div class="row-fluid">
                <div class="span12" style="margin-top: 30px;text-align: center;background-color: dimgrey;">
                    <address>
                        <strong>集美大学计算机工程学院.</strong><br/>
                        福建,厦门<br/>
                        <abbr title="Phone">author:</abbr> xxxx
                    </address>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
