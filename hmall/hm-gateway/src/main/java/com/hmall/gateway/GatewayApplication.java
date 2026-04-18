package com.hmall.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @ProjectName: hmall
 * @Package: com.hmall
 * @Description:
 * @Author: Simon
 * @CreateDate: 2026/4/8
 */
@SpringBootApplication
@Component
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);
    }
}
