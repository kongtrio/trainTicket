package jmu.edu.cn.control.tourist;

import jmu.edu.cn.control.BaseController;
import jmu.edu.cn.domain.Advices;
import jmu.edu.cn.domain.Notify;
import jmu.edu.cn.domain.QueryParam;
import jmu.edu.cn.service.AdvicesService;
import jmu.edu.cn.service.NotifyService;
import jmu.edu.cn.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Administrator on 2016/3/17.
 * 处理功投诉建议信息的处理器
 */

@RequestMapping("/advice")
@Controller("advice_AdviceController")
public class AdviceController extends BaseController {
    @Autowired
    private AdvicesService advicesService;

    /**
     * 跳转到投诉建议页面
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "/tourist/advice";
    }

    /**
     * 提交一条投诉建议
     *
     * @param advices 投诉建议的而信息
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String save(Advices advices, Model model) {
        advicesService.save(advices);
        model.addAttribute("msg", "提交成功,谢谢您宝贵的意见");
        return "/tourist/advice";
    }
}
