package com.hmall.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @version 1.0
 * @ProjectName: hmall
 * @Package: com.hmall.api.feign
 * @Description:
 * @Author: Simon
 * @CreateDate: 2026/3/25
 */
@FeignClient("trade-service")
public interface TradeClient {
    @PutMapping("/orders/{orderId}")
    void markOrderPaySuccess(@PathVariable("orderId") Long orderId) ;
}
