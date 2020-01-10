package cn.my.system.web.admin;

import cn.my.system.entity.About;
import cn.my.system.entity.User;
import cn.my.system.service.AboutService;
import cn.my.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AboutMeController {

    @Autowired
    private AboutService aboutService;

    @Autowired
    private UserService userService;


    /*
    跳转到关于我界面
     */
    @GetMapping("/about")
    public String About(Model model) {
        model.addAttribute("aboutme", aboutService.getAbout(1L));
        return "admin/about";
    }

    /*
    跳转到关于我编辑界面
     */
    @GetMapping("/about/{id}/update")
    public String toInputPage(@PathVariable("id") Long id, Model model) {
        id = 1L;
        About about = aboutService.getAbout(id);
        model.addAttribute("about", about);
        model.addAttribute("users", userService.getUser(1L));
        return "admin/about_input";
    }

    /*
    修改个人信息
     */
    @PostMapping("/about/{id}")
    public String editInput(@Valid About about, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        id = 1L;
        about.setUser(userService.getUser(id));//获取到user表中的头像信息
        User user = userService.getUser(id);//user对象
        if (result.hasErrors()) {
            return "/admin/about_input";
        }
        About a = aboutService.updateAbout(id, about);
        user.setAvatar(aboutService.getAvatar(id));//设置user对象的avatar值为about里的值
        userService.updateUser(id, user);
        if (a == null) {
            attributes.addFlashAttribute("message", "修改失败");
        } else {
            attributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/about";
    }

}
