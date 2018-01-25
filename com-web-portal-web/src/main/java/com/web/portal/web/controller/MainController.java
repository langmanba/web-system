package com.web.portal.web.controller;

import com.web.portal.web.service.MainWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    MainWebService mainWebService;

    @RequestMapping(value = "/test")
    String show(){

        return mainWebService.getShow();
    }
}