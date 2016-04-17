package jmu.edu.cn.control.user;

import jmu.edu.cn.control.BaseController;
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

/**
 * Created by Administrator on 2016/4/11.
 */
@RequestMapping("/user")
@Controller("user_controller")
public class ContactController extends BaseController {
    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/contact/list")
    public String index(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                        @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                        @ModelAttribute("msg") String msg, Model model) {
        Users user = (Users) request.getSession().getAttribute("user");
        Page<Contact> contactsByUser = usersService.findContactsByUser(pageNo, pageSize, user);
        model.addAttribute("contacts", contactsByUser);
        return "/user/contact";
    }

    @RequestMapping(value = "/contact/addContact", method = RequestMethod.GET)
    public String addContact() {
        return "/user/addContact";
    }

    @RequestMapping(value = "/contact/addContact", method = RequestMethod.POST)
    public String saveContact(Contact contact, RedirectAttributes model) {
        String msg = "添加联系人失败";
        Users user = (Users) request.getSession().getAttribute("user");
        if (contact != null && user != null) {
            usersService.saveContact(contact, user);
            msg = "添加联系人成功";
        }
        model.addFlashAttribute("msg", msg);
        return "redirect:/user/contact/list";
    }

    @RequestMapping(value = "/contact/updateContact", method = RequestMethod.GET)
    public String updateContact(Model model, long contactId) {
        Contact contactById = usersService.findContactById(contactId);
        model.addAttribute("contact", contactById);
        return "/user/addContact";
    }

    @RequestMapping(value = "/contact/updateContact", method = RequestMethod.POST)
    public String updateContact(RedirectAttributes model, long contactId, Contact contact) {
        String msg = "修改联系人失败";
        Contact contactById = usersService.findContactById(contactId);
        if (contactById != null) {
            contactById.setName(contact.getName());
            contactById.setTelphone(contact.getTelphone());
            contactById.setIdentityCard(contact.getIdentityCard());
            usersService.updateContact(contactById);
            msg = "修改联系人成功";
        }
        model.addFlashAttribute("msg", msg);
        return "redirect:/user/contact/list";
    }

    @RequestMapping(value = "/contact/del/{contactId}")
    public String delUsers(@PathVariable("contactId") Long contactId, RedirectAttributes model) {
        usersService.deleteContact(contactId);
        model.addFlashAttribute("msg", "删除联系人成功");
        return "redirect:/user/contact/list";
    }
}
