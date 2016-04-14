<link rel="stylesheet" type="text/css" href="${basePath}/css/jquery-ui/jquery-ui.css">
<div class="row">
    <div class="span3" style="background-color: currentColor;border-radius: 5%;">
        <h3 style="color: cornsilk;">
            系统公告
        </h3>
        <ul>
        <#if notifys??>
            <#list notifys as data>
                <li>
                    <a href="${basePath}/notify/${data.id}"><span style="color:red">[公告]</span>${data.title!""}</a>
                </li>
            </#list>
        </#if>
        </ul>
    </div>
    <div class="span9">
        <form id="order_form" class="form-search" method="post" action="${basePath}/tourist/order">
        </form>
        <form class="form-search" method="post" action="${basePath}/tourist">
            <input class="input-medium search-query" id="begin_site" name="beginSite" type="text"
                   value="<#if param??>${param.beginSite!""}</#if>" placeholder="起始站"/>
            <input class="input-medium search-query" id="end_site" name="endSite" type="text"
                   value="<#if param??>${param.endSite!""}</#if>" placeholder="终点站"/>
            <input class="input-medium search-query" type="text" id="time" name="time" placeholder="时间"
                   value="<#if param??>${param.time!""}<#else>${date!''}</#if>" readonly="readonly"/>
            <button type="submit" class="btn">查找对应车辆</button>
        </form>
        <table class="table">
            <thead>
            <tr>
                <th>
                    列车编号
                </th>
                <th>
                    出发站
                </th>
                <th>
                    到达站
                </th>
                <th>
                    出发时间
                </th>
                <th>
                    到达时间
                </th>
                <th>
                    历时
                </th>
                <th>
                    余票
                </th>
                <th>
                    操作
                </th>
            </tr>
            </thead>
            <tbody>
            <#if trains??>
                <#list trains as trainReport>
                <tr>
                    <td>${trainReport.trainDetail.train.trainSerial!""}</td>
                    <td>${trainReport.beginSite!""}</td>
                    <td>${trainReport.endSite!""}</td>
                    <td>${trainReport.beginTime!""}</td>
                    <td>${trainReport.endTime!""}</td>
                    <td>${trainReport.useTime!""}</td>
                    <td>${trainReport.trainDetail.seatNumber!""}</td>
                    <td><a href="#" onclick="orderTicket()">预订</a></td>
                </tr>
                </#list>
            </#if>
            <#--<tr class="success">-->
            <#--<td>-->
            <#--1-->
            <#--</td>-->
            <#--<td>-->
            <#--TB - Monthly-->
            <#--</td>-->
            <#--<td>-->
            <#--01/04/2012-->
            <#--</td>-->
            <#--<td>-->
            <#--Approved-->
            <#--</td>-->
            <#--<td>-->
            <#--Approved-->
            <#--</td>-->
            <#--<td>-->
            <#--Approved-->
            <#--</td>-->
            <#--<td>-->
            <#--Approved-->
            <#--</td>-->
            <#--<td>-->
            <#--Approved-->
            <#--</td>-->
            <#--</tr>-->
            </tbody>
        </table>
    </div>
</div>
<script src="${basePath}/js/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript">
    var db_site_array = [];
    $.ajax({
        url: "${basePath}/admin/train/getSites",
        success: function (data) {
            db_site_array = eval("(" + data + ")");
            $("#begin_site").autocomplete({
                source: db_site_array,
                autoFocusType: true
            });
            $("#end_site").autocomplete({
                source: db_site_array,
                autoFocusType: true
            });
        }
    });
    $("#time").datepicker({"dateFormat": "yy-mm-dd", minDate: 0, maxDate: "+2M"});
    function orderTicket(){
        $("#order_form").submit();
    }
</script>
