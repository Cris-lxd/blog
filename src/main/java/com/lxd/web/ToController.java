package com.lxd.web;/*
 *  create by 20224
 *  2020/7/26
 * */

import com.lxd.po.Blog;
import com.lxd.po.Type;
import com.lxd.service.BlogService;
import com.lxd.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/to")
public class ToController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    @RequestMapping("/toTypes")
    public String toTypes(Model model){
        List<Type> types = typeService.listType();
        int countType = typeService.countType();
        List<Blog> blogByType = blogService.getBlogByType((long) 1);
        model.addAttribute("types",types);
        model.addAttribute("countType",countType);
        model.addAttribute("blogByType",blogByType);
        return "types";
    }
    @RequestMapping("/toTags")
    public String toTags(){
        return "tags";
    }
    @RequestMapping("/toArchives")
    public String toArchives(){
        return "archives";
    }
    @RequestMapping("/toAbout")
    public String toAbout(){
        return "about";
    }
    @RequestMapping("/toIndex1")
    public String toIndex1(){
        return "redirect:/";
    }
    @RequestMapping("/toAdmin")
    public String toAdmin(){
        return "admin/login";
    }

    @RequestMapping("/getBlogByType/{id}")
    public String getBlogByType(@PathVariable Long id,Model model){

        List<Type> types = typeService.listType();
        int countType = typeService.countType();
        List<Blog> blogByType = blogService.getBlogByType(id);
        model.addAttribute("types",types);
        model.addAttribute("countType",countType);
        model.addAttribute("blogByType",blogByType);

        return "types";
    }
}
