package com.lxd.web;

import com.lxd.NotFoundException;
import com.lxd.dao.BlogRepository;
import com.lxd.service.BlogService;
import com.lxd.service.TagService;
import com.lxd.service.TypeService;
import com.lxd.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class indexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;


    @GetMapping("/")   //请求根路径    //路径变量传递两个参数
    public String index(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){     //PathVariable  路径变量  即获取url的参数
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));   //指定显示的大小   对应index的th:each="type : ${types}"
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop1(5));  //获得推荐的，修改排序
        model.addAttribute("recommendBlogs1", blogService.listRecommendBlogTop(3));  //获得最新的，时间排序
        return "index";   //若无异常。则返回到index页面
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query,
                         Model model){
        model.addAttribute("page", blogService.listBlog("%"+query+"%", pageable));
        model.addAttribute("query",query);   //将query再返回到查询的地方
        return "search";

    }

    @GetMapping("/blog/{id}")   //请求根路径
    public String blog(@PathVariable Long id,Model model){     //PathVariable  路径变量  即获取url的参数
        model.addAttribute("blog",blogService.getBlog(id));
        return "blog";   //若无异常。则返回到index页面
    }

}
