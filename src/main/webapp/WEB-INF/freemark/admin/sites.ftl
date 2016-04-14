<link rel="stylesheet" type="text/css" href="${basePath}/css/page.css">
<section>
    <form action="${basePath}/admin/sites" method="POST" id="query_form">
        <input type="hidden" name="pageNo" id="pageNo" value="1"/>

        <div class="page_title" style="margin: 10px 0px 10px 0px;">
            <h2 class="fl">站点列表</h2>

            <span style="margin-left: 100px;font-style: inherit">搜索条件:</span>
            <input type="text" class="textbox" style="height: 15px" value="<#if param??>${param.sites!""}</#if>"
                   name="sites" placeholder="请输入站点"/>

            <input type="text" class="textbox" style="height: 15px" value="<#if param??>${param.province!""}</#if>"
                   name="province" placeholder="请输入省份"/>

            <a class="fr top_rt_btn" onclick="javascript:$('#query_form').submit()" style="float: inherit;">查询</a>
            <a class="fr top_rt_btn" onclick="showAddDiv()">添加站点</a>
        </div>
    </form>
    <table class="table">
        <tr>
            <th>站点名</th>
            <th>所在省份</th>
            <th>添加时间</th>
            <th>备注</th>
            <th>操作</th>
        </tr>

        <tbody id="testDiv">
        <#if sites??>
            <#list sites.content as data>
            <tr>
                <td>
                ${data.site!""}
                </td>
                <td>${data.province!""}</td>
                <td>${data.insertTime!""}</td>
                <td>${data.remark!""}</td>
                <td>
                    <a href="#"
                       onclick="showAlterDiv(${data.id?c},'${data.site!''}','${data.province!''}','${data.remark!''}')">修改站点</a>
                    <a href="${basePath}/admin/sites/delSites/${data.id?c}" class="inner_btn">删除该站点</a>
                </td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
    <div id="pageColumn"></div>
</section>
<section class="pop_bg" id="addDiv">
    <form action="${basePath}/admin/sites/addSites" id="addForm" method="post">
        <div class="pop_cont">
            <!--title-->
            <h3>添加站点</h3>
            <!--content-->
            <div class="pop_cont_input">
                <ul>
                    <li>
                        <span>站点名称</span>
                        <input type="text" class="textbox" name="site" id="add_site" datatype="s2-10"
                               errormsg="站点名至少2个字符,最多10个字符！"/>
                    </li>
                    <li>
                        <span>所在省份</span>
                        <input type="text" class="textbox" name="province" datatype="s2-10"
                               errormsg="省份至少2个字符,最多10个字符！"/>
                    </li>
                    <li>
                        <span>站点备注</span>
                        <textarea class="textarea" style="height:50px;width:80%;"
                                  name="remark"></textarea>
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
    <form action="${basePath}/admin/sites/alterSites" id="alterForm" method="post">
        <input type="hidden" name="id" id="alter_id">

        <div class="pop_cont">
            <!--title-->
            <h3>修改站点</h3>
            <!--content-->
            <div class="pop_cont_input">
                <ul>
                    <li>
                        <span>站点名称</span>
                        <input type="text" id="alter_site" class="textbox" name="site" datatype="s2-10"
                               errormsg="站点名至少2个字符,最多10个字符！"/>
                    </li>
                    <li>
                        <span>所在省份</span>
                        <input type="text" id="alter_province" class="textbox" name="province" datatype="s2-10"
                               errormsg="省份至少2个字符,最多10个字符！"/>
                    </li>
                    <li>
                        <span>站点备注</span>
                        <textarea class="textarea" id="alter_remark" style="height:50px;width:80%;"
                                  name="remark"></textarea>
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
<script type="text/javascript">
    var query_username = $("#query_username").val();
    initPagesWithClick("pageColumn",${sites.number},${sites.totalPages}, pageClickEvent);

    $("#query_username").change(function () {
        $("#pageNo").val(1);
        $("#query_form").submit();
    });

    function showAddDiv() {
        $("#addDiv").fadeIn();
    }

    function showAlterDiv(id, site, province, remark) {
        $("#alter_id").val(id);
        $("#alter_site").val(site);
        $("#alter_province").val(province);
        $("#alter_remark").val(remark);
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
            var name = $("#add_site").val();
            var flag = false;
//                    http://www.cnblogs.com/jayleke/archive/2012/08/10/2633174.html
            $.ajax({
                type: 'POST',
                url: '${basePath}/admin/sites/checkSiteExist',
                dataType: 'json',
                data: {site: name},
                async: false,
                success: function (data) {
                    if (data == true || data == "true") {
                        flag = true;
                    } else {
                        $("#add_pop").html("该站点已存在");
                    }
                }
            });
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
            var name = $("#alter_site").val();
            var flag = false;
//                    http://www.cnblogs.com/jayleke/archive/2012/08/10/2633174.html
            $.ajax({
                type: 'POST',
                url: '${basePath}/admin/sites/checkSiteExist',
                dataType: 'json',
                data: {site: name},
                async: false,
                success: function (data) {
                    if (data == true || data == "true") {
                        flag = true;
                    } else {
                        $("#alter_pop").html("该站点已存在");
                    }
                }
            });
            return flag;
        }
    });

</script>