package jmu.edu.cn.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 处理权限不足的处理器
 */
@Controller
@RequestMapping("")
public class AccessDeniedController {

    /**
     * 跳转到警告权限不足的页面
     */
    @RequestMapping("accessDeniedPage")
    public String accessDenied() {
        return "/accessDenied";
    }
}
