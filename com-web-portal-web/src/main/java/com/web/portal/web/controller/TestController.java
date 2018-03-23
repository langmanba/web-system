package com.web.portal.web.controller;

import com.web.portal.web.pojo.Result;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController extends BaseController {

    @RequestMapping(value = "/trr")
    public String doHandleErrosr() {
        System.out.println("".substring(2));
        return "eeer";
    }



    @RequestMapping(value = "/error")
    public Result doError() {
        Result result = new Result();
        return result;
    }
}
