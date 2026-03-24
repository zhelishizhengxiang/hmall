package com.hmall.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @version 1.0
 * @ProjectName: hmall
 * @Package: com.hmall.api.feign
 * @Description:
 * @Author: Simon
 * @CreateDate: 2026/3/25
 */
@FeignClient("user-service")
public interface UserClient {
    @PutMapping("/users/money/deduct")
    void deductMoney(@RequestParam("pw") String pw, @RequestParam("amount") Integer amount);
}
