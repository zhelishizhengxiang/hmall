package com.hmall.trade;

import com.hmall.api.config.FeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @version 1.0
 * @ProjectName: hmall
 * @Package: com.hmall.user
 * @Description:
 * @Author: Simon
 * @CreateDate: 2026/3/24
 */

@EnableFeignClients(value = "com.hmall.api.feign", defaultConfiguration = FeignConfig.class)
@MapperScan("com.hmall.trade.mapper")
@SpringBootApplication
public class TradeApplication {
    public static void main(String[] args) {
        SpringApplication.run(TradeApplication.class,args);
    }
}
