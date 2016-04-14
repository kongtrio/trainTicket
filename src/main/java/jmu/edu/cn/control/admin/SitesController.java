package jmu.edu.cn.control.admin;

import jmu.edu.cn.control.BaseController;
import jmu.edu.cn.domain.QueryParam;
import jmu.edu.cn.domain.Sites;
import jmu.edu.cn.domain.Users;
import jmu.edu.cn.service.SitesService;
import jmu.edu.cn.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

/**
 * Created by Administrator on 2016/3/20.
 */
@RequestMapping("/admin/sites")
@Controller
public class SitesController extends BaseController {
    @Autowired
    private SitesService sitesService;

    @RequestMapping(value = "")
    public String admin(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                        @RequestParam(required = false, defaultValue = "10") Integer pageSize, @ModelAttribute("notifyMsg") String notifyMsg, QueryParam queryParam, Model model) {
        Page<Sites> sites = sitesService.findAll(pageNo, pageSize, queryParam);
        model.addAttribute("sites", sites);
        model.addAttribute("param", queryParam);
        return "/admin/sites";
    }

    @RequestMapping(value = "/delSites/{siteId}")
    public String delSites(@PathVariable("siteId") Long siteId, RedirectAttributes model) {
        sitesService.delete(siteId);
        model.addFlashAttribute("notifyMsg", "删除站点成功");
        return "redirect:/admin/sites";
    }

    @RequestMapping(value = "/addSites", method = RequestMethod.POST)
    public String addSites(Sites sites, RedirectAttributes model) {
        sitesService.saveOrUpdate(sites);
        model.addFlashAttribute("notifyMsg", "添加站点成功！");
        return "redirect:/admin/sites";
    }

    @RequestMapping(value = "/alterSites", method = RequestMethod.POST)
    public String alterSites(Sites sites, RedirectAttributes model) {
        if (sites == null) {
            model.addFlashAttribute("notifyMsg", "系统异常,请联系管理员");
            return "redirect:/admin/users";
        }
        Sites siteById = sitesService.findById(sites.getId());
        siteById.setSite(sites.getSite());
        siteById.setProvince(sites.getProvince());
        siteById.setRemark(sites.getRemark());
        sitesService.saveOrUpdate(siteById);
        model.addFlashAttribute("notifyMsg", "修改成功");
        return "redirect:/admin/sites";
    }

    @RequestMapping(value = "/checkSiteExist", method = RequestMethod.POST)
    @ResponseBody
    public String checkSiteExist(String site) {
        Sites bySite = sitesService.findBySite(site);
        return bySite == null ? "true" : "false";
    }
}
