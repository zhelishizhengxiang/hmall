package com.hmall.common.config;

import com.hmall.common.interceptor.UserInfoInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @version 1.0
 * @ProjectName: hmall
 * @Package: com.hmall.common.config
 * @Description:
 * @Author: Simon
 * @CreateDate: 2026/4/18
 */
@Configuration
//网关没有引入web启动器，也不需要这个拦截器。但是需要使用common这个module。所以需要加条件注解
@ConditionalOnClass(DispatcherServlet.class)
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //此处拦截所有路径即可，因为该拦截器只是为了添加用户信息，如果没有就不添加而已，登录拦截的工作以及再网关做过
        registry.addInterceptor(new UserInfoInterceptor());
    }
}
