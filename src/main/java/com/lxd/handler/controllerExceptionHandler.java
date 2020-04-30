package com.lxd.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
/*
*   1.定义一个异常类
*   2.实例化一个logger，创建一个命名子系统的记录器
*   3.利用ModelAndView进行获取异常并返回
* */



@ControllerAdvice
public class controllerExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)                      //表示这个方法可以做异常处理
    public ModelAndView exceptionhandler(HttpServletRequest request,Exception e) throws Exception {    //请求处理异常
        logger.error("Request URl:{},Exception:{}",request.getRequestURL(),e);   //将处理的异常加入到日志

        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) !=null ){    //判断你有无加了注解的异常处理，若有则不拦截直接叫个springboot自己处理
            throw e;
        }

        ModelAndView mv=new ModelAndView();
        mv.addObject("url");                        //获取url
        mv.addObject("exception",e);
        mv.setViewName("error/error");                                              //返回到自定义的error页面

        return mv;
    }
}
