package com.hmall.api.config;

import feign.Logger;
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
}
