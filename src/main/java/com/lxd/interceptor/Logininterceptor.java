package com.lxd.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Cris on 2020/3/24
 */
public class Logininterceptor extends HandlerInterceptorAdapter {         //处理器拦截器


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {    //重写他的预处理方法


        if(request.getSession().getAttribute("user") == null){     //获取到的缓存里面的用户信息若为空
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
