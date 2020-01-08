package cn.my.system.web.admin;

import cn.my.system.entity.About;
import cn.my.system.entity.User;
import cn.my.system.service.AboutService;
import cn.my.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AboutMeController {

    @Autowired
    private AboutService aboutService;

    @Autowired
    private UserService userService;

    @GetMapping("/about")
    public String About() {

        return "admin/about";
    }


    @GetMapping("/about/input")
    public String editInput(Model model, HttpSession session) {
        model.addAttribute("about", new About());
        model.addAttribute("users",userService.getUser(1L));
        return "/admin/about_input";
    }

}
