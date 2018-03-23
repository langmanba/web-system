package com.web.portal.web.controller;

import com.web.portal.web.pojo.Result;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 继承ErrorController,可以统一处理错误返回信息
 */

public class BaseController implements ErrorController{
    @Override
    public String getErrorPath() {
        return null;
    }

    @RequestMapping
    public Result doHandleError() {
        return new Result();
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();
        return new Result();
    }

}
