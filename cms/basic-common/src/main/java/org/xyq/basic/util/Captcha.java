package org.xyq.basic.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Captcha {
	private int width;
	private int height;
	private int num = 4;
	private String code;
	private static final Random ran = new Random();
	private static Captcha captcha;
	private Captcha(){
		code = "0123456789";
	}
	public static Captcha getInstance(){
		if(captcha == null){
			captcha = new Captcha();
		}
		return captcha;
	}
	
	public void set(int width, int height, int num, String code){
		this.width = width;
		this.height = height;
		this.num = num;
		this.code = code;
	}
	public void set(int width, int height){
		this.width = width;
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String generateCheckcode(){
		StringBuffer cc = new StringBuffer();
		for(int i=0; i<num; i++){
			cc.append(code.charAt(ran.nextInt(code.length())));
		}
		return cc.toString();
	}
	
	public BufferedImage generateCheckImage(String checkcode){
		//创建一个图片对象
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//获取图片对象的画笔
		Graphics graphics = img.createGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, width, height);
		graphics.setColor(Color.BLACK);
		graphics.drawRect(0, 0, width-1, height-1);
		Font font = new Font("宋体", Font.BOLD+Font.ITALIC, (int)(height*0.8));
		graphics.setFont(font);
		for(int i=0; i<num; i++){
			graphics.setColor(new Color(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255)));
			graphics.drawString(String.valueOf(checkcode.charAt(i)), i*(width/num)+4, (int)(height*0.8));
		}
		//加一些点
		for(int i=0; i<(width+height); i++){
			graphics.setColor(new Color(ran.nextInt(180), ran.nextInt(180), ran.nextInt(180)));
			graphics.drawOval(ran.nextInt(width), ran.nextInt(height), 1, 1);
		}
		//加一些线
		for(int i=0; i<4; i++){
			graphics.setColor(new Color(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255)));
			graphics.drawLine(0, ran.nextInt(height), width, ran.nextInt(height));
		}
		return img;
	}
	
}
