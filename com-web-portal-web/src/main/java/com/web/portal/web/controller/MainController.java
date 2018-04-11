package com.web.portal.web.controller;

import com.web.portal.web.pojo.Result;
import com.web.portal.web.pojo.UserBean;
import com.web.portal.web.service.ImageService;
import com.web.portal.web.service.MainWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class MainController {

    @Autowired
    MainWebService mainWebService;
    @Autowired
    ImageService imageService;

    @RequestMapping(value = "/test")
    String show() {

        return mainWebService.getShow();
    }

    @RequestMapping(value = "/img")
    public String getImg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        imageService.createImg(request,response);
        return null;
    }

    @RequestMapping(value = "/math_img")
    public String getMath(HttpServletRequest request, HttpServletResponse response) throws IOException {
        imageService.createMathImg(request,response);
        return null;
    }

    /**
     * 验证图片验证码
     *
     * @param bean
     * @param request
     */
    @RequestMapping(value = "/user/check_pic_yzm.api")
    public Result checkPicYzm(UserBean bean, HttpServletRequest request) {
        return imageService.checkPicYzm(bean, request);
    }


}
