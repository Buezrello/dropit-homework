package com.example.dropit.feign;

import com.example.dropit.model.geoapify.GeoapifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "geoapify-server", url = "${geoapify.host}")
public interface GeoapifyFeignClient {

    @GetMapping("/v1/geocode/search")
    GeoapifyResponse getGeoapifyResponse(@RequestParam String text, @RequestParam String apiKey);
}
