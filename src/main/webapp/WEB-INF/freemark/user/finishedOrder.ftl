<link rel="stylesheet" type="text/css" href="${basePath}/css/page.css">
<link rel="stylesheet" type="text/css" href="${basePath}/css/jquery-ui/jquery-ui.css">
<form class="form-search" action="${basePath}/user/order/unFinished" method="POST" id="query_form">
    <input type="hidden" name="pageNo" id="pageNo" value="1"/>
    <input class="input-medium search-query" type="text" id="beginTime" name="beginTime" placeholder="开始时间"
           value="<#if param??>${param.beginTime!""}</#if>" readonly="readonly"/>
    <input class="input-medium search-query" type="text" id="endTime" name="endTime" placeholder="结束时间"
           value="<#if param??>${param.endTime!""}</#if>" readonly="readonly"/>
    &nbsp;&nbsp;&nbsp;&nbsp;
    根据
    <select name="timeType">
        <option value="orderTime">下单时间</option>
        <option value="trainTime">列车出发时间</option>
    </select>
    &nbsp;
    <button type="submit" class="btn">查找</button>
</form>
<table class="table">
    <tr>
        <th>下单时间</th>
        <th>列车编号</th>
        <th>起始站</th>
        <th>终点站</th>
        <th>开车时间</th>
        <th>到达时间</th>
        <th>历时</th>
        <th>订单总价</th>
        <th>乘客数量</th>
    </tr>

    <tbody id="testDiv">
    <#if orders??>
        <#list orders.content as data>
        <tr id="order_${data.id}">
            <td>${data.time}</td>
            <td>
            ${data.trainDetail.train.trainSerial!""}
            </td>
            <td>${data.beginSite!""}</td>
            <td>${data.endSite!""}</td>
            <td>${data.beginTime}</td>
            <td>${data.endTime}</td>
            <td>${data.useTime}</td>
            <td>${data.price}</td>
            <td>${data.ordersDetails?size} <a style="cursor: pointer"
                                              onclick="showContacts('order_${data.id}','${data.contactFormat}')">详情</a>
            </td>
        </tr>
        </#list>
    </#if>
    </tbody>
</table>
<div id="pageColumn"></div>
<script src="${basePath}/js/jquery-ui/jquery-ui.js"></script>
<script src="${basePath}/js/jquery.myPagination.js"></script>
<script type="text/javascript">
    $("#beginTime").datepicker({"dateFormat": "yy-mm-dd"});
    $("#endTime").datepicker({"dateFormat": "yy-mm-dd"});
    <#if orders??>
    initPagesWithClick("pageColumn",${orders.number},${orders.totalPages}, pageClickEvent);
    </#if>
    function showContacts(orderTrId, content) {
        $(".contact_detail").remove();
        var $tr = $("#" + orderTrId);
        $tr.after("<tr class='contact_detail success'  align='center'><td colspan='10' align='center'>" + content + "</td></tr>");
    }
</script>