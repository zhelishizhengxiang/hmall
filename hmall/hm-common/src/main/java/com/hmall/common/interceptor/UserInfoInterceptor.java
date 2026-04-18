package com.hmall.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.hmall.common.utils.UserContext;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @version 1.0
 * @ProjectName: hmall
 * @Package: com.hmall.common.interceptor
 * @Description:
 * @Author: Simon
 * @CreateDate: 2026/4/18
 */
public class UserInfoInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获取用户信息
        String userId = request.getHeader("user-info");
        //2.有才会存入threadLocal
        if (StrUtil.isNotBlank(userId)){
            UserContext.setUser(Long.valueOf(userId));
        }
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.removeUser();
    }
}
