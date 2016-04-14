<section class="pop_bg">
    <form action="#" id="addForm" method="post">
        <div class="pop_cont">
            <!--title-->
            <h3>添加商品</h3>
            <!--content-->
            <div class="pop_cont_input">
                <ul>
                    <li>
                        <span>商品名称</span>
                        <input type="text" class="textbox"/>
                    </li>
                    <li>
                        <span class="ttl">类型</span>
                        <select class="select">
                            <option>书籍</option>
                        </select>
                    </li>
                    <li>
                        <span class="ttl">商品原价</span>
                        <input type="text" class="textbox"/>
                    </li>
                    <li>
                        <span class="ttl">商品价格</span>
                        <input type="text" class="textbox"/>
                    </li>
                    <li>
                        <span class="ttl">商品简介</span>
                        <textarea class="textarea" style="height:50px;width:80%;"></textarea>
                    </li>
                </ul>
            </div>
            <!--以pop_cont_text分界-->
            <div class="pop_cont_text">
                这里是文字性提示信息！
            </div>
            <!--bottom:operate->button-->
            <div class="btm_btn">
                <input type="button" value="确认" class="input_btn trueBtn"/>
                <input type="button" value="关闭" class="input_btn falseBtn"/>
            </div>
        </div>
    </form>
</section>