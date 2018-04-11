package com.web.portal.web.service;

import com.web.portal.web.pojo.Result;
import com.web.portal.web.pojo.UserBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ImageService {
    public void createImg(HttpServletRequest request, HttpServletResponse response) throws IOException;

    public void createMathImg(HttpServletRequest request, HttpServletResponse response) throws IOException;

    Result checkPicYzm(UserBean bean, HttpServletRequest request);
}
