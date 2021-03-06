package com.web.portal.center.controller;

import com.web.portal.center.dao.QueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainCenterController {

    @Autowired
    QueryMapper mapper;

    @Value("${server.port}")
    String port;
    @Value("${spring.application.name}")
    String name;

    @RequestMapping(value = "/rest/mainc")
    String show() {
        return "Response from:" + name + "，port:" + port;
    }

    @RequestMapping(value = "/getCity")
    String showList(){
        return mapper.getAge("lyb123");
    }
}
