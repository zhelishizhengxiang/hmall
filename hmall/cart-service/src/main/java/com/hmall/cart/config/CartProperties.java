package com.hmall.cart.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @ProjectName: hmall
 * @Package: com.hmall.cart.config
 * @Description:
 * @Author: Simon
 * @CreateDate: 2026/4/19
 */
@Data
@Component
@ConfigurationProperties(prefix = "hm.cart")
public class CartProperties {
    private Integer maxItems;
}
