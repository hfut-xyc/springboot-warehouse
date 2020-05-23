package org.server.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CaptchaGenerator {

    private static final int WIDTH = 100;

    private static final int HEIGHT = 30;

    private static final String sequence = "0123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static Random random = new Random();

    private static Font font = new Font("宋体", Font.BOLD, 25);

    private String code;

    // 随机取色
    private static Color getRandomColor() {
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    // 随机生成4个字符的验证码
    private static String getRandomCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            code.append(sequence.charAt(random.nextInt(sequence.length())));
        }
        return code.toString();
    }

    public BufferedImage createImage() {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(font);
        // 填充矩形框背景颜色为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // 添加验证码序列
        this.code = getRandomCode();
        for(int i = 0; i < 4; i++) {
            g.setColor(getRandomColor());
            g.drawString(String.valueOf(code.charAt(i)), 20 * i + 15, 25);
        }
        // 绘制干扰线
        for (int i = 0; i < 5; i++) {
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            g.setColor(getRandomColor());
            g.drawLine(x1, y1, x2, y2);
        }
        return image;
    }

    public String getCode() {
        return this.code;
    }
}
