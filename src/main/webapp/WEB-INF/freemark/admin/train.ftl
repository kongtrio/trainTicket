<link rel="stylesheet" type="text/css" href="${basePath}/css/page.css">
<#--<link rel="stylesheet" type="text/css" href="${basePath}/css/jquery.timepicker.css">-->
<#--<link rel="stylesheet" type="text/css" href="${basePath}/css/bootstrap-datepicker.css">-->
<section>
    <form action="${basePath}/admin/train" method="post" id="query_form">
        <input type="hidden" name="pageNo" id="pageNo" value="1"/>

        <div class="page_title" style="margin: 10px 0px 10px 0px;">
            <h2 class="fl">列车列表</h2>

            <span style="margin-left: 100px;font-style: inherit">搜索条件:</span>
            <input type="text" class="textbox" style="height: 15px" value="<#if param??>${param.sites!""}</#if>"
                   name="sites" placeholder="请输入站点"/>

            <a class="fr top_rt_btn" onclick="javascript:$('#query_form').submit()" style="float: inherit;">查询</a>
        </div>
    </form>
    <table class="table">
        <tr>
            <th>列车编号</th>
            <th>所经站点</th>
            <th>操作</th>
        </tr>

        <tbody id="testDiv">
        <#if trains??>
            <#list trains.content as data>
            <tr>
                <td>
                ${data.trainSerial!""}
                </td>
                <td>
                    <#list data.trainScribeList as x>
                        <p style="margin: 10px 10px">第${x.siteIndex}站:${x.siteName};&nbsp;票价:${x.price}
                            元&nbsp;到站时间:${x.time}</p>
                    </#list>
                </td>
                <td>
                <#--<a href="#"-->
                <#--onclick="showAlterDiv(${data.id?c},'${data.name!''}','${data.users.userName}','${data.telphone!''}','${data.identityCard!''}')">修改联系人</a>-->
                    <a href="${basePath}/admin/delTrain/${data.id?c}" class="inner_btn">删除站点</a>
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
    initPagesWithClick("pageColumn",${trains.number},${trains.totalPages}, pageClickEvent);
    //    $('#testTime').timepicker();
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