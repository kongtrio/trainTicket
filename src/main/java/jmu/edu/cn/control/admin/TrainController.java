package jmu.edu.cn.control.admin;

import com.google.common.collect.Lists;
import jmu.edu.cn.domain.QueryParam;
import jmu.edu.cn.domain.Sites;
import jmu.edu.cn.domain.Train;
import jmu.edu.cn.domain.TrainScribe;
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
 */

@RequestMapping("/admin")
@Controller
public class TrainController {
    @Autowired
    private TrainService trainService;
    @Autowired
    private SitesService sitesService;

    @RequestMapping(value = "")
    public String index() {
        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/train")
    public String train(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                        @RequestParam(required = false, defaultValue = "4") Integer pageSize, @ModelAttribute("notifyMsg") String notifyMsg, QueryParam queryParam, Model model) {
        Page<Train> trains = trainService.findAll(pageNo, pageSize, queryParam);
        model.addAttribute("trains", trains);
        model.addAttribute("param", queryParam);
        return "/admin/train";
    }

    @RequestMapping(value = "/delTrain/{trainId}")
    public String delUsers(@PathVariable("trainId") Long trainId, RedirectAttributes model) {
        trainService.delTrain(trainId);
        model.addFlashAttribute("notifyMsg", "删除列车成功");
        return "redirect:/admin/train";
    }

    @RequestMapping(value = "/addTrain", method = RequestMethod.GET)
    public String addTrain() {
        return "/admin/addTrain";
    }

    @RequestMapping(value = "/addTrain", method = RequestMethod.POST)
    public String saveTrain(String trainSerial, String tarinDetail, RedirectAttributesModelMap model) {
        JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
        List<TrainScribe> list = JsonMapper.nonEmptyMapper().fromJson(tarinDetail, jsonMapper.contructCollectionType(List.class, TrainScribe.class));
        trainService.createTrain(trainSerial, list);
        model.addFlashAttribute("notifyMsg", "添加列车成功");
        return "redirect:/admin/train";
    }

    @ResponseBody
    @RequestMapping(value = "/train/getSites")
    public String getAllSite() {
        List<Sites> allSites = sitesService.findAll();
        List<String> siteList = Lists.newArrayList();
        if (allSites != null) {
            for (Sites sites : allSites) {
                siteList.add(sites.getSite());
            }
        }
        return JsonMapper.nonEmptyMapper().toJson(siteList);
    }
}
