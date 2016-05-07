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
 * 处理公告信息的处理器
 */
@RequestMapping("/admin/notify")
@Controller("admin_notifycontroller")
public class NotifyController extends BaseController {
    @Autowired
    private NotifyService notifyService;

    /**
     * 根据参数获取公告信息并返回给客户端
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
        Page<Notify> notifys = notifyService.findAll(pageNo, pageSize, queryParam);
        model.addAttribute("notifys", notifys);
        model.addAttribute("param", queryParam);
        return "/admin/notify";
    }

    /**
     * 根据id删除一条公告
     *
     * @param notifyId 公告的id
     * @param model
     * @return
     */
    @RequestMapping(value = "/delNotify/{notifyId}")
    public String delNotify(@PathVariable("notifyId") Long notifyId, RedirectAttributes model) {
        notifyService.delete(notifyId);
        model.addFlashAttribute("notifyMsg", "删除公告成功");
        return "redirect:/admin/notify";
    }

    /**
     * 添加一条心的公告
     *
     * @param notify        公告信息
     * @param expireTimeStr 过期时间
     * @return
     */
    @RequestMapping(value = "/addNotify", method = RequestMethod.POST)
    public String addNotify(Notify notify, String expireTimeStr, RedirectAttributes model) {
        Date expireTime = DateUtil.parseDate(expireTimeStr, "yyyy-MM-dd");
        notify.setExpireTime(expireTime);
        notify.setInsertTime(new Date());
        notifyService.saveOrUpdate(notify);
        model.addFlashAttribute("notifyMsg", "添加公告成功！");
        return "redirect:/admin/notify";
    }

    /**
     * 修改一条公告
     *
     * @param notify        要修改的公告内容
     * @param expireTimeStr 过期时间
     * @return
     */
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
