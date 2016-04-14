<link rel="stylesheet" type="text/css" href="${basePath}/css/page.css">
<form action="${basePath}/user/contact/list" method="POST" id="query_form">
    <input type="hidden" name="pageNo" id="pageNo" value="1"/>
</form>
<table class="table">
    <tr>
        <th>联系人名字</th>
        <th>联系人电话</th>
        <th>身份证</th>
        <th>所属用户</th>
        <th>操作</th>
    </tr>

    <tbody id="testDiv">
    <#if contacts??>
        <#list contacts.content as data>
        <tr>
            <td>
            ${data.name!""}
            </td>
            <td>${data.telphone!""}</td>
            <td>${data.identityCard!""}</td>
            <td>${data.users.userName}</td>
            <td>
                <a href="${basePath}/user/contact/updateContact?contactId=${data.id?c}">修改联系人</a>
                <a href="${basePath}/user/contact/del/${data.id?c}" class="inner_btn">删除联系人</a>
            </td>
        </tr>
        </#list>
    </#if>
    </tbody>
</table>
<div id="pageColumn"></div>
<script src="${basePath}/js/jquery.myPagination.js"></script>
<script type="text/javascript">
    initPagesWithClick("pageColumn",${contacts.number},${contacts.totalPages}, pageClickEvent);
</script>