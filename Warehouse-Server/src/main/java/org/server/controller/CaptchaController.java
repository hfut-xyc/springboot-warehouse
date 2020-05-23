package org.server.controller;

import org.server.utils.CaptchaGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;


@RestController
public class CaptchaController {

    private CaptchaGenerator generator = new CaptchaGenerator();

    @GetMapping("/captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
       BufferedImage image = generator.createImage();
       request.getSession().setAttribute("code", generator.getCode());
       ImageIO.write(image,"JPEG", response.getOutputStream());
    }

}
