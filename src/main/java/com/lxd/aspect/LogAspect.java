package com.lxd.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.PrivateKey;
import java.util.Arrays;


/*
* aop横切控制器的请求
*
* */
@Aspect
@Component   //组件扫描    切面必须的两个注解
public class LogAspect {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    /*定义一个切面
    * */
    @Pointcut("execution(* com.lxd.web.*.*(..))")     //申明是一个切面    拦截web目录下的所有的了类方法
    public void log(){}

    @Before("log()")               //切面执行前执行
    public void doBefore(JoinPoint joinPoint){

        /*
        * 1.请求之前先获取到四个参数
        * 2.调用RequestLog构造器输出
        * */
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();   //获取
        HttpServletRequest request=attributes.getRequest();
        String url=request.getRequestURI();
        String ip=request.getRemoteAddr();    //地址
        /*
        * 前面获取类名，后面获取方法名
        * */
        String classMethod=joinPoint.getSignature().getDeclaringTypeName() + "." +joinPoint.getSignature().getName();
        Object[] args=joinPoint.getArgs();   //获取到请求的参数
        RequestLog requestLog=new RequestLog(url, ip, classMethod, args);   //调用构造器
        logger.info("Request {}",requestLog);

        logger.info("--------------doBefore--------");
    }
    @After("log()")
    public void doAfter(){
//        logger.info("--------------doAfter--------");
    }

    @AfterReturning(returning = "result",pointcut = "log()")   //通过result捕获
    public void doAfterReturn(Object result){    //方法执行完返回时拦截
        logger.info("result:{}" +result);

    }

    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {   //构造器
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {    //转化为字符串
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
