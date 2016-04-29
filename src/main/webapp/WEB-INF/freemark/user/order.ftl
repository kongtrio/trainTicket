<form action="${basePath}/tourist/order/create" method="post" id="order_form">
    <input type="hidden" name="beginSite" value="${beginSite!''}"/>
    <input type="hidden" name="endSite" value="${endSite!''}"/>
    <input type="hidden" name="datefrom" id="beginTime" value="${beginTime!''}"/>
    <input type="hidden" name="dateto" id="endTime" value="${endTime!''}"/>
    <input type="hidden" name="useTime" value="${useTime!''}"/>
    <input type="hidden" name="price" id="price"/>
    <input type="hidden" name="trainDetailId" value="${train.id!''}"/>
    <input type="hidden" name="contactIds" id="contacts_id"/>
    <input type="hidden" id="orderId" name="orderId" value="<#if orderId?? && orderId!=0>${orderId}<#else>0</#if>"/>
</form>
<div class="span12">
    <h1 align="center">
        火车票订单
    </h1>
    <hr/>
    <p align="center">
        <pre style="font-size: 18px">

        出发站:<strong>${beginSite!""}</strong>    终点站:<strong>${endSite!""}</strong>    出发时间:<strong>${beginTime!""}</strong>   到达时间:<strong>${endTime!""}</strong>

        所需时间:<strong>${useTime!""}</strong>    票价:<strong>${price!""}元</strong>

        列车编号:<strong>${train.trainSerial!""}</strong>
        </pre>
    </p>
    <hr/>
    <ul class="inline">
        <li>
            <strong style="font-size: 18px">选择联系人:</strong>
        </li>
    <#if user??>
        <#list user.contacts as contact>
            <li>
                <input type="checkbox" class="contact_check" value="${contact.id}"/>
            ${contact.name}
            </li>
        </#list>
    </#if>
    </ul>
    <a style="font-size: 20px;margin-left: 10px;" href="${basePath}/user/contact/list">联系人管理</a>
    <hr/>
    <button class="btn" type="button" onclick="toOrder()"><#if orderId?? && orderId!=0>改签<#else>订票</#if></button>
    <span style="float: right;margin-right: 40px;font-size: 20px;color: crimson;">总价:<em id="total_price">0</em>元</span>
</div>
<script type="text/javascript">
    function toOrder() {
        init();
        var ids = $("#contacts_id").val();
        var price = $("#total_price").html();
        if (ids == "" || price == 0) {
            alert("请至少选择一个乘客");
            return false;
        }
        $("#order_form").submit();
    }

    $(document).ready(function () {
        $(".contact_check").click(function () {
            init();
        });
    });

    function init() {
        var once_price = '${price}';
        var check_num = 0;
        var check_id = "";
        check_num = 0;
        check_id = "";
        $(".contact_check").each(function () {
            console.log(this.checked);
            if (this.checked == true) {
                check_num++;
                check_id += this.value + ",";
            }
        });
        $("#contacts_id").val(check_id);
        $("#price").val(check_num * once_price);
        $("#total_price").html(check_num * once_price);
    }

</script>