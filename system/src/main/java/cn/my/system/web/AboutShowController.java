package cn.my.system.web;

import cn.my.system.service.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutShowController {
    @Autowired
    private AboutService aboutService;
    @GetMapping("/about")
    public String archive(Model model){
        model.addAttribute("aboutme",aboutService.getAbout(1L));
        return "about";
    }
}
