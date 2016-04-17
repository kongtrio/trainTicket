<link rel="stylesheet" type="text/css" href="${basePath}/css/page.css">
<#--<link rel="stylesheet" type="text/css" href="${basePath}/css/jquery.timepicker.css">-->
<#--<link rel="stylesheet" type="text/css" href="${basePath}/css/bootstrap-datepicker.css">-->
<section>
    <form action="${basePath}/admin/trainDetail" method="post" id="query_form" style="margin-bottom: 20px;">
        <input type="hidden" name="pageNo" id="pageNo" value="1"/>

        <div class="page_title" style="margin: 10px 0px 10px 0px;height: 55px;">
            <h2 class="fl">列车列表</h2>

            <span style="margin-left: 100px;font-style: inherit">搜索条件:</span>
            <input type="text" class="textbox" style="height: 15px;width: 80px"
                   value="<#if param??>${param.sites!""}</#if>"
                   name="sites" placeholder="经过站点"/>
            <input type="text" class="textbox" style="height: 15px;width: 80px"
                   value="<#if param??>${param.serial!""}</#if>"
                   name="serial" placeholder="列车编号"/>

            根据发车日期
            <input type="text" class="textbox" style="height: 15px;width: 80px" id="beginTime"
                   value="<#if param??>${param.beginTime!""}</#if>" readonly
                   name="beginTime" placeholder="请输入搜索起始日期"/>
            -
            <input type="text" class="textbox" style="height: 15px;width: 80px" id="endTime"
                   value="<#if param??>${param.endTime!""}</#if>" readonly
                   name="endTime" placeholder="请输入搜索结束日期"/>

            根据列车状态
            <select class="select" name="status"
                    style="width: 50px;margin-bottom: 15px;">
                <option value="all">全部</option>
                <option value="stop" <#if param?? && param.status?? && param.status=="stop">selected</#if>>停运</option>
                <option value="normal"  <#if param?? && param.status?? && param.status=="normal">selected</#if>>正常
                </option>
            </select>
            <a class="fr top_rt_btn" onclick="javascript:$('#query_form').submit()" style="float: inherit;">查询</a>
        </div>
    </form>
    <table class="table">
        <tr>
            <th>列车编号</th>
            <th>发车日期</th>
            <th>当天具体发车时间</th>
            <th>到达终点站时间</th>
            <th>剩余票数</th>
            <th>起始站</th>
            <th>终点站</th>
            <th>列车状态</th>
        </tr>

        <tbody id="testDiv">
        <#if trainDetails??>
            <#list trainDetails.content as data>
            <tr>
                <td>
                ${data.train.trainSerial!""}
                </td>
                <td>${data.time?string("yyyy-MM-dd")}</td>
                <td>
                    <#list data.train.trainScribeList as scribe>
                        <#if scribe.siteIndex == 1>
                    ${scribe.time}
                    </#if>
                    </#list>
                </td>
                <td>
                    <#list data.train.trainScribeList as scribe>
                        <#if scribe.siteIndex == data.train.trainScribeList?size>
                    ${scribe.time}
                    </#if>
                    </#list>
                </td>
                <td>${data.seatNumber}</td>
                <td>
                    <#list data.train.trainScribeList as scribe>
                        <#if scribe.siteIndex == 1>
                    ${scribe.siteName}
                    </#if>
                    </#list>
                </td>
                <td>
                    <#list data.train.trainScribeList as scribe>
                        <#if scribe.siteIndex == data.train.trainScribeList?size>
                    ${scribe.siteName}
                    </#if>
                    </#list>
                </td>
                <td>
                    <#if data.status==1>
                        正常
                        <a href="${basePath}/admin/trainDetail/changeStatus/${data.id?c}?status=0" class="inner_btn">停运</a>
                    <#else>
                        停运
                        <a href="${basePath}/admin/trainDetail/changeStatus/${data.id?c}?status=1" class="inner_btn">恢复</a>
                    </#if>

                </td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
    <div id="pageColumn"></div>
