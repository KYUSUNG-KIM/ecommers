package com.toy.order.client;

import com.toy.ecommercecommon.global.dto.CommonResponse;
import com.toy.order.config.FeignClientConfig;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "member-service", configuration = FeignClientConfig.class)
public interface CustomerFeignClient {

    @PutMapping(value = "/members/{email}/point-deduct")
    public CommonResponse<Integer> deductPoint(@PathVariable("email") String email,
                                               @Valid @RequestBody PointDeductRequest request);
}