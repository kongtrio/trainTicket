<link rel="stylesheet" type="text/css" href="${basePath}/css/page.css">
<section>
    <form action="${basePath}/admin/users" method="POST" id="query_form">
        <input type="hidden" name="pageNo" id="pageNo" value="1"/>

        <div class="page_title">
            <h2 class="fl">用户列表</h2>

            <span style="margin-left: 100px;font-style: inherit">搜索条件:</span>
            <input type="text" class="textbox" style="height: 15px" value="<#if param??>${param.username!""}</#if>"
                   name="username" placeholder="请输入用户名"/>

            <a class="fr top_rt_btn" onclick="javascript:$('#query_form').submit()" style="float: inherit;">查询</a>

            <a class="fr top_rt_btn" onclick="showAddDiv()">添加用户</a>
        </div>
    </form>
    <table class="table">
        <tr>
            <th>用户名</th>
            <th>电话</th>
            <th>注册时间</th>
            <th>最后登录时间</th>
            <th>账号状态</th>
            <th>账户角色</th>
            <th>备注</th>
            <th>操作</th>
        </tr>

        <tbody id="testDiv">
        <#list users.content as data>
        <tr>
            <td>
            ${data.userName}
            </td>
            <td>${data.telphone!""}</td>
            <td>${data.registerTime}</td>
            <td>${data.lastLoginTime}</td>
            <td><#if data.status==1>正常<#else>异常</#if></td>
            <td>
                <#list data.roles as role>
                    ${role.remark}
                </#list>
            </td>
            <td>${data.remark!""}</td>
            <td>
                <a href="#"
                   onclick="showAlterDiv('${data.userName}','${data.password}','${data.telphone!''}','${data.remark!''}')">修改用户</a>
                <a href="${basePath}/admin/users/delUsers/${data.id?c}" class="inner_btn">删除用户</a>
            </td>
        </tr>
        </#list>
        </tbody>
    </table>
    <div id="pageColumn"></div>
</section>
<section class="pop_bg" id="addDiv">
    <form action="${basePath}/admin/users/addUsers" id="addForm" method="post">
        <div class="pop_cont">
            <!--title-->
            <h3>添加用户</h3>
            <!--content-->
            <div class="pop_cont_input">
                <ul>
                    <li>
                        <span>用户名称</span>
                        <input type="text" class="textbox" id="username" name="userName" datatype="s4-16"
                               errormsg="用户名至少4个字符,最多16个字符！"/>
                    </li>
                    <li>
                        <span class="ttl">用户密码</span>
                        <input type="password" class="textbox" name="password" datatype="*4-16"
                               errormsg="密码至少4个字符,最多16个字符！"/>
                    </li>
                    <li>
                        <span class="ttl">电话号码</span>
                        <input type="text" class="textbox" name="telphone"/>
                    </li>
                    <li>
                        <span class="ttl">用户描述</span>
                        <textarea class="textarea" style="height:50px;width:80%;" name="remark"></textarea>
                    </li>
                </ul>
            </div>
            <!--以pop_cont_text分界-->
            <div class="pop_cont_text" id="add_pop" style="color: red">
            </div>
            <!--bottom:operate->button-->
            <div class="btm_btn">
                <input type="submit" value="确认" class="input_btn trueBtn"/>
                <input type="button" value="关闭" class="input_btn falseBtn"/>
            </div>
        </div>
    </form>
</section>

<section class="pop_bg" id="alterDiv">
    <form action="${basePath}/admin/users/alterUsers" id="alterForm" method="post">
        <div class="pop_cont">
            <!--title-->
            <h3>修改用户</h3>
            <!--content-->
            <div class="pop_cont_input">
                <ul>
                    <li>
                        <span>用户名称</span>
                        <input type="text" class="textbox" id="alter_username" name="userName" readonly datatype="s4-16"
                               errormsg="用户名至少4个字符,最多16个字符！"/>
                    </li>
                    <li>
                        <span class="ttl">用户密码</span>
                        <input type="password" class="textbox" id="alter_password" name="password" datatype="*4-16"
                               errormsg="密码至少4个字符,最多16个字符！"/>
                    </li>
                    <li>
                        <span class="ttl">电话号码</span>
                        <input type="text" class="textbox" id="alter_telphone" name="telphone"/>
                    </li>
                    <li>
                        <span class="ttl">用户描述</span>
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
                <input type="button" value="关闭" class="input_btn falseBtn" id="closeAlter"/>
            </div>
        </div>
    </form>
</section>
<!--结束：弹出框效果-->
<script src="${basePath}/js/jquery.myPagination.js"></script>
<script type="text/javascript">
    initPages("pageColumn",${users.number},${users.totalPages}, pageClickEvent);

    function showAddDiv() {
        $("#addDiv").fadeIn();
    }

    function showAlterDiv(username, password, telphone, remark) {
        $("#alter_username").val(username);
        $("#alter_password").val(password);
        $("#alter_telphone").val(telphone);
        $("#alter_remark").val(remark);
        $("#alterDiv").fadeIn();
    }

    //弹出：取消或关闭按钮
    $(".falseBtn").click(function () {
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
            var username = $("#username").val();
            var flag = false;
//                    http://www.cnblogs.com/jayleke/archive/2012/08/10/2633174.html
            $.ajax({
                type: 'POST',
                url: '${basePath}/genal/checkUsernameExist',
                dataType: 'json',
                data: {username: username},
                async: false,
                success: function (data) {
                    if (data == true || data == "true") {
                        flag = true;
                    } else {
                        $("#add_pop").html("用户名已存在");
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
        }
    });

</script>