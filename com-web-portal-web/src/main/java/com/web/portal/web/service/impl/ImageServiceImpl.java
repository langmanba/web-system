package com.web.portal.web.service.impl;

import com.util.CheckUtil;
import com.util.VerificationCodeTool;
import com.web.portal.web.pojo.Result;
import com.web.portal.web.pojo.UserBean;
import com.web.portal.web.pojo.UserConstants;
import com.web.portal.web.service.ImageService;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Component("imageService")
public class ImageServiceImpl implements ImageService {
    @Override
    public void createImg(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        VerificationCodeTool vct = new VerificationCodeTool();
        BufferedImage image = vct.getnumberImage();
        HttpSession session = request.getSession(true);
        try {
            ServletOutputStream localServletOutputStream = response.getOutputStream();
            session.setAttribute(UserConstants.SESSION_YZM, vct.getXyresult() + "");
            ImageIO.write(image, "JPEG", localServletOutputStream);
            localServletOutputStream.flush();
            localServletOutputStream.close();
            System.out.println("验证码生成成功,内容:" + vct.getRandomString() + " 结果:" + vct.getXyresult());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void createMathImg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        VerificationCodeTool vct = new VerificationCodeTool();
        BufferedImage image = vct.drawVerificationCodeImage();
        HttpSession session = request.getSession(true);
        try {
            ServletOutputStream localServletOutputStream = response.getOutputStream();
            session.setAttribute(UserConstants.SESSION_YZM, vct.getXyresult() + "");
            ImageIO.write(image, "JPEG", localServletOutputStream);
            localServletOutputStream.flush();
            localServletOutputStream.close();
            System.out.println("验证码生成成功,内容:" + vct.getRandomString() + " 结果:" + vct.getXyresult());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public Result checkPicYzm(UserBean bean, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null){
            String yzm = (String) session.getAttribute(UserConstants.SESSION_YZM);
            if (! CheckUtil.isNullString(yzm) && ! CheckUtil.isNullString(bean.getYzm())) {
                if ( yzm.equalsIgnoreCase(bean.getYzm())) {
                    bean.setBusiErrCode(0);
                    bean.setBusiErrDesc("验证码正确");
                } else {
                    bean.setBusiErrCode(-1);
                    bean.setBusiErrDesc("验证码错误");
                }
            } else {
                bean.setBusiErrCode(-1);
                bean.setBusiErrDesc("验证码错误");
            }
            Result result = new Result(bean.getBusiErrCode(), bean.getBusiErrDesc());
            return result;
        }
        return null;
    }
}
