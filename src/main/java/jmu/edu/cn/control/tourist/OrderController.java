package jmu.edu.cn.control.tourist;

import jmu.edu.cn.domain.QueryParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/4/12.
 */
@RequestMapping("/tourist")
@Controller("tourist_orderController")
public class OrderController {
    @RequestMapping(value = "/order")
    public String index(QueryParam queryParam, String msg, Model model) {
        return "/user/order";
    }

    @RequestMapping(value = "/order/unFinished")
    public String unFinished(QueryParam queryParam, String msg, Model model) {
        return "/user/unFinishedOrder";
    }

    @RequestMapping(value = "/order/finished")
    public String finishedOrder(QueryParam queryParam, String msg, Model model) {
        return "/user/finishedOrder";
    }


}
