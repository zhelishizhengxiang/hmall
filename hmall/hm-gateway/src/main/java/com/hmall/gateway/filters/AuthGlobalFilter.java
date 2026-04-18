package com.hmall.gateway.filters;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.AntPathMatcher;
import com.hmall.common.exception.UnauthorizedException;
import com.hmall.gateway.config.AuthProperties;
import com.hmall.gateway.utils.JwtTool;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @version 1.0
 * @ProjectName: hmall
 * @Package: com.hmall.gateway.filters
 * @Description:
 * @Author: Simon
 * @CreateDate: 2026/4/18
 */
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(AuthProperties.class)
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private final AuthProperties authProperties;
    private final JwtTool jwtTool;
    private AntPathMatcher antPathMatcher=new AntPathMatcher();
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获取request
        ServerHttpRequest request = exchange.getRequest();
        //2.判断路径是否需要登录校验
        if (isExclude(request.getPath().toString())){
            return chain.filter(exchange);
        }
        //3.获取token
        HttpHeaders headers = request.getHeaders();
        //httpHeader其实是一个map，可从中直接获得对应的请求行数据
        List<String> authorization = headers.get("authorization");
        String token=null;
        if(!CollectionUtil.isEmpty(authorization)){
            token=authorization.get(0);
        }
        //4.校验并解析token
        Long userId=null;
        try {
            userId = jwtTool.parseToken(token);
        }catch (UnauthorizedException e){
            //拦截，设置状态码为401并返回
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        String userInfo=userId.toString();
        //5.传递用户信息
        ServerWebExchange ex = exchange.mutate()
                .request(builder -> builder.header("user-info", userInfo))
                .build();
        //6.放行,并传递最新的上下文
        return chain.filter(ex);
    }

    private boolean isExclude(String path) {
        //配置的是antPath的pathPattern，正则路径，所以此处不能直接使用equals进行判断
        for (String pathPattern: authProperties.getExcludePaths()){
            if (antPathMatcher.match(pathPattern,path))
                return true;
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
