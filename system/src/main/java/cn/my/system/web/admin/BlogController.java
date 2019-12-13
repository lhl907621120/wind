package cn.my.system.web.admin;

import cn.my.system.entity.Blog;
import cn.my.system.entity.Type;
import cn.my.system.entity.User;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("blog_page", blogService.listBlog(pageable, blog));
        return "admin/blogs";
    }

    /*
    局部动态渲染
    */
    @PostMapping("/blogs/search")
    public String Search(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("blog_page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }

    /*
    跳转到博客新增页面
     */
    @GetMapping("/blogs/input")
    public String toInputPage(Model model) {
        model.addAttribute("blog", new Blog());
        setTypeAndTag(model);
        return "admin/blogs-input";
    }

    /*
    新增博客保存操作
     */
    @PostMapping("/blogs")
    public String saveBlog(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        //页面type.id通过${types}属性赋值到controller的blog对象的new一个type，通过typesetId。
        Type t = typeService.getType(blog.getType().getId());
        blog.setType(t);//
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b = blogService.saveBlog(blog);
        if (b == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }

    //来到修改页面，查出要修改的博客，在页面回显
    @GetMapping("/blogs/{id}/input")
    public String toEditPage(@PathVariable("id") Long id, Model model) {
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blog);
//        回到修改页面
        return "admin/blogs-input";
    }

    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }

    @PutMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LIST;
    }
}
