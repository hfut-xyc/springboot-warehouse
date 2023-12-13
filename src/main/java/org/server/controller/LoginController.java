package org.server.controller;

import org.server.pojo.dto.Result;
import org.server.pojo.entity.User;
import org.server.service.UserService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


@RestController
public class LoginController {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 30;
    private static final String sequence = "0123456789abcdefghjkmnpqrstuvwxyz";
    private static final Font font = new Font("宋体", Font.BOLD, 25);
    private static final Random random = new Random();

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User form, HttpServletRequest request) throws Exception {
        String username = form.getUsername();
        String password = form.getPassword();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new Exception("用户名和密码不能为空");
        }
        User user = userService.findByUsername(username);
        if (user == null || !password.equals(user.getPassword())) {
            throw new Exception("登录失败");
        }
        user.setPassword(null);
        request.getSession().setAttribute("user", user);
        return Result.ok("登录成功");
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) throws Exception {
        request.getSession().removeAttribute("user");
        return Result.ok("注销成功");
    }

    @GetMapping("/captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = createCode();
        BufferedImage image = createImage(code);

        request.getSession().setAttribute("code", code);
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    private static BufferedImage createImage(String code) {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // 绘制验证码字符
        for(int i = 0; i < 4; i++) {
            g.setColor(createRandomColor());
            g.drawString("" + code.charAt(i), 20 * i + 15, 25);
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
    private static String createCode() {
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
