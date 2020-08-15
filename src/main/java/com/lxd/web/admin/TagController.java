package com.lxd.web.admin;

import com.lxd.po.Tag;
import com.lxd.po.Type;
import com.lxd.service.BlogService;
import com.lxd.service.TagService;
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

import javax.persistence.Id;
import javax.validation.Valid;

/**
 * Created by Cris on 2020/3/28
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;

    /*
    *   查询列表方法  倒序输出
    * */
    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                               Pageable pageable, Model model){
        model.addAttribute("page", tagService.listTag(pageable));
        model.addAttribute("recommendBlogs1", blogService.listRecommendBlogTop(3));
        return "admin/tags";
    }


    /*
    *    新增方法
    * */
    @GetMapping("/tags/input")     //前端页面调用这个方法的写法   th:href="@{/admin/types/input}"
    public String input(Model model){
        model.addAttribute("tag",new Tag());  //前端types-input需要拿到type值   th:object="${type}"
        model.addAttribute("recommendBlogs1", blogService.listRecommendBlogTop(3));
        return "admin/tags-input";
    }
    @PostMapping("/tags")
    public String postpost(@Valid Tag tag, BindingResult result, RedirectAttributes attributes,Model model){
        Tag tag1=tagService.getTagByName(tag.getName());    //如果有名字就能获取到
        if(tag1 != null){
            result.rejectValue("name", "nameError", "不能重复添加");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t=tagService.saveTag(tag);
        if (t == null) {      //更新以后重新判断
            attributes.addFlashAttribute("message","新增失败");
        }else{
            attributes.addFlashAttribute("message","新增成功");

        }
        model.addAttribute("recommendBlogs1", blogService.listRecommendBlogTop(3));
        return "redirect:/admin/tags";
    }


    /*
     *    更新方法
     * */
    @GetMapping("/tags/{id}/input")          //为了将原有的内容提取到inp框中
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        model.addAttribute("recommendBlogs1", blogService.listRecommendBlogTop(3));
        return "admin/tags-input";
    }

    @PostMapping("/tags{id}")
    public String editTags(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes,Model model){
        Tag tag1=tagService.getTagByName(tag.getName());
        if(tag1 != null){
            result.rejectValue("name", "nameError","不能重复添加");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t=tagService.updateTag(id,tag);
        if(t==null){
            attributes.addFlashAttribute("message", "更新失败");
        }else{
            attributes.addFlashAttribute("message", "更新成功");

        }
        model.addAttribute("recommendBlogs1", blogService.listRecommendBlogTop(3));
        return "redirect:/admin/tags";

    }

    /*
    *  删除方法
    * */
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }


}
