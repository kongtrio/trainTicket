<link rel="stylesheet" type="text/css" href="${basePath}/css/page.css">
<link rel="stylesheet" type="text/css" href="${basePath}/css/jquery.timepicker.css">
<link rel="stylesheet" type="text/css" href="${basePath}/css/jquery-ui/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="${basePath}/css/jquery-ui/chosen.css">
<form action="${basePath}/admin/addTrain" method="post" id="selectForm">
    <input id="trainDetail" name="tarinDetail" type="hidden">
    <input id="trainSerialForm" name="trainSerial" type="hidden">
</form>
<section>
    <h2><strong style="color:grey;">增加一个站点</strong></h2>
    <ul class="ulColumn2">
        <li>
            <span class="item_name" style="width:120px;">站点：</span>
            <select class="chosen_site" data-placeholder="请选择站点" name="beginSite" id="site_name" style="width:150px;">
                <option value=""></option>
            </select>
        </li>

        <#--<li>-->
            <#--<span class="item_name" style="width:120px;">站点：</span>-->
            <#--<input type="text" id="site_name" class="textbox textbox_295" placeholder="站点信息..."/>-->
        <#--</li>-->
        <li>
            <span class="item_name" style="width:120px;">到站时间：</span>
            <input type="text" id="site_time" class="textbox textbox_295"/>
        </li>
        <li>
            <span class="item_name" style="width:120px;">该站票价：</span>
            <input type="text" id="site_price" class="textbox textbox_295"/>
            <span class="errorTips">填的是起始站到该站的票价</span>
        </li>
        <li>
            <span class="item_name" style="width:120px;"></span>
            <input type="submit" value="添加" class="link_btn" onclick="addSite()"/>
        </li>
    </ul>
</section>

<section>
    <div class="page_title" style="margin: 10px 0px 10px 0px;">
        <h2 class="fl">列车详情</h2>

        <h2 class="fl" style="margin-left:375px;">列车编号:</h2>
        <input type="text" id="train_serial" class="textbox textbox_295" style="height: 15px;width: 150px"/>
        <a class="fr top_rt_btn" onclick="submitForm()">保存该列列车信息</a>
    </div>
    <table class="table">
        <tr>
            <th>站点</th>
            <th>票价</th>
            <th>到站时间</th>
            <th>站次</th>
            <th>操作</th>
        </tr>

        <tbody id="site_table">
        </tbody>
    </table>
</section>
<!--结束：弹出框效果-->
<script src="${basePath}/js/jquery.myPagination.js"></script>
<script src="${basePath}/js/jquery-ui/jquery-ui.js"></script>
<script src="${basePath}/js/jquery.timepicker.js"></script>
<script src="${basePath}/js/jquery-ui/chosen.jquery.js"></script>
<script type="text/javascript">
    var site_array = [];
    var site_name = [];
    var db_site_array = [];
    $.ajax({
        url: "${basePath}/admin/train/getSites",
        success: function (data) {
            db_site_array = eval("(" + data + ")");
            var $beginSite = $("#site_name");
            for (var i = 0; i < db_site_array.length; i++) {
                var $htmlBegin = $("<option value='" + db_site_array[i] + "'>" + db_site_array[i] + "</option>");
                $beginSite.append($htmlBegin);
            }
            $(".chosen_site").chosen();
        }
    });

    $('#site_time').timepicker({
        'step': function (i) {
            return 1;
        }, 'timeFormat': 'H:i:s'
    });

    function del_site(tr_index) {
        if (tr_index > 1) {
            $("#debBut_" + (tr_index - 1)).append("<a onclick='del_site(\"" + (tr_index - 1) + "\")'>删除该站点</a>");
        }
        $("#tr_" + tr_index).remove();
        site_array.pop();
    }

    function addSite() {
        var index = 1;
        var pre_obj;
        if (site_array.length > 0) {
            index = site_array.length + 1;
            pre_obj = site_array[site_array.length - 1];
        }
        var site_name = $("#site_name").val();
        var site_time = $("#site_time").val();
        var site_price = $("#site_price").val();


        if (isBlank(site_name)) {
            alert("站点不正确");
            return false;
        }

        for (var i = 0; i < site_array.length; i++) {
            if (site_name == site_array[i].site_name) {
                alert("该站点已经添加过了");
                return false;
            }
        }

        if (!contains(db_site_array, site_name)) {
            alert("填写站点不存在");
            return false;
        }

        var re = /^(([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])(:([0-5]?[0-9]))?$/;
        var match = site_time.match(re);
        if (isBlank(site_time) || match == null) {
            alert("时间格式不正确");
            return false;
        }

        if (pre_obj != undefined) {
            var time = parseStr2Time(pre_obj.time);
            var input_time = parseStr2Time(site_time);
            if (time >= input_time) {
                alert("该站的到站时间必须比上一站的到站时间晚!");
                return false;
            }
        }
        re = /^[0-9]*$/;
        match = site_price.match(re);
        if (isBlank(site_price) || match == null) {
            alert("票价必须是正整数");
            return false;
        }

        if (pre_obj != undefined) {
            if (parseInt(site_price) <= pre_obj.price) {
                alert("该站的票价不能小于上一站的票价!上一站票价" + pre_obj.price + " 你填写的票价" + site_price);
                return false;
            }
        }

        var index_site = "首站";
        if (index > 1) {
            index_site = "第" + index + "站";
        }
        var $tr = $("<tr id='tr_" + index + "'></tr>");
        $tr.append("<td>" + site_name + "</td>");
        $tr.append("<td>" + site_price + "</td>");
        $tr.append("<td>" + site_time + "</td>");
        $tr.append("<td>" + index_site + "</td>");
        $tr.append("<td id='debBut_" + index + "'><a onclick='del_site(\"" + index + "\")'>删除该站点</a></td>");
        $("#site_table").append($tr);
        if (index > 1) {
            $("#debBut_" + (index - 1)).html("");
        }

        var obj = new Object();
        obj.siteName = site_name;
        obj.time = site_time;
        obj.price = site_price;
        obj.siteIndex = index;
        site_array.push(obj);

        $("#site_name").val("");
        $("#site_time").val("");
        $("#site_price").val("");
    }

    function submitForm() {
        var val = $("#train_serial").val();
        if (val == "") {
            alert("列车号不能为空");
            return false;
        }
        $("#trainSerialForm").val(val);
        var stringify = JSON.stringify(site_array);
        $("#trainDetail").val(stringify);
        $("#selectForm").submit();
    }

</script>