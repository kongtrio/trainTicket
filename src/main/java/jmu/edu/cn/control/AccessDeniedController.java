package jmu.edu.cn.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class AccessDeniedController {

    @RequestMapping("accessDeniedPage")
    public String accessDenied() {
        return "/accessDenied";
    }
}
