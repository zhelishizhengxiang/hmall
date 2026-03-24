package com.hmall.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

/**
 * @version 1.0
 * @ProjectName: hmall
 * @Package: com.hmall.api.feign
 * @Description:
 * @Author: Simon
 * @CreateDate: 2026/3/24
 */
@FeignClient("cart-service")
public interface CartClient {
    @DeleteMapping("/carts")
     void deleteCartItemByIds(@RequestParam("ids") Collection<Long> ids);
}
