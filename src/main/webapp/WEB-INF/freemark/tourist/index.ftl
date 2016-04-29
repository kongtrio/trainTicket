<link rel="stylesheet" type="text/css" href="${basePath}/css/jquery-ui/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="${basePath}/css/jquery-ui/chosen.css">
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
            <input type="hidden" id="o_begin_site" name="beginSite"/>
            <input type="hidden" id="o_end_site" name="endSite"/>
            <input type="hidden" id="o_begin_time" name="beginTime"/>
            <input type="hidden" id="o_end_time" name="endTime"/>
            <input type="hidden" id="o_use_time" name="useTime"/>
            <input type="hidden" id="traindetail_id" name="id"/>
            <input type="hidden" id="orderId" name="orderId"
                   value="<#if orderId?? && orderId!=0>${orderId}<#else>0</#if>"/>
        </form>
        <form class="form-search" method="post" action="${basePath}/tourist">
            <select class="chosen_site" data-placeholder="请选择起始站" name="beginSite" id="begin_site">
                <option value=""></option>
            </select>
            <select class="chosen_site" data-placeholder="请选择终点站" name="endSite" id="end_site">
                <option value=""></option>
            </select>
        <#--<input class="input-medium search-query" id="begin_site" name="beginSite" type="text" data-toggle="modal"-->
        <#--value="<#if param??>${param.beginSite!""}</#if>" placeholder="起始站"/>-->
        <#--<input class="input-medium search-query" id="end_site" name="endSite" type="text"-->
        <#--value="<#if param??>${param.endSite!""}</#if>" placeholder="终点站"/>-->
            <input class="input-medium search-query" type="text" id="time" name="time" placeholder="时间"
                   style="margin-bottom: 20px"
                   value="<#if param??>${param.time!""}<#else>${date!''}</#if>" readonly="readonly"/>
            <input type="hidden" id="orderId" name="orderId" value="<#if orderId??>${orderId}<#else>0</#if>"/>
            <button type="submit" class="btn" id="search_train" style="margin-bottom: 20px">查找对应车辆</button>
        <#if orderId?? && orderId!=0> <span style="float: right;color:red;font-size: 20px">改签车票</span></#if>

        </form>
    <#if trains?? && trains?size gt 0>
        <table class="table" style="margin-bottom: 20px">
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
                <#list trains as trainReport>
                <tr>
                    <td>${trainReport.trainDetail.train.trainSerial!""}</td>
                    <td>${trainReport.beginSite!""}</td>
                    <td>${trainReport.endSite!""}</td>
                    <td>${trainReport.beginTime!""}</td>
                    <td>${trainReport.endTime!""}</td>
                    <td>${trainReport.useTime!""}</td>
                    <td>${trainReport.seatNum!""}</td>
                    <td><a href="#"
                           onclick="orderTicket('${trainReport.beginSite!""}','${trainReport.endSite!""}',
                                   '${trainReport.beginTime!""}','${trainReport.endTime!""}','${trainReport.useTime!""}',
                                   '${trainReport.trainDetail.id!""}')"><#if orderId?? && orderId!=0>改签<#else>
                        预订</#if></a></td>
                </tr>
                </#list>
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
    <#else>
        <h3>没有找到列车</h3>
    </#if>
    </div>
</div>
<script src="${basePath}/js/jquery-ui/jquery-ui.js"></script>
<script src="${basePath}/js/jquery-ui/chosen.jquery.js"></script>
<script type="text/javascript">
    var db_site_array = [];
    var searchBeginSite = '<#if param??>${param.beginSite!""}</#if>';
    var searchEndSite = '<#if param??>${param.endSite!""}</#if>';
    $.ajax({
        url: "${basePath}/admin/train/getSites",
        success: function (data) {
            db_site_array = eval("(" + data + ")");
            var $beginSite = $("#begin_site");
            var $endSite = $("#end_site");
            for (var i = 0; i < db_site_array.length; i++) {
                var $htmlBegin = $("<option value='" + db_site_array[i] + "'>" + db_site_array[i] + "</option>");
                var $htmlEnd = $("<option value='" + db_site_array[i] + "'>" + db_site_array[i] + "</option>");
                if (searchBeginSite != undefined && db_site_array[i] == searchBeginSite) {
                    $htmlBegin.attr("selected", "selected");
                }
                if (searchEndSite != undefined && db_site_array[i] == searchEndSite) {
                    $htmlEnd.attr("selected", "selected");
                }
                $beginSite.append($htmlBegin);
                $endSite.append($htmlEnd);
            }
            $(".chosen_site").chosen();
        }
    });
    $("#time").datepicker({"dateFormat": "yy-mm-dd", minDate: 0, maxDate: "+2M"});

    $("#search_train").click(function () {
        var beginSite = $("#begin_site").val();
        var endSite = $("#end_site").val();
        if (!contains(db_site_array, beginSite)) {
            openAlert("该起始站点不存在");
            return false;
        }
        if (!contains(db_site_array, endSite)) {
            openAlert("该终点站不存在");
            return false;
        }
    });

    function orderTicket(beginSite, endSite, beginTime, endTime, useTime, id) {
        beginTime = $("#time").val() + " " + beginTime;
        endTime = $("#time").val() + " " + endTime;
        $("#o_begin_site").val(beginSite);
        $("#o_end_site").val(endSite);
        $("#o_begin_time").val(beginTime);
        $("#o_end_time").val(endTime);
        $("#o_use_time").val(useTime);
        $("#traindetail_id").val(id);
        $("#order_form").submit();
    }

    function openAlert(msg) {
        $("#msg_content").html(msg);
        $("#dialog-message").dialog({
            height: "auto",
            modal: true,
            buttons: {
                Ok: function () {
                    $(this).dialog("close");
                }
            }
        });
    }
</script>
