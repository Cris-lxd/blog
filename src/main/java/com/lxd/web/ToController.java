package com.lxd.web;/*
 *  create by 20224
 *  2020/7/26
 * */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/to")
public class ToController {

    @RequestMapping("/toTypes")
    public String toTypes(){
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
}
