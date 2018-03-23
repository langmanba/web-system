package com.web.portal.web.service.impl;

import com.web.portal.web.service.ImageService;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

@Component("imageService")
public class ImageServiceImpl implements ImageService {
    @Override
    public void createImg(HttpServletResponse response) throws IOException {
        int i = 70;
        int j = 20;
        BufferedImage localBufferedImage = new BufferedImage(i, j, 1);
        Graphics localGraphics = localBufferedImage.getGraphics();
        Random localRandom = new Random();
        localGraphics.fillRect(0, 0, i, j);
        localGraphics.setFont(new Font("Times New Roman", 0, 18));
        localGraphics.setColor(_$1(160, 200));
        for (int k = 0; k < 155; ++k) {
            int l = localRandom.nextInt(i);
            int i1 = localRandom.nextInt(j);
            int i2 = localRandom.nextInt(12);
            int i3 = localRandom.nextInt(12);
            localGraphics.drawLine(l, i1, l + i2, i1 + i3);
        }
        String str2 = "";
        for (int l = 0; l < 5; ++l) {
            String str3 = String.valueOf(localRandom.nextInt(10));
            str2 = str2 + str3;
            localGraphics.setColor(new Color(20 + localRandom.nextInt(110),
                    20 + localRandom.nextInt(110), 20 + localRandom
                    .nextInt(110)));
            localGraphics.drawString(str3, 13 * l + 6, 16);
        }

        System.out.println(str2);

        localGraphics.dispose();
        localBufferedImage.flush();
        OutputStream os = response.getOutputStream();
        ImageIO.write(localBufferedImage, "JPEG", os);
        os.flush();
        os.close();
    }

    private static Color _$1(int paramInt1, int paramInt2) {
        Random localRandom = new Random();
        if (paramInt1 > 255)
            paramInt1 = 255;
        if (paramInt2 > 255)
            paramInt2 = 255;
        int i = paramInt1 + localRandom.nextInt(paramInt2 - paramInt1);
        int j = paramInt1 + localRandom.nextInt(paramInt2 - paramInt1);
        int k = paramInt1 + localRandom.nextInt(paramInt2 - paramInt1);
        return new Color(i, j, k);
    }
}
