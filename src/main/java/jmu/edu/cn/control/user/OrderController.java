package jmu.edu.cn.control.user;

import jmu.edu.cn.control.BaseController;
import jmu.edu.cn.domain.Orders;
import jmu.edu.cn.domain.QueryParam;
import jmu.edu.cn.domain.TrainDetail;
import jmu.edu.cn.domain.Users;
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
 * 处理用户的订单信息的处理器
 */
@RequestMapping("/user")
@Controller("user_orderController")
public class OrderController extends BaseController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private TrainService trainService;

    /**
     * 获取没发车的订单并返回给客户端
     *
     * @param pageNo     第几页
     * @param pageSize   页面几条数据
     * @param queryParam 查询参数
     * @return
     */
    @RequestMapping(value = "/order/unFinished")
    public String unFinished(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                             @RequestParam(required = false, defaultValue = "4") Integer pageSize,
                             QueryParam queryParam, Model model, @ModelAttribute("msg") String msg) {
        Users user = (Users) request.getSession().getAttribute("user");
        queryParam.setUsers(user);
        Page<Orders> unFinishedOrder = ordersService.getUnFinishedOrder(pageNo, pageSize, queryParam);
        model.addAttribute("orders", unFinishedOrder);
        model.addAttribute("param", queryParam);
        return "/user/unFinishedOrder";
    }

    /**
     * 获取已经发车的订单并返回给客户端
     *
     * @param pageNo     第几页
     * @param pageSize   页面几条数据
     * @param queryParam 查询参数
     * @return
     */
    @RequestMapping(value = "/order/finished")
    public String finishedOrder(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                @RequestParam(required = false, defaultValue = "6") Integer pageSize,
                                QueryParam queryParam, Model model) {
        Users user = (Users) request.getSession().getAttribute("user");
        queryParam.setUsers(user);
        Page<Orders> unFinishedOrder = ordersService.getFinishedOrder(pageNo, pageSize, queryParam);
        model.addAttribute("orders", unFinishedOrder);
        model.addAttribute("param", queryParam);
        return "/user/finishedOrder";
    }

    /**
     * 退票操作
     *
     * @param orderId 根据orderId来退票
     * @return
     */
    @RequestMapping(value = "/order/cancel/{orderId}")
    public String cancelOrder(@PathVariable("orderId") Long orderId, RedirectAttributes model) {
        Orders orders = ordersService.getById(orderId);
        if (orders == null) {
            model.addFlashAttribute("msg", "退票失败,请联系管理员");
            return "redirect:/user/order/unFinished";
        }
        ordersService.delete(orders);
        model.addFlashAttribute("msg", "退票成功");
        return "redirect:/user/order/unFinished";
    }

}
