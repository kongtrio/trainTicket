<link rel="stylesheet" type="text/css" href="${basePath}/css/page.css">
<link rel="stylesheet" type="text/css" href="${basePath}/css/jquery-ui/jquery-ui.min.css">
<section>
    <form action="${basePath}/admin/advice" method="POST" id="query_form">
        <input type="hidden" name="pageNo" id="pageNo" value="1"/>

        <div class="page_title" style="margin: 10px 0px 10px 0px;">
            <h2 class="fl">投诉建议</h2>

            <span style="margin-left: 100px;font-style: inherit">搜索条件:</span>
            <input type="text" class="textbox" style="height: 15px" value="<#if param??>${param.username!""}</#if>"
                   name="username" placeholder="投诉人姓名"/>

            <a class="fr top_rt_btn" onclick="javascript:$('#query_form').submit()" style="float: inherit;">查询</a>
        </div>
    </form>
    <table class="table">
        <tr>
            <th>姓名</th>
            <th>邮箱</th>
            <th>投诉时间</th>
            <th>内容</th>
        </tr>

        <tbody id="testDiv">
        <#if advices??>
            <#list advices.content as data>
            <tr>
                <td>
                ${data.username!""}
                </td>
                <td>${data.email!""}</td>
                <td>${data.insertTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                <td>${data.content!""}</td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
    <div id="pageColumn"></div>
</section>
<!--结束：弹出框效果-->
<script src="${basePath}/js/jquery.myPagination.js"></script>
<#--<script src="${basePath}/js/jquery.timepicker.js"></script>-->
<script src="${basePath}/js/jquery-ui/jquery-ui.min.js"></script>
<script type="text/javascript">
    var query_username = $("#query_username").val();
    <#if advices??>
    initPagesWithClick("pageColumn",${advices.number},${advices.totalPages}, pageClickEvent);
    </#if>
</script>