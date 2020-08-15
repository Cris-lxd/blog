package com.lxd.web;/*
 *  create by 20224
 *  2020/7/26
 * */

import com.lxd.dao.BlogRepository;
import com.lxd.po.Blog;
import com.lxd.po.Tag;
import com.lxd.po.Type;
import com.lxd.service.BlogService;
import com.lxd.service.TagService;
import com.lxd.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Controller
@RequestMapping("/to")
public class ToController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogRepository blogRepository;

    /**
     * 分类
     * @param model
     * @return
     */
    @RequestMapping("/toTypes")
    public String toTypes(Model model){
        List<Type> types = typeService.listType();
        int countType = typeService.countType();
        List<Blog> blogByType = blogService.getBlogByType((long) 2);    //默认展示java类型
        model.addAttribute("types",types);
        model.addAttribute("countType",countType);
        model.addAttribute("blogByType",blogByType);
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(3));
        return "types";
    }

    /**
     * 标签页
     * @param model
     * @return
     */
    @RequestMapping("/toTags")
    public String toTags(Model model){
        List<Tag> tags = tagService.listTag();
        int countTag = tagService.countTag();
        List<Blog> blogByTag = blogService.getBlogByTag((long) 11);    //默认展示java类型
        model.addAttribute("tags",tags);
        model.addAttribute("countTag",countTag);
        model.addAttribute("blogByTag",blogByTag);
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(3));
        return "tags";
    }

    /**
     * 归档
     * @param model
     * @return
     * @throws ParseException
     */
    @RequestMapping("/toArchives")
    public String toArchives(Model model) throws ParseException {
        List<Blog> all = blogRepository.findAll();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Blog> blogWithDate = new ArrayList<Blog>();
        for (int i = 0; i < all.size(); i++) {
            String data = sdf.format(all.get(i).getCreateTime());
            if(data.substring(0,4).equals("2020")){
                blogWithDate.add(all.get(i));
            };
        }
        model.addAttribute("blogNum",blogRepository.count());
        model.addAttribute("blogWithDate",blogWithDate);
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(3));
        return "archives";
    }

    /**
     * 关于我页面
     * @param model
     * @return
     */
    @RequestMapping("/toAbout")
    public String toAbout(Model model){
        List<Tag> tags = tagService.listTag();
        List<Type> types = typeService.listType();
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(3));
        model.addAttribute("tags",tags);
        model.addAttribute("types",types);
        return "about";
    }
    @RequestMapping("/toIndex1")
    public String toIndex1(Model model){
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(3));
        return "redirect:/";
    }
    @RequestMapping("/toAdmin")
    public String toAdmin(){
        return "admin/login";
    }

    /**
     * 根据typeid获取对应blogs
     */

    @RequestMapping("/getBlogByType/{id}")
    public String getBlogByType(@PathVariable Long id,Model model){

        List<Type> types = typeService.listType();
        int countType = typeService.countType();
        List<Blog> blogByType = blogService.getBlogByType(id);
        model.addAttribute("types",types);
        model.addAttribute("countType",countType);
        model.addAttribute("blogByType",blogByType);
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(3));
        return "types";
    }

    /**
     * 根据tagid获取blogs
     */
    @RequestMapping("/getBlogByTag/{id}")
    public String getBlogByTag(@PathVariable Long id,Model model){

        List<Tag> tags = tagService.listTag();
        int countTag = tagService.countTag();
        List<Blog> blogByTag = blogService.getBlogByTag(id);
        model.addAttribute("tags",tags);
        model.addAttribute("countTag",countTag);
        model.addAttribute("blogByTag",blogByTag);
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(3));
        return "tags";
    }
}
