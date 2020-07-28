package com.lxd.web.admin;

import com.lxd.po.User;
import com.lxd.service.UserService;
import com.lxd.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Cris on 2020/3/23
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;


    @GetMapping()            //默认使用全局  作用是第一次访问时返回到配置的页面
    public String loginPage(){
        return "admin/login";
    }

    /*
    * 登录
    * */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {    //拿到session
        User user=userService.checkUser(username, password);    //调用判断
        if(user !=null){
            user.setPassword(null);
            session.setAttribute("user",user);
            //return "/admin/index";
            return "admin/index";
        }else{
            attributes.addFlashAttribute("message", "用户名和密码错误");
            return "redirect:/admin";     //重定向到登录页面
        }
    }

    /*
    *注销用户
    */
    @GetMapping("/logout")      //注销到logout页面，重定向到登录页面
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
    @RequestMapping("/toAdmin")
    public String toAdmin(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "admin/login";
    }
}
