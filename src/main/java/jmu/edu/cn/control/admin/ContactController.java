package jmu.edu.cn.control.admin;

import jmu.edu.cn.domain.Contact;
import jmu.edu.cn.domain.QueryParam;
import jmu.edu.cn.domain.Users;
import jmu.edu.cn.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by Administrator on 2016/3/21.
 * 处理联系人消息的处理器
 */
@RequestMapping("/admin/contact")
@Controller("admin_controller")
public class ContactController {
    @Autowired
    private UsersService usersService;


    /**
     * 获取联系人数据并返回给客户端
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
        Page<Contact> contacts = usersService.findAllContacts(pageNo, pageSize, queryParam);
        List<Users> allUsers = usersService.findAll();
        model.addAttribute("contacts", contacts);
        model.addAttribute("allUsers", allUsers);
        model.addAttribute("queryParam", queryParam);
        return "/admin/contacts";
    }


    /**
     * 根据传进来的contactId删除对应的联系人
     *
     * @param contactId 联系人ID
     * @return
     */
    @RequestMapping(value = "/delContacts/{contactId}")
    public String delContacts(@PathVariable("contactId") Long contactId, RedirectAttributes model) {
        usersService.deleteContact(contactId);
        model.addFlashAttribute("notifyMsg", "删除联系人成功");
        return "redirect:/admin/contact";
    }

    /**
     * 添加一个新的联系人
     *
     * @param contact  客户端传过来的联系人详情
     * @param username 指定添加到那个用户底下
     * @return
     */
    @RequestMapping(value = "/addContacts", method = RequestMethod.POST)
    public String addUsers(Contact contact, String username, RedirectAttributes model) {
        String msg = "添加联系人失败";
        Users byUsername = usersService.findByUsername(username);
        if (contact != null && byUsername != null) {
            usersService.saveContact(contact, byUsername);
            msg = "添加联系人成功";
        }
        model.addFlashAttribute("notifyMsg", msg);
        return "redirect:/admin/contact";
    }

    /**
     * 修改一个联系人
     *
     * @param contact 修改的联系人参数
     * @return
     */
    @RequestMapping(value = "/alterContacts", method = RequestMethod.POST)
    public String alterContacts(Contact contact, RedirectAttributes model) {
        String msg = "修改联系人失败";
        Contact contactById = usersService.findContactById(contact.getId());
        if (contactById != null) {
            contactById.setName(contact.getName());
            contactById.setTelphone(contact.getTelphone());
            contactById.setIdentityCard(contact.getIdentityCard());
            usersService.updateContact(contactById);
            msg = "修改联系人成功";
        }
        model.addFlashAttribute("notifyMsg", msg);
        return "redirect:/admin/contact";
    }
}
