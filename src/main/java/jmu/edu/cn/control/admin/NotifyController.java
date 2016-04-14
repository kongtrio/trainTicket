package jmu.edu.cn.control.admin;

import jmu.edu.cn.control.BaseController;
import jmu.edu.cn.domain.Notify;
import jmu.edu.cn.domain.QueryParam;
import jmu.edu.cn.domain.Sites;
import jmu.edu.cn.service.NotifyService;
import jmu.edu.cn.service.SitesService;
import jmu.edu.cn.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

/**
 * Created by Administrator on 2016/3/20.
 */
@RequestMapping("/admin/notify")
@Controller("admin_notifycontroller")
public class NotifyController extends BaseController {
    @Autowired
    private NotifyService notifyService;

    @RequestMapping(value = "")
    public String admin(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                        @RequestParam(required = false, defaultValue = "10") Integer pageSize, @ModelAttribute("notifyMsg") String notifyMsg, QueryParam queryParam, Model model) {
        Page<Notify> notifys = notifyService.findAll(pageNo, pageSize, queryParam);
        model.addAttribute("notifys", notifys);
        model.addAttribute("param", queryParam);
        return "/admin/notify";
    }

    @RequestMapping(value = "/delNotify/{siteId}")
    public String delNotify(@PathVariable("siteId") Long siteId, RedirectAttributes model) {
        notifyService.delete(siteId);
        model.addFlashAttribute("notifyMsg", "删除站点成功");
        return "redirect:/admin/notify";
    }

    @RequestMapping(value = "/addNotify", method = RequestMethod.POST)
    public String addNotify(Notify notify, String expireTimeStr, RedirectAttributes model) {
        Date expireTime = DateUtil.parseDate(expireTimeStr, "yyyy-MM-dd");
        notify.setExpireTime(expireTime);
        notifyService.saveOrUpdate(notify);
        model.addFlashAttribute("notifyMsg", "添加公告成功！");
        return "redirect:/admin/notify";
    }

    @RequestMapping(value = "/alterNotify", method = RequestMethod.POST)
    public String alterNotify(Notify notify, String expireTimeStr, RedirectAttributes model) {
        if (notify == null) {
            model.addFlashAttribute("notifyMsg", "系统异常,请联系管理员");
            return "redirect:/admin/users";
        }
        Date expireTime = DateUtil.parseDate(expireTimeStr, "yyyy-MM-dd");
        Notify byId = notifyService.findById(notify.getId());
        byId.setTitle(notify.getTitle());
        byId.setContent(notify.getContent());
        byId.setExpireTime(expireTime);
        notifyService.saveOrUpdate(byId);
        model.addFlashAttribute("notifyMsg", "修改成功");
        return "redirect:/admin/notify";
    }
}
