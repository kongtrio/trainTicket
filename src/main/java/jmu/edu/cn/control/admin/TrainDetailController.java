package jmu.edu.cn.control.admin;

import com.google.common.collect.Lists;
import jmu.edu.cn.domain.*;
import jmu.edu.cn.service.SitesService;
import jmu.edu.cn.service.TrainService;
import jmu.edu.cn.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.List;

/**
 * Created by Administrator on 2016/3/20.
 * 处理列车班次的处理器
 */

@RequestMapping("/admin/trainDetail")
@Controller
public class TrainDetailController {
    @Autowired
    private TrainService trainService;
    @Autowired
    private SitesService sitesService;

    /**
     * 获取列车班次数据并且返回给客户端
     *
     * @param pageNo     第几页数据
     * @param pageSize   一页展示几条数据
     * @param queryParam 一些查询参数
     * @return
     */
    @RequestMapping(value = "")
    public String train(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                        @RequestParam(required = false, defaultValue = "10") Integer pageSize, QueryParam queryParam, Model model) {
        List<Train> trainBySite = trainService.getTrainBySite(queryParam.getSites());
        Page<TrainDetail> trains = trainService.findAllDetail(pageNo, pageSize, queryParam, trainBySite);
        model.addAttribute("trainDetails", trains);
        model.addAttribute("param", queryParam);
        return "/admin/trainDetail";
    }

    /**
     * 修改列车班次的状态,状态分为停运和正常
     *
     * @param trainDetailId 列车班次的id
     * @param status        列车的状态
     * @return
     */
    @RequestMapping(value = "/changeStatus/{trainDetailId}")
    public String changeStatus(@PathVariable("trainDetailId") Long trainDetailId, Integer status, Model model) {
        TrainDetail byId = trainService.getDetailById(trainDetailId);
        byId.setStatus(status);
        trainService.saveTrainDetail(byId);
        model.addAttribute("id", trainDetailId);
        return "redirect:/admin/trainDetail";
    }
}
