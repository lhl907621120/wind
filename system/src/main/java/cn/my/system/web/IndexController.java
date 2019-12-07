package cn.my.system.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    @GetMapping("/templates/admin/login")
    public String login(){
        System.out.println("------index------");
        return "login";
    }
    @GetMapping("/index")
    public  String index(){
        return "index";
    }
    @GetMapping("/blog")
    public  String blog(){
        return "blog";
    }
}
