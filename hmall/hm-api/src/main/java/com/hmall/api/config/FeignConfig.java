package com.hmall.api.config;

import com.hmall.common.utils.UserContext;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;

/**
 * @version 1.0
 * @ProjectName: hmall
 * @Package: com.hmall.api.config
 * @Description:
 * @Author: Simon
 * @CreateDate: 2026/3/24
 */
public class FeignConfig {
    @Bean
    public Logger.Level feignLogLevel(){
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor userInfoRequestInterceptors(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                Long userId = UserContext.getUser();
                if (userId!=null){
                    requestTemplate.header("user-info",userId.toString());
                }

            }
        };
    }

}
