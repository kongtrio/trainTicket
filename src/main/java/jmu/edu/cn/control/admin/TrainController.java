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
 * 处理列车信息的处理器
 */

@RequestMapping("/admin")
@Controller
public class TrainController {
    @Autowired
    private TrainService trainService;
    @Autowired
    private SitesService sitesService;

    /**
     * 重定向到/admin/users这个地址  (就是跳转到UsersController的admin方法里面)
     *
     * @return
     */
    @RequestMapping(value = "")
    public String index() {
        return "redirect:/admin/users";
    }

    /**
     * 获取列车信息并返回给客户端
     *
     * @param pageNo     第几页数据
     * @param pageSize   一页展示几条数据
     * @param notifyMsg  提示消息
     * @param queryParam 一些查询参数
     * @return
     */
    @RequestMapping(value = "/train")
    public String train(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                        @RequestParam(required = false, defaultValue = "4") Integer pageSize, @ModelAttribute("notifyMsg") String notifyMsg, QueryParam queryParam, Model model) {
        Page<Train> trains = trainService.findAll(pageNo, pageSize, queryParam);
        model.addAttribute("trains", trains);
        model.addAttribute("param", queryParam);
        return "/admin/train";
    }

    /**
     * 根据列车id删除一辆列车
     *
     * @param trainId 列车id
     * @param model
     * @return
     */
    @RequestMapping(value = "/delTrain/{trainId}")
    public String delTrain(@PathVariable("trainId") Long trainId, RedirectAttributes model) {
        trainService.delTrain(trainId);
        model.addFlashAttribute("notifyMsg", "删除列车成功");
        return "redirect:/admin/train";
    }

    /**
     * 跳转到添加列车的页面
     *
     * @return
     */
    @RequestMapping(value = "/addTrain", method = RequestMethod.GET)
    public String addTrain() {
        return "/admin/addTrain";
    }

    /**
     * 添加一辆新的列车
     *
     * @param trainSerial 列车编号
     * @param tarinDetail 列车各个站点的信息
     * @return
     */
    @RequestMapping(value = "/addTrain", method = RequestMethod.POST)
    public String saveTrain(String trainSerial, String tarinDetail, RedirectAttributesModelMap model) {
        JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
        List<TrainScribe> list = JsonMapper.nonEmptyMapper().fromJson(tarinDetail, jsonMapper.contructCollectionType(List.class, TrainScribe.class));
        trainService.createTrain(trainSerial, list);
        model.addFlashAttribute("notifyMsg", "添加列车成功");
        return "redirect:/admin/train";
    }

    /**
     * 获取所有的站点信息,并返回json字符串
     *
     * @return
     */
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
