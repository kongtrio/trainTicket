<link rel="stylesheet" type="text/css" href="${basePath}/css/page.css">
<link rel="stylesheet" type="text/css" href="${basePath}/css/jquery-ui/jquery-ui.min.css">
<section>
    <form action="${basePath}/admin/notify" method="POST" id="query_form">
        <input type="hidden" name="pageNo" id="pageNo" value="1"/>

        <div class="page_title" style="margin: 10px 0px 10px 0px;">
            <h2 class="fl">公告列表</h2>

            <span style="margin-left: 100px;font-style: inherit">搜索条件:</span>
            <input type="text" class="textbox" style="height: 15px" value="<#if param??>${param.searchKey!""}</#if>"
                   name="searchKey" placeholder="标题或内容"/>

            <a class="fr top_rt_btn" onclick="javascript:$('#query_form').submit()" style="float: inherit;">查询</a>
            <a class="fr top_rt_btn" onclick="showAddDiv()">添加公告</a>
        </div>
    </form>
    <table class="table">
        <tr>
            <th>标题</th>
            <th>内容</th>
            <th>添加时间</th>
            <th>过期时间</th>
            <th>操作</th>
        </tr>

        <tbody id="testDiv">
        <#if notifys??>
            <#list notifys.content as data>
            <tr>
                <td>
                ${data.title!""}
                </td>
                <td>${data.content!""}</td>
                <td>${data.insertTime?string("yyyy-MM-dd")}</td>
                <td>${data.expireTime?string("yyyy-MM-dd")}</td>
                <td>
                    <a href="#"
                       onclick="showAlterDiv(${data.id?c},'${data.title!''}','${data.content!''}','${data.expireTime?string("yyyy-MM-dd")}')">修改站点</a>
                    <a href="${basePath}/admin/notify/delNotify/${data.id?c}" class="inner_btn">删除该公告</a>
                </td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
    <div id="pageColumn"></div>
</section>
<section class="pop_bg" id="addDiv">
    <form action="${basePath}/admin/notify/addNotify" id="addForm" method="post">
        <div class="pop_cont">
            <!--title-->
            <h3>添加公告</h3>
            <!--content-->
            <div class="pop_cont_input">
                <ul>
                    <li>
                        <span>公告标题</span>
                        <input type="text" class="textbox" name="title" id="add_site" datatype="s2-50"
                               errormsg="标题名至少2个字符,最多50个字符！"/>
                    </li>
                    <li>
                        <span>内容</span>
                        <textarea class="textarea" style="height:50px;width:80%;" datatype="s2-1000"
                                  errormsg="内容至少2个字符,最多1000个字符！"
                                  name="content"></textarea>
                    </li>
                    <li>
                        <span>过期时间</span>
                        <input type="text" class="textbox" name="expireTimeStr" id="add_expire_time"/>
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
    <form action="${basePath}/admin/notify/alterNotify" id="alterForm" method="post">
        <input type="hidden" name="id" id="alter_id">

        <div class="pop_cont">
            <!--title-->
            <h3>修改公告</h3>
            <!--content-->
            <div class="pop_cont_input">
                <ul>
                    <li>
                        <span>公告标题</span>
                        <input type="text" class="textbox" name="title" id="alter_title" datatype="s2-50"
                               errormsg="标题名至少2个字符,最多50个字符！"/>
                    </li>
                    <li>
                        <span>内容</span>
                        <textarea class="textarea" id="alter_content" style="height:50px;width:80%;"
                                  datatype="s2-1000"
                                  errormsg="内容至少2个字符,最多1000个字符！"
                                  name="content"></textarea>
                    </li>
                    <li>
                        <span>过期时间</span>
                        <input type="text" class="textbox" name="expireTimeStr" id="alter_expire_time"/>
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
<#--<script src="${basePath}/js/jquery.timepicker.js"></script>-->
<script src="${basePath}/js/jquery-ui/jquery-ui.min.js"></script>
<script type="text/javascript">
    var query_username = $("#query_username").val();
<#if notifys??>
    initPagesWithClick("pageColumn",${notifys.number},${notifys.totalPages}, pageClickEvent);
</#if>
    $("#add_expire_time").datepicker({"dateFormat": "yy-mm-dd"});
    $("#alter_expire_time").datepicker({"dateFormat": "yy-mm-dd"});
    $("#query_username").change(function () {
        $("#pageNo").val(1);
        $("#query_form").submit();
    });

    function showAddDiv() {
        $("#addDiv").fadeIn();
    }

    function showAlterDiv(id, title, content, expireTime) {
        $("#alter_id").val(id);
        $("#alter_title").val(title);
        $("#alter_content").val(content);
        $("#alter_expire_time").val(expireTime);
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
        }, beforeSubmit: function (curform) {
            //在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
            //这里明确return false的话表单将不会提交;
            var expire_time = $("#add_expire_time").val();
            var flag = true;
            if (expire_time == "") {
                alert("过期时间不能为空");
                return false;
            }
            return flag;
        }
    });
    $("#alterForm").Validform({
        tiptype: function (msg, o, cssctl) {
            var objtip = $("#alter_pop");
            cssctl(objtip, o.type);
            objtip.text(msg);
        }, beforeSubmit: function (curform) {
            //在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
            //这里明确return false的话表单将不会提交;
            var expire_time = $("#alter_expire_time").val();
            var flag = true;
            if (expire_time == "") {
                alert("过期时间不能为空");
                return false;
            }
            return flag;
        }
    });

</script>