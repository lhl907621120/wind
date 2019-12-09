package cn.my.system.web.admin;
import cn.my.system.entity.Blog;
import cn.my.system.service.BlogService;
import cn.my.system.service.TagService;
import cn.my.system.service.TypeService;
import cn.my.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class BlogController {
    private BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService){
        this.blogService = blogService;
    }

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String list(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("blog_page", blogService.listBlog(pageable, blog));
        return "/admin/blogs";
    }

    /*
    局部动态渲染
    */
    @PostMapping("/blogs/search")
    public String Search(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("blog_page", blogService.listBlog(pageable, blog));
        return "/admin/blogs :: blogList";
    }
    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("blog", new Blog());
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("types", typeService.listType());
        return "/admin/blogs-input";
    }
}
