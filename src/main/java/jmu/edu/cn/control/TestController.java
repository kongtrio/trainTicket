package jmu.edu.cn.control;

import jmu.edu.cn.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/test")
@Controller
public class TestController {
    public static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private UsersService usersService;

    @RequestMapping("/login")
    public String list(@RequestParam(value = "username", required = false) String username, String pwd, Model model) {
        return "/test/login";
    }

    @RequestMapping("/succes")
    public String succes(@RequestParam(value = "username", required = false) String username, String pwd, Model model) {
        return "/test/succes";
    }

    @RequestMapping("/deny")
    public String deny(@RequestParam(value = "username", required = false) String username, String pwd, Model model) {
        return "/test/deny";
    }

    @RequestMapping("/errorUser")
    public String logout(@RequestParam(value = "username", required = false) String username, String pwd, Model model) {
        return "/test/erroruser";
    }
}
