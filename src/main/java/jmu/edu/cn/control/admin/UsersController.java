package jmu.edu.cn.control.admin;

import jmu.edu.cn.domain.QueryParam;
import jmu.edu.cn.domain.Users;
import jmu.edu.cn.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by Administrator on 2016/3/20.
 */
@RequestMapping("/admin/users")
@Controller
public class UsersController {
    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "")
    public String admin(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                        @RequestParam(required = false, defaultValue = "10") Integer pageSize, @ModelAttribute("notifyMsg") String notifyMsg, QueryParam queryParam, Model model) {
        Page<Users> users = usersService.findAll(pageNo, pageSize,queryParam);
        model.addAttribute("users", users);
        model.addAttribute("param", queryParam);
        return "/admin/users";
    }

    @RequestMapping(value = "/delUsers/{userId}")
    public String delUsers(@PathVariable("userId") Long userId, RedirectAttributes model) {
        usersService.delete(userId);
        model.addFlashAttribute("notifyMsg", "删除用户成功");
        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/addUsers", method = RequestMethod.POST)
    public String addUsers(Users users, RedirectAttributes model) {
        usersService.save(users);
        model.addFlashAttribute("notifyMsg", "添加用户成功");
        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/alterUsers", method = RequestMethod.POST)
    public String alterUsers(Users users, RedirectAttributes model) {
        Users alterUser = usersService.findByUsername(users.getUserName());
        if (alterUser == null) {
            model.addFlashAttribute("notifyMsg", "修改失败,找不到此用户");
            return "redirect:/admin/users";
        }
        alterUser.setPassword(users.getPassword());
        alterUser.setTelphone(users.getTelphone());
        alterUser.setRemark(users.getRemark());
        usersService.update(alterUser);
        model.addFlashAttribute("notifyMsg", "修改成功");
        return "redirect:/admin/users";
    }
}
