package jmu.edu.cn.control;

import jmu.edu.cn.domain.Users;
import jmu.edu.cn.service.UsersService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/3/17.
 */

@RequestMapping("/genal")
@Controller
public class GenalController extends BaseController {
    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(String msg, Model model) {
        return "/genernal/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(String msg, Model model) {
        if ("errorUser".equals(msg)) {
            msg = "用户名或者密码错误";
        } else if ("registerSuc".equals(msg)) {
            msg = "注册成功,请登录";
        }
        String codeError = (String) session.getAttribute("codeError");
        if (StringUtils.isNotBlank(codeError)) {
            msg = "验证码错误,请重新填写";
            session.setAttribute("codeError", null);
        }
        model.addAttribute("msg", msg);
        return "/login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(Users users, Model model) {
        usersService.save(users);
        model.addAttribute("msg", "registerSuc");
        return "redirect:/genal/login";
    }

    @RequestMapping(value = "/checkUsernameExist", method = RequestMethod.POST)
    @ResponseBody
    public String checkUsernameExist(String username) {
        Users users = usersService.findByUsername(username);
        return users == null ? "true" : "false";
    }
}
