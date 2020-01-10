package cn.my.system.web.admin;

import cn.my.system.entity.User;
import cn.my.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(method = RequestMethod.GET)
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("/admin/login")
    public String login(@RequestParam(required = false) String username, @RequestParam(required = false) String password, HttpSession session, RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/my_index";
        } else {
            attributes.addFlashAttribute("message", "Message：请输入正确的用户名密码");
            return "redirect:/admin";
        }

    }
    /*
    注销
     */
    @GetMapping("admin/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
