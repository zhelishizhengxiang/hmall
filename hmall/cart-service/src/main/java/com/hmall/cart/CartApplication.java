package com.hmall.cart;

import com.hmall.api.config.FeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @version 1.0
 * @ProjectName: hmall
 * @Package: PACKAGE_NAME
 * @Description:
 * @Author: Simon
 * @CreateDate: 2026/3/23
 */

@EnableFeignClients(value = "com.hmall.api.feign", defaultConfiguration = FeignConfig.class)
@MapperScan("com.hmall.cart.mapper")
@SpringBootApplication
public class CartApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class,args);
    }
}
