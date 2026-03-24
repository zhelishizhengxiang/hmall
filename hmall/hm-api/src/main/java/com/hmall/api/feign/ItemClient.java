package com.hmall.api.feign;


import com.hmall.api.dto.ItemDTO;
import com.hmall.api.dto.OrderDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/**
 * @version 1.0
 * @ProjectName: hmall
 * @Package: com.hmall.cart.feign
 * @Description:
 * @Author: Simon
 * @CreateDate: 2026/3/24
 */
@FeignClient(value = "item-service")
public interface ItemClient {


    @GetMapping("/items")
    public List<ItemDTO> queryItemByIds(@RequestParam("ids") Collection<Long> ids);

    @PutMapping("/items/stock/deduct")
    public void deductStock(@RequestBody List<OrderDetailDTO> items);

}
