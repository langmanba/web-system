package com.web.portal.web.controller;

import com.web.portal.web.service.ImageService;
import com.web.portal.web.service.MainWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

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
        imageService.createImg(response);
        return null;
    }



}
