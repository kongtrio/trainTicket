package jmu.edu.cn.control.user;

import jmu.edu.cn.domain.Orders;
import jmu.edu.cn.domain.QueryParam;
import jmu.edu.cn.domain.TrainDetail;
import jmu.edu.cn.service.OrdersService;
import jmu.edu.cn.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by Administrator on 2016/4/12.
 */
@RequestMapping("/user")
@Controller("user_orderController")
public class OrderController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private TrainService trainService;

    @RequestMapping(value = "/order/unFinished")
    public String unFinished(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                             @RequestParam(required = false, defaultValue = "4") Integer pageSize,
                             QueryParam queryParam, Model model, @ModelAttribute("msg") String msg) {
        Page<Orders> unFinishedOrder = ordersService.getUnFinishedOrder(pageNo, pageSize, queryParam);
        model.addAttribute("orders", unFinishedOrder);
        model.addAttribute("param", queryParam);
        return "/user/unFinishedOrder";
    }

    @RequestMapping(value = "/order/finished")
    public String finishedOrder(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                @RequestParam(required = false, defaultValue = "6") Integer pageSize,
                                QueryParam queryParam, Model model) {
        Page<Orders> unFinishedOrder = ordersService.getFinishedOrder(pageNo, pageSize, queryParam);
        model.addAttribute("orders", unFinishedOrder);
        model.addAttribute("param", queryParam);
        return "/user/finishedOrder";
    }

    @RequestMapping(value = "/order/cancel/{orderId}")
    public String cancelOrder(@PathVariable("orderId") Long orderId, RedirectAttributes model) {
        Orders orders = ordersService.getById(orderId);
        if (orders == null) {
            model.addFlashAttribute("msg", "退票失败,请联系管理员");
            return "redirect:/user/order/unFinished";
        }
        ordersService.delete(orders);
        model.addFlashAttribute("msg","退票成功");
        return "redirect:/user/order/unFinished";
    }

}
