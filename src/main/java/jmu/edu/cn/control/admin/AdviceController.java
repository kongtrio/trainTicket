package jmu.edu.cn.control.admin;

import jmu.edu.cn.control.BaseController;
import jmu.edu.cn.domain.Advices;
import jmu.edu.cn.domain.QueryParam;
import jmu.edu.cn.service.AdvicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Administrator on 2016/3/17.
 * 管理员页面---处理投诉建议的处理器
 */
@RequestMapping("/admin/advice")
@Controller("admin_advice_AdviceController")
public class AdviceController extends BaseController {
    @Autowired
    private AdvicesService advicesService;

    /**
     * 获取投诉信息并返回给客户端
     *
     * @param pageNo     展示第几页数据
     * @param pageSize   一页展示几条数据
     * @param queryParam 一些查询参数
     */
    @RequestMapping(value = "")
    public String index(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                        @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                        QueryParam queryParam, Model model) {
        Page<Advices> list = advicesService.list(pageNo, pageSize, queryParam);
        model.addAttribute("advices", list);
        model.addAttribute("param", queryParam);
        return "/admin/advice";
    }
}
