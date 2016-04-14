package jmu.edu.cn.control.user;

import jmu.edu.cn.domain.QueryParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/4/12.
 */
@RequestMapping("/user")
@Controller("user_orderController")
public class OrderController {
    @RequestMapping(value = "/order/unFinished")
    public String unFinished(QueryParam queryParam, String msg, Model model) {
        return "/user/unFinishedOrder";
    }

    @RequestMapping(value = "/order/finished")
    public String finishedOrder(QueryParam queryParam, String msg, Model model) {
        return "/user/finishedOrder";
    }
}
