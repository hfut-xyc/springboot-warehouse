package org.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.server.utils.CaptchaGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;


@Api(tags = "CaptchaController", description = "验证码生成")
@RestController
public class CaptchaController {

    private static CaptchaGenerator generator = new CaptchaGenerator();

    @ApiOperation("登录验证码生成接口")
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
       BufferedImage image = generator.createImage();
       request.getSession().setAttribute("code", generator.getCode());
       ImageIO.write(image, "JPEG", response.getOutputStream());
    }

}
