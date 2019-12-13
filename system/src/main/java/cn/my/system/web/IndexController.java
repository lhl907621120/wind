package cn.my.system.web;

import cn.my.system.entity.Blog;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping
public class IndexController {
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping()
    public String blog() {
        return "blog";
    }
}
