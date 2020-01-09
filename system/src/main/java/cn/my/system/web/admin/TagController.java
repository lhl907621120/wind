package cn.my.system.web.admin;

import cn.my.system.entity.Tag;
import cn.my.system.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;

    /*
    标签页面
     */
    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }

    /*
    跳转标签新增页面
     */
    @GetMapping("/tags/input")
    public String editTags(Model model) {
        model.addAttribute("tag", new Tag());
        return "/admin/tags_input";
    }

    /*
    新增标签
     */
    @PostMapping("/tags")
    public String Post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
        Tag t = tagService.getTagByName(tag.getName());
        if (t != null) {
            result.rejectValue("name", "nameError", "该标签已存在");
        }
        if (result.hasErrors()) {
            return "/admin/tags_input";
        }
        Tag tag1 = tagService.saveTag(tag);
        if (tag1 == null) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/tags";
    }

    /*
    跳转修改标签页面
     */
    @GetMapping("/tags/{id}/input")
    public String editTags(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "/admin/tags_input";
    }

    /*
    修改标签
     */
    @PostMapping("/tags/{id}")
    public String EditTags(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        Tag t = tagService.getTagByName(tag.getName());
        if (t != null) {
            result.rejectValue("name", "nameError", "该标签已存在");
        }
        if (result.hasErrors()) {
            return "/admin/tags_input";
        }
        Tag tag1 = tagService.update(id, tag);
        if (tag1 == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";
    }

    /*
    删除标签
    */
    @GetMapping("/tags/{id}/delete")
    public String Delete(@PathVariable Long id, RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags/";
    }
}
