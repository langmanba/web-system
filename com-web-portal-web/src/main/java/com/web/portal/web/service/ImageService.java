package com.web.portal.web.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ImageService {
    public void createImg(HttpServletResponse response) throws IOException;
}
