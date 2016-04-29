package jmu.edu.cn.control.tourist;

import jmu.edu.cn.control.BaseController;
import jmu.edu.cn.domain.Notify;
import jmu.edu.cn.domain.QueryParam;
import jmu.edu.cn.service.NotifyService;
import jmu.edu.cn.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Administrator on 2016/3/17.
 * 处理公告信息的处理器
 */

@RequestMapping("/notify")
@Controller("tourist_notifycontroller")
public class NotifyController extends BaseController {
    @Autowired
    private TrainService trainService;
    @Autowired
    private NotifyService notifyService;

    /**
     * 获取公告信息并且返回给客户端
     *
     * @param pageNo   第几页
     * @param pageSize 页面几条数据
     * @return
     */
    @RequestMapping(value = "")
    public String index(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                        @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                        Model model) {
        Page<Notify> all = notifyService.findAll(pageNo, pageSize, new QueryParam());
        model.addAttribute("notifys", all);
        return "/tourist/notifys";
    }

    /**
     * 查看具体某条公告
     *
     * @param notifyId 公告id
     */
    @RequestMapping(value = "/{notifyId}")
    public String notify(@PathVariable("notifyId") long notifyId, Model model) {
        Notify notify = notifyService.findById(notifyId);
        model.addAttribute("notify", notify);
        return "/tourist/notify";
    }
}
