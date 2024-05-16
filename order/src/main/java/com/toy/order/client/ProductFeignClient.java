package com.toy.order.client;

import com.toy.order.dto.CheckOptionForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductFeignClient {

    // 한번에 조회되도록 변경
    @Deprecated
    @GetMapping(value = "/options/{optionCode}")
    ProductOptionFeignResponse getProductOption(@PathVariable("optionCode") String optionCode);

    @PostMapping(value = "/options/purchasable")
    public boolean checkPurchasableOfProductOption(@RequestBody List<CheckOptionForm> forms);

    @PostMapping(value = "/options/inventories/deduct")
    public void deductInventory(@RequestBody List<CheckOptionForm> forms);
}
