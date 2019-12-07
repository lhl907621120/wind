package cn.my.system.web.admin;

import cn.my.system.entity.Type;
import cn.my.system.service.TypeService;
import javassist.compiler.ast.ASTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping
public class TypeController {
    @Autowired
    private TypeService typeService;

    /*
    分类页面
     */
    @GetMapping("/admin/types")
    public String types(@PageableDefault(size = 3, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    /*
    跳转新增分类页面
     */
    @GetMapping("/admin/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "/admin/types_input";
    }

    /*
    新增分类
     */
    @PostMapping("/admin/types")
    public String Post(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        Type t = typeService.getTypeByName(type.getName());
        if (t != null) {
            result.rejectValue("name", "nameError", "该分类已存在");
        }
        if (result.hasErrors()) {
            return "/admin/types_input";
        }
        Type type1 = typeService.saveType(type);
        if (type1 == null) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }

    /*
    跳转修改分类页面
     */
    @GetMapping("/admin/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "/admin/types_input";
    }

    /*
  修改分类
   */
    @PostMapping("/admin/types/{id}")
    public String EditPost(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        Type t = typeService.getTypeByName(type.getName());
        if (t != null) {
            result.rejectValue("name", "nameError", "该分类已存在");
        }
        if (result.hasErrors()) {
            return "/admin/types_input";
        }
        Type type1 = typeService.update(id, type);
        if (type1 == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }

    /*
    删除分类
     */
    @GetMapping("/admin/types/{id}/delete")
    public String Delete(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types/";
    }
}
