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
 * 处理站点信息的处理器
 */
@RequestMapping("/admin/sites")
@Controller
public class SitesController extends BaseController {
    @Autowired
    private SitesService sitesService;

    /**
     * 根据参数获取站点信息并返回给客户端
     *
     * @param pageNo     第几页数据
     * @param pageSize   一页展示几条数据
     * @param notifyMsg  提示消息
     * @param queryParam 一些查询参数
     * @return
     */
    @RequestMapping(value = "")
    public String admin(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                        @RequestParam(required = false, defaultValue = "10") Integer pageSize, @ModelAttribute("notifyMsg") String notifyMsg, QueryParam queryParam, Model model) {
        Page<Sites> sites = sitesService.findAll(pageNo, pageSize, queryParam);
        model.addAttribute("sites", sites);
        model.addAttribute("param", queryParam);
        return "/admin/sites";
    }

    /**
     * 根据siteId删除一个站点
     *
     * @param siteId 站点对应的id
     * @return
     */
    @RequestMapping(value = "/delSites/{siteId}")
    public String delSites(@PathVariable("siteId") Long siteId, RedirectAttributes model) {
        sitesService.delete(siteId);
        model.addFlashAttribute("notifyMsg", "删除站点成功");
        return "redirect:/admin/sites";
    }

    /**
     * 添加一个新的站点
     *
     * @param sites 新的站点信息
     * @return
     */
    @RequestMapping(value = "/addSites", method = RequestMethod.POST)
    public String addSites(Sites sites, RedirectAttributes model) {
        sitesService.saveOrUpdate(sites);
        model.addFlashAttribute("notifyMsg", "添加站点成功!");
        return "redirect:/admin/sites";
    }

    /**
     * 修改一个站点
     *
     * @param sites 要修改的站点的信息
     * @return
     */
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

    /**
     * 检查这个站点是否已经存在是数据库中
     *
     * @param site 要检查的站点的名称
     * @return
     */
    @RequestMapping(value = "/checkSiteExist", method = RequestMethod.POST)
    @ResponseBody
    public String checkSiteExist(String site) {
        Sites bySite = sitesService.findBySite(site);
        return bySite == null ? "true" : "false";
    }
}
