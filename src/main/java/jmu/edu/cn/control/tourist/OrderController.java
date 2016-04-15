package jmu.edu.cn.control.tourist;

import jmu.edu.cn.control.BaseController;
import jmu.edu.cn.domain.*;
import jmu.edu.cn.service.TrainService;
import jmu.edu.cn.service.UsersService;
import jmu.edu.cn.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
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

    @RequestMapping(value = "/order")
    public String index(QueryParam queryParam, Model model) {
        Users user = (Users) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        Users tmpUser = usersService.getById(user.getId());
        Train train = trainService.getById(queryParam.getId());
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
        model.addAttribute("train", train);
        model.addAttribute("user", tmpUser);
        return "/user/order";
    }

    @RequestMapping(value = "/order/create")
    public String createOrder(Orders orders, QueryParam queryParam, Model model) {
        String sss = "dddd";
        return "redirect:/tourist";
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
