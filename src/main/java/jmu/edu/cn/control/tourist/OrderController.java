package jmu.edu.cn.control.tourist;

import com.google.common.collect.Lists;
import jmu.edu.cn.control.BaseController;
import jmu.edu.cn.domain.*;
import jmu.edu.cn.service.OrdersService;
import jmu.edu.cn.service.TrainService;
import jmu.edu.cn.service.UsersService;
import jmu.edu.cn.util.DateUtil;
import jmu.edu.cn.util.SeatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by Administrator on 2016/4/12.
 */
@RequestMapping("/tourist")
@Controller("tourist_orderController")
public class OrderController extends BaseController {
    @Autowired
    private TrainService trainService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private OrdersService ordersService;

    @RequestMapping(value = "/order")
    public String index(QueryParam queryParam, Long orderId, Model model) {
        Users user = (Users) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/tourist";
        }
        Users tmpUser = usersService.getById(user.getId());
        TrainDetail trainDetail = trainService.getDetailById(queryParam.getId());
        Train train = trainDetail.getTrain();
        List<TrainScribe> trainScribeList = train.getTrainScribeList();
        long beginPrice = 0;
        long endPrice = 0;
        for (TrainScribe trainScribe : trainScribeList) {
            if (trainScribe.getSiteName().equals(queryParam.getBeginSite())) {
                beginPrice = trainScribe.getPrice();
            }
            if (trainScribe.getSiteName().equals(queryParam.getEndSite())) {
                endPrice = trainScribe.getPrice();
            }
        }
        String useTime = queryParam.getUseTime();
        String[] split = useTime.split(":");
        String cnTime = "";
        if (Integer.parseInt(split[0]) == 0) {
            cnTime = split[1] + "分钟";
        } else {
            cnTime = split[0] + "个小时" + split[1] + "分钟";
        }
        model.addAttribute("beginSite", queryParam.getBeginSite());
        model.addAttribute("endSite", queryParam.getEndSite());
        model.addAttribute("beginTime", queryParam.getBeginTime());
        model.addAttribute("endTime", queryParam.getEndTime());
        model.addAttribute("useTime", cnTime);
        model.addAttribute("price", endPrice - beginPrice);
        model.addAttribute("train", trainDetail);
        model.addAttribute("orderId", orderId);
        model.addAttribute("user", tmpUser);
        return "/user/order";
    }

    @RequestMapping(value = "/order/create")
    public String createOrder(Orders orders, QueryParam queryParam, long orderId, RedirectAttributes model) {
        Users user = (Users) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/tourist";
        }

        String datefrom = queryParam.getDatefrom();
        String dateto = queryParam.getDateto();
        orders.setBeginTime(DateUtil.parseDate(datefrom, "yyyy-MM-dd HH:mm:ss"));
        orders.setEndTime(DateUtil.parseDate(dateto, "yyyy-MM-dd HH:mm:ss"));
        long trainDetailId = queryParam.getTrainDetailId();
        String contactIds = queryParam.getContactIds();
        if (contactIds.length() > 0) {
            contactIds = contactIds.substring(0, contactIds.length() - 1);
        }
        TrainDetail trainDetail = trainService.findDetailById(trainDetailId);
        List<Orders> byTrainDetail = ordersService.getByTrainDetail(trainDetail);
        //获取已经销售出去的座位
        List<String> selledSeat = Lists.newArrayList();
        List<String> hasSeat = Lists.newArrayList();
        for (Orders existOrder : byTrainDetail) {
            List<OrdersDetail> ordersDetails = existOrder.getOrdersDetails();
            for (OrdersDetail ordersDetail : ordersDetails) {
                selledSeat.add(ordersDetail.getSeatSerial());
            }
        }
        hasSeat.addAll(SeatUtil.seatSets);
        hasSeat.removeAll(selledSeat);

        String[] split = contactIds.split(",");
        List<Contact> contactByIds = usersService.findContactByIds(string2long(split));

        if (hasSeat.size() <= 0 || hasSeat.size() < split.length) {
            model.addFlashAttribute("msg", "对不起,余票不足");
            return "redirect:/tourist";
        }

        //TODO
        trainDetail.setSeatNumber("3,3,3");
        trainService.saveTrainDetail(trainDetail);

        orders.setUsers(user);
        orders.setTrainDetail(trainDetail);

        if (orderId != 0) {
            Orders oldOrder = ordersService.getById(orderId);
            TrainDetail oldOrderTrainDetail = oldOrder.getTrainDetail();
            //收回旧的座位
            oldOrderTrainDetail.setSeatNumber(oldOrderTrainDetail.getSeatNumber() + oldOrder.getOrdersDetails().size());
            trainService.saveTrainDetail(oldOrderTrainDetail);

            oldOrder.copy(orders);
            Orders update = ordersService.update(oldOrder);
            List<OrdersDetail> saveOrderDetail = Lists.newArrayList();
            for (int i = 0; i < contactByIds.size(); i++) {
                Contact contact = contactByIds.get(i);
                OrdersDetail ordersDetail = new OrdersDetail();
                String seatSerial = hasSeat.get(i);
                ordersDetail.setSeatSerial(seatSerial);
                ordersDetail.setContact(contact);
                ordersDetail.setOrders(update);
                saveOrderDetail.add(ordersDetail);
            }
            ordersService.saveDetailList(saveOrderDetail);
            model.addFlashAttribute("msg", "改签成功");
        } else {
            Orders saveOrder = ordersService.save(orders);

            List<OrdersDetail> saveOrderDetail = Lists.newArrayList();
            for (int i = 0; i < contactByIds.size(); i++) {
                Contact contact = contactByIds.get(i);
                OrdersDetail ordersDetail = new OrdersDetail();
                String seatSerial = hasSeat.get(i);
                ordersDetail.setSeatSerial(seatSerial);
                ordersDetail.setContact(contact);
                ordersDetail.setOrders(saveOrder);
                saveOrderDetail.add(ordersDetail);
            }
            saveOrder.setOrdersDetails(saveOrderDetail);
            ordersService.save(saveOrder);

            model.addFlashAttribute("msg", "订票成功");
        }
        return "redirect:/user/order/unFinished";
    }

    @RequestMapping(value = "/order/change/{orderId}")
    public String changeOrder(@PathVariable("orderId") Long orderId, Model model) {
        Orders orders = ordersService.getById(orderId);
        if (orders == null) {
            return "redirect:/user/order/unFinished";
        }
        model.addAttribute("orderId", orderId);
        return "redirect:/tourist";
    }

    private List<Long> string2long(String[] strs) {
        List<Long> longIds = Lists.newArrayList();
        for (String str : strs) {
            longIds.add(Long.parseLong(str));
        }
        return longIds;
    }
}
