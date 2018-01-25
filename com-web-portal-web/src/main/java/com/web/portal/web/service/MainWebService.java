package com.web.portal.web.service;

import com.web.portal.web.feign.ProtalFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainWebService {

    @Autowired
    ProtalFeignClient protalFeignClient;

    public String getShow(){
        return protalFeignClient.getShow();
    }
}
