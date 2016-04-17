package jmu.edu.cn.control.tourist;

import jmu.edu.cn.control.BaseController;
import jmu.edu.cn.domain.*;
import jmu.edu.cn.service.NotifyService;
import jmu.edu.cn.service.OrdersService;
import jmu.edu.cn.service.TrainService;
import jmu.edu.cn.service.UsersService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2016/3/17.
 */

@RequestMapping("/tourist")
@Controller
public class TouristController extends BaseController {
    @Autowired
    private TrainService trainService;
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private OrdersService ordersService;

    @RequestMapping(value = "")
    public String index(QueryParam queryParam, Long orderId, String msg, Model model) {
        if (StringUtils.isNotBlank(queryParam.getBeginSite()) && StringUtils.isNotBlank(queryParam.getEndSite())
                && StringUtils.isNotBlank(queryParam.getTime())) {
            List<TrainReport> trainDetailList = trainService.getTrainDetailList
                    (queryParam.getBeginSite(), queryParam.getEndSite(), queryParam.getTime());
            model.addAttribute("trains", trainDetailList);
            model.addAttribute("param", queryParam);
        }
        Page<Notify> all = notifyService.findAll(1, 10, new QueryParam());
        if (all != null) {
            model.addAttribute("notifys", all.getContent());
        }
        model.addAttribute("orderId", orderId);
        return "/tourist/index";
    }

}
