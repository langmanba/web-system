package com.web.portal.web.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "web-system-center-web")
public interface ProtalFeignClient {

    @RequestMapping("/rest/mainc")
    String getShow();
}
