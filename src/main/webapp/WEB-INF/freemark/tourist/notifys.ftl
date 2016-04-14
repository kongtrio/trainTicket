<link rel="stylesheet" type="text/css" href="${basePath}/css/page.css">
<form action="${basePath}/notify" method="POST" id="query_form">
    <input type="hidden" name="pageNo" id="pageNo" value="1"/>
</form>
<table class="table">
    <thead>
    <tbody id="testDiv">
    <#if notifys??>
        <#list notifys.content as data>
        <tr>
            <td>
                <a href="${basePath}/notify/${data.id}">${data.title!""}</a>
            </td>
            <td>${data.insertTime?string("yyyy-MM-dd")}</td>
        </tr>
        </#list>
    </#if>
    </tbody>
</table>
<div id="pageColumn"></div>
<script src="${basePath}/js/jquery.myPagination.js"></script>
<#--<script src="${basePath}/js/jquery.timepicker.js"></script>-->
<script src="${basePath}/js/jquery-ui/jquery-ui.min.js"></script>
<script type="text/javascript">
    var query_username = $("#query_username").val();
    initPagesWithClick("pageColumn",${notifys.number},${notifys.totalPages}, pageClickEvent);
</script>