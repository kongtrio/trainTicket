package jmu.edu.cn.control.user;

import jmu.edu.cn.control.BaseController;
import jmu.edu.cn.domain.Contact;
import jmu.edu.cn.domain.QueryParam;
import jmu.edu.cn.domain.Users;
import jmu.edu.cn.service.TrainService;
import jmu.edu.cn.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by Administrator on 2016/4/10.
 * 处理用户信息的处理器
 */

@RequestMapping("/user")
@Controller
public class UserController extends BaseController {
    @Autowired
    private UsersService usersService;

    /**
     * 获取用户信息并跳转到用户信息界面
     *
     * @return
     */
    @RequestMapping(value = "")
    public String index(@ModelAttribute("msg") String msg, Model model) {
        Users user = (Users) request.getSession().getAttribute("user");
        model.addAttribute("user", user);
        return "/user/index";
    }

    /**
     * 跳转到修改密码的界面
     *
     * @return
     */
    @RequestMapping(value = "/alterPassword", method = RequestMethod.GET)
    public String alterPassword(@ModelAttribute("notifyMsg") String notifyMsg) {
        return "/user/alterPassword";
    }

    /**
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param password    新密码
     * @return
     */
    @RequestMapping(value = "/alterPassword", method = RequestMethod.POST)
    public String updateContact(RedirectAttributes model, String oldPassword, String password) {
        Users user = (Users) request.getSession().getAttribute("user");
        if (!user.getPassword().equals(oldPassword)) {
            model.addFlashAttribute("msg", "旧密码错误");
            return "redirect:/user/alterPassword";
        }
        user.setPassword(password);
        usersService.save(user);
        model.addFlashAttribute("msg", "密码修改成功");
        return "redirect:/user";
    }

    /**
     * 跳转到修改电话号码的界面
     *
     * @return
     */
    @RequestMapping(value = "/alterTel", method = RequestMethod.GET)
    public String alterTel(@ModelAttribute("notifyMsg") String notifyMsg) {
        return "/user/alterTel";
    }

    /**
     * 修改密码
     *
     * @return
     */
    @RequestMapping(value = "/alterTel", method = RequestMethod.POST)
    public String alterTel(RedirectAttributes model, String telphone) {
        Users user = (Users) request.getSession().getAttribute("user");
        user.setTelphone(telphone);
        usersService.save(user);
        model.addFlashAttribute("msg", "电话号码修改成功");
        return "redirect:/user";
    }
}
