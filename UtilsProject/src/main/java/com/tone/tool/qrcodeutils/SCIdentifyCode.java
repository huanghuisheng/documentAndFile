package com.tone.tool.qrcodeutils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SCIdentifyCode {
	// 图片高度
	private static final int IMG_HEIGHT = 60;
	// 图片宽度
	private static final int IMG_WIDTH = 120;
	/**
	 * @param 简体中文验证码生成
	 */
	public static void main(String[] args) {
//		for(int i = 0; i < 4; i ++) {
//			System.out.print(createHexCode());
//		}
		getSCIdentifyCode(4);
	}
	/**
	 * Des: 得到生成的验证码图片
	 * @param num 生成的汉字个数
	 * @return map 返回str:图片中的汉字，img:生成的图片
	 */
	public static Map<String, Object> getSCIdentifyCode(int num) {
		if(num <= 0 || num > 8) {
			throw new ArrayIndexOutOfBoundsException("超出汉字生成个数");
		}
		Map<String,Object> map = new HashMap<String, Object>();
		char[] c = new char[num];
		// 分别生成num个汉字
		for(int i = 0; i < num; i ++) {
			c[i] = createHexCode();
		}
		// 生成汉字图片
		BufferedImage image = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.setBackground(Color.WHITE);
		// 设置字体  
        g.setFont(new Font("宋体", Font.BOLD, 20)); 
        
		int x = 5;
		int y = IMG_HEIGHT / 2;
		// 画汉字到画布上
		for(int j = 0; j < c.length; j ++) {
			// 设置字体旋转角度  
            int degree = new Random().nextInt() % 45;   
            // 正向角度  
            g.rotate(degree * Math.PI / 180, x, y);  
            g.drawString(String.valueOf(c[j]), x, y);  
            // 反向角度  
            g.rotate(-degree * Math.PI / 180, x, y);
            x += 30;
		}
		// 画出干扰线
		for(int n = 0; n < 8; n ++) {
	        // 设置线条个数并画线  
            int x1 = new Random().nextInt(IMG_WIDTH);  
            int y1 = new Random().nextInt(IMG_HEIGHT);  
            int x2 = new Random().nextInt(IMG_WIDTH);  
            int y2 = new Random().nextInt(IMG_HEIGHT);  
            g.drawLine(x1, y1, x2, y2);  
		}
		
		// 画出随机点
		for(int m = 0; m < 400; m ++) {
			int x1 = new Random().nextInt(IMG_WIDTH);  
            int y1 = new Random().nextInt(IMG_HEIGHT);  
            g.drawLine(x1, y1, x1, y1);
		}
		
		System.out.println(c);
		map.put("str", c);
		map.put("img", g);
		return map;
	}
	/**
	 * Des: 随机生成单个常用汉字
	 * @return char
	 */
	private static char createHexCode() {
		String str = "";
        int hightPos;
        int lowPos;

        Random random = new Random();

        hightPos = (176 + Math.abs(random.nextInt(39)));
        lowPos = (161 + Math.abs(random.nextInt(93)));

        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(hightPos)).byteValue();
        b[1] = (Integer.valueOf(lowPos)).byteValue();

        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("<=====<^_^>====>错误");
        }
        return str.charAt(0);
	}
}
