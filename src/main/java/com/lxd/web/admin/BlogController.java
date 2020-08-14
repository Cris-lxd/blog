package com.lxd.web.admin;

import com.lxd.po.Blog;
import com.lxd.po.Tag;
import com.lxd.po.Type;
import com.lxd.po.User;
import com.lxd.service.BlogService;
import com.lxd.service.TagService;
import com.lxd.service.TypeService;
import com.lxd.vo.BlogQuery;
import org.hibernate.query.criteria.internal.predicate.ExistsPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cris on 2020/3/24
 */
@Controller
@RequestMapping("/admin")     //用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。
public class BlogController {

    private static final String INPUT="admin/blogs-input";
    private static final String LIST="admin/blogs";
    private static final String REDIRECT_LIST="redirect:/admin/blogs";
    private static final String DELETE="redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    /*  model实例化一般是为了拿到一个已经查询到的对象，放在model里面
    *
    * */
    public String blogs(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){

        model.addAttribute("types", typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable, blog));    //查询page对象放到model模型里面
        return LIST;
    }
    /*
    *  查询时返回的部分页面
    * */
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model){

        model.addAttribute("page",blogService.listBlog(pageable, blog));    //查询page对象放到model模型里面
        return "admin/blogs :: blogList";    //blogs页面下面的列表部分  ,对应blogs.html  83行
    }

    /*
    *   新增
    * */
    @GetMapping("/blogs/input")     //拿到默认值放入内容，对应blogs的新增按钮，渠道这个model一一赋值
    public String input(Type type, Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("blog", new Blog());
        return INPUT;
    }
    @GetMapping("/blogs/{id}/input")     //拿到默认值放入内容，对应blogs的新增按钮，渠道这个model一一赋值
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("firstPictures", "https://picsum.photos/seed/picsum/800/450");
        Blog blog=blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blog);
        return INPUT;
    }

    @PostMapping("/blogs")
    public String post(Blog blog, Tag tag, HttpSession session, RedirectAttributes attributes){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));    //对应blogs的type.id,获取到对应的type值
        blog.setTags(tagService.listTag(blog.getTagIds()));       //对应标签
        Blog b;
        //如果勾选了不需要首图，则将首图设置为null

        if(blog.getId() ==null){
            b=blogService.saveBlog(blog);
        }else{
            b=blogService.updateBlog(blog.getId(), blog);
        }

        if(b==null){
            attributes.addFlashAttribute("message", "操作失败");
        }else{
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }


    /*
    * 修改方法
    * */



    /*
    * 删除方法
    * */
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id){
        blogService.deleteBlog(id);
        return DELETE;
    }

}


