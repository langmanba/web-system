package com.web.portal.center.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainCenterController {

    @Value("${server.port}")
    String port;
    @Value("${spring.application.name}")
    String name;

    @RequestMapping(value = "/rest/mainc")
    String show() {
        return "Response from:" + name + "，port:" + port;
    }

    @RequestMapping(value = "/getCity")
    List showList(){

    }
}