</section>
<section class="pop_bg" id="addDiv">
    <form action="${basePath}/admin/contact/addContacts" id="addForm" method="post">
        <div class="pop_cont">
            <!--title-->
            <h3>添加联系人</h3>
            <!--content-->
            <div class="pop_cont_input">
                <ul>
                    <li>
                        <span>联系人名字</span>
                        <input type="text" class="textbox" name="name" datatype="s2-5"
                               errormsg="联系人名字至少2个字符,最多5个字符！"/>
                    </li>
                    <li>
                        <span>联系人电话</span>
                        <input type="text" class="textbox" name="telphone" datatype="s11-11"
                               errormsg="电话必须是11位的号码！"/>
                    </li>
                    <li>
                        <span>身份证号码</span>
                        <input type="text" class="textbox" name="identityCard" datatype="s18-18"
                               errormsg="身份格式不正确！"/>
                    </li>
                    <li>
                        <span>所属用户</span>
                        <select class="select" name="username" style="width: 100px;margin-left: 14px">
                            <option>请选择用户</option>
                        <#--<#list allUsers as user>-->
                        <#--<option value="${user.userName}">${user.userName}</option>-->
                        <#--</#list>-->
                        </select>
                    </li>
                </ul>
            </div>
            <!--以pop_cont_text分界-->
            <div class="pop_cont_text" id="add_pop" style="color: red">
            </div>
            <!--bottom:operate->button-->
            <div class="btm_btn">
                <input type="submit" value="确认" class="input_btn trueBtn"/>
                <input type="button" value="关闭" id="closeAdd" class="input_btn falseBtn"/>
            </div>
        </div>
    </form>
</section>

<section class="pop_bg" id="alterDiv">
    <form action="${basePath}/admin/contact/alterContacts" id="alterForm" method="post">
        <input type="hidden" name="id" id="alter_id">

        <div class="pop_cont">
            <!--title-->
            <h3>修改联系人</h3>
            <!--content-->
            <div class="pop_cont_input">
                <ul>
                    <li>
                        <span>联系人名字</span>
                        <input type="text" class="textbox" id="alter_name" name="name" datatype="s2-5"
                               errormsg="联系人名字至少2个字符,最多5个字符！"/>
                    </li>
                    <li>
                        <span>联系人电话</span>
                        <input type="text" class="textbox" id="alter_telphone" name="telphone" datatype="s11-11"
                               errormsg="电话必须是11位的号码！"/>
                    </li>
                    <li>
                        <span>身份证号码</span>
                        <input type="text" class="textbox" id="alter_identityCard" name="identityCard" datatype="s18-18"
                               errormsg="身份格式不正确！"/>
                    </li>
                    <li>
                        <span>所属用户</span>
                        <select class="select" name="username" id="alter_username"
                                style="width: 100px;margin-left: 14px">
                        </select>
                    </li>
                </ul>
            </div>
            <!--以pop_cont_text分界-->
            <div class="pop_cont_text" id="alter_pop" style="color: red">
            </div>
            <!--bottom:operate->button-->
            <div class="btm_btn">
                <input type="submit" value="确认" class="input_btn trueBtn"/>
                <input type="button" value="关闭" id="closeAlter" class="input_btn falseBtn"/>
            </div>
        </div>
    </form>
</section>
<!--结束：弹出框效果-->
<script src="${basePath}/js/jquery.myPagination.js"></script>
<#--<script src="${basePath}/js/bootstrap-datepicker.js"></script>-->
<#--<script src="${basePath}/js/jquery.timepicker.js"></script>-->
<script type="text/javascript">
    var query_username = $("#query_username").val();
    <#if trainDetails??>
    initPagesWithClick("pageColumn",${trainDetails.number},${trainDetails.totalPages}, pageClickEvent);
    </#if>
    $("#beginTime").datepicker({"dateFormat": "yy-mm-dd"});
    $("#endTime").datepicker({"dateFormat": "yy-mm-dd"});

    $("#query_username").change(function () {
        $("#pageNo").val(1);
        $("#query_form").submit();
    });

    function showAddDiv() {
        $("#addDiv").fadeIn();
    }

    function showAlterDiv(id, name, username, telphone, idCard) {
        $("#alter_id").val(id);
        $("#alter_name").val(name);
        $("#alter_telphone").val(telphone);
        $("#alter_identityCard").val(idCard);
        $("#alter_username").html("<option value='" + username + "'>" + username + "</option>");
        $("#alterDiv").fadeIn();
    }

    //弹出：取消或关闭按钮
    $("#closeAdd").click(function () {
        $("#addDiv").fadeOut();
    });
    //弹出：取消或关闭按钮
    $("#closeAlter").click(function () {
        $("#alterDiv").fadeOut();
    });

    $("#addForm").Validform({
        tiptype: function (msg, o, cssctl) {
            var objtip = $("#add_pop");
            cssctl(objtip, o.type);
            objtip.text(msg);
        }
    });
    $("#alterForm").Validform({
        tiptype: function (msg, o, cssctl) {
            var objtip = $("#alter_pop");
            cssctl(objtip, o.type);
            objtip.text(msg);
        }
    });


</script>