package org.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


@RestController
public class CaptchaController {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 30;
    private static final String sequence = "0123456789abcdefghijkmnpqrstuvwxyz";
    private static final Font font = new Font("宋体", Font.BOLD, 25);
    private static final Random random = new Random();

    @GetMapping("/captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = createCode();
        BufferedImage image = createImage(code);

        request.getSession().setAttribute("code", code);
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    public static BufferedImage createImage(String code) {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // 绘制验证码字符
        for(int i = 0; i < 4; i++) {
            g.setColor(createRandomColor());
            g.drawString(String.valueOf(code.charAt(i)), 20 * i + 15, 25);
        }
        // 绘制干扰线
        for (int i = 0; i < 5; i++) {
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            g.setColor(createRandomColor());
            g.drawLine(x1, y1, x2, y2);
        }
        return image;
    }

    // 随机生成4个字符的验证码
    public static String createCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            code.append(sequence.charAt(random.nextInt(sequence.length())));
        }
        return code.toString();
    }

    private static Color createRandomColor() {
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
}
