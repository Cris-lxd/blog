package com.lxd.web;
import com.lxd.dao.TypeRepository;
import com.lxd.mapper.UserMapper;
import com.lxd.po.Blog;
import com.lxd.po.Comment;
import com.lxd.service.BlogService;
import com.lxd.service.CommentService;
import com.lxd.service.TagService;
import com.lxd.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

//import	java.beans.WeakIdentityMap.Entry;

@Controller
public class indexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private UserMapper userMapper;


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
        model.addAttribute("comment",commentService.findCommentByBlogId(id));
        return "blog";   //若无异常。则返回到index页面
    }

    @RequestMapping("/blog/comment")
    @ResponseBody
    public void comment(HttpServletRequest request){
        Long blogId = Long.valueOf(request.getParameter("blogId")) ;
        String nickname1 = request.getParameter("name");
        String content = request.getParameter("content");
        String email = request.getParameter("email");
        Comment c =new Comment();
        Blog b = new Blog();
        b.setId(blogId);
        c.setCreateTime(new Date());
        c.setName(nickname1);
        c.setContent(content);
        c.setEmail(email);
        c.setBlog(b);
        commentService.saveComment(c);

    }


    @RequestMapping("/echarts")
    @ResponseBody
    public List<Map<String,String>> echarts(HttpServletRequest request){
        List<Map<String, String>> a = userMapper.findA();
        List<Map<String,String>> list = new ArrayList<Map<String,String>> ();
        for (int i = 0; i < a.size(); i++) {
            Map<String,String> map = new LinkedHashMap<String,String>();
            String value = String.valueOf(a.get(i).get("count"));
            String name = a.get(i).get("name");
            map.put("value",value);
            map.put("name",name);
            list.add(map);
        }
        return list;
    }


    public final static String getIpAddress(HttpServletRequest request){
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }

}
