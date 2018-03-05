package com.tone.tool.qrcodeutils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * 校验码生成,
 * @author feng.xuan
 *
 */
public class ValidatorGenerator {
	
	public String sRand = null ;
	
	public Color getRandColor ( int fc , int bc )
	{
		//给定范围获得随机颜色
		Random random = new Random () ;
		if ( fc > 255 )
			fc = 255 ;
		if ( bc > 255 )
			bc = 255 ;
		int r = fc + random.nextInt ( bc - fc ) ;
		int g = fc + random.nextInt ( bc - fc ) ;
		int b = fc + random.nextInt ( bc - fc ) ;
		return new Color ( r , g , b ) ;
	}
	
	public BufferedImage creatImage ()
	{
		// 在内存中创建图象
//		int width = 60 , height = 20 ;
		int width = 75, height = 25 ;
		BufferedImage image = new BufferedImage ( width , height ,
				BufferedImage.TYPE_INT_RGB ) ;
		
		// 获取图形上下文
		Graphics g = image.getGraphics () ;
		
		//生成随机类
		Random random = new Random () ;
		
		// 设定背景色
		g.setColor ( getRandColor ( 200 , 250 ) ) ;
		g.fillRect ( 0 , 0 , width , height ) ;
		
		//设定字体
		//generated for windows
		g.setFont ( new Font ( "Times New Roman" , Font.PLAIN , 20 ) ) ;
		//generated for linux
		//g.setFont ( new Font ( "Times New Roman" , Font.PLAIN , 15 ) ) ;
		
		
		//画边框
		//g.setColor(new Color());
		//g.drawRect(0,0,width-1,height-1);
		
		// 随机产生156条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor ( getRandColor ( 160 , 200 ) ) ;
		for ( int i = 0 ; i < 156 ; i++ )
		{
			int x = random.nextInt ( width ) ;
			int y = random.nextInt ( height ) ;
			int xl = random.nextInt ( 12 ) ;
			int yl = random.nextInt ( 12 ) ;
			g.drawLine ( x , y , x + xl , y + yl ) ;
		}
		
		// 取随机产生的认证码(4位数字)
		//String rand = request.getParameter("rand");
		//rand = rand.substring(0,rand.indexOf("."));
//		int fsize=(int)(height*0.8);//字体大小为图片高度的80%
//        int fx=0;
//        int fy=fsize;
//        g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,fsize));
//        //写字符
//        for(int i=0;i<textCode.length();i++){
//            fy=randomLocation?(int)((Math.random()*0.3+0.6)*height):fy;//每个字符高低是否随机
//            g.setColor(foreColor==null?getRandomColor():foreColor);
//            g.drawString(textCode.charAt(i)+"",fx,fy);
//            fx+=(width / textCode.length()) * (Math.random() * 0.3 + 0.8); //依据宽度浮动
//    　　　}
		//随机字符验证码
		StringBuffer sb = new StringBuffer();
		for ( int i = 0 ; i < 5 ; i++ )
		{
			int rr = random.nextInt ( 200 );
			String rand = getRandomString ( rr ) ;
			sb.append(rand);
//			g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,fsize));
			
			// 将认证码显示到图象中
			g.setColor ( new Color ( 20 + random.nextInt ( 110 ) ,
					20 + random.nextInt ( 110 ) ,
					20 + random.nextInt ( 110 ) ) ) ; //调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString ( rand , 13 * i + 6 , 16 ) ;
		}
		sRand = sb.toString();
		
		// 图象生效
		g.dispose () ;
		return image ;
	}
	
	//加减验证码
	public BufferedImage creatImage1 ()
	{
		// 在内存中创建图象
		//int width = 75, height = 25 ;
		int width = 120, height =40 ;
		BufferedImage image = new BufferedImage ( width , height ,
				BufferedImage.TYPE_INT_RGB ) ;
		
		// 获取图形上下文
		Graphics2D g = (Graphics2D) image.getGraphics () ;
		
		//生成随机类
		Random random = new Random () ;
		
		// 设定背景色
		g.setColor ( getRandColor ( 200 , 250 ) ) ;
		g.fillRect ( 0 , 0 , width , height ) ;
		
		//设定字体
		g.setFont ( new Font ( "Times New Roman" , Font.PLAIN , 22 ) ) ;

		//画边框
		// 随机产生156条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor ( getRandColor ( 160 , 200 ) ) ;
		for ( int i = 0 ; i < 156 ; i++ )
		{
			int x = random.nextInt ( width ) ;
			int y = random.nextInt ( height ) ;
			int xl = random.nextInt ( 12 ) ;
			int yl = random.nextInt ( 12 ) ;
			g.drawLine ( x , y , x + xl , y + yl ) ;
		}
		
		//随机加减法验证码
		Map<String,Object> map = getRadomDigitalVerification();
		g.setColor ( new Color ( 20 + random.nextInt ( 110 ) ,
					20 + random.nextInt ( 110 ) ,
					20 + random.nextInt ( 110 ) ) ) ; //调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
		int x=5;
		for (int i = 0; i < 5; i++) {
			String aaa = "rand"+(i+1);
			int y = 28;
			if(i==1){
				 g.drawString ( map.get(aaa).toString() , x , y) ;  //加减符号不倾斜
				 x += 25;
				 continue;
			}
			// 设置字体旋转角度  
            int degree = new Random().nextInt() % 30;   
            // 正向角度  
            g.rotate(degree * Math.PI / 180, x, y);  
            g.drawString ( map.get(aaa).toString() , x , y) ;
            // 反向角度  
            g.rotate(-degree * Math.PI / 180, x, y);
            x += 25;
		}
		sRand = map.get("result").toString();   //设置结果
		// 图象生效
		g.dispose () ;
		return image ;
	}
	
	//中文验证码
	public BufferedImage creatImage2 ()
	{
		// 在内存中创建图象
//		int width = 60 , height = 20 ;
		int width = 120, height =40 ;
		BufferedImage image = new BufferedImage ( width , height ,
				BufferedImage.TYPE_INT_RGB ) ;
		
		// 获取图形上下文
		Graphics2D g = (Graphics2D) image.getGraphics () ;
		
		//生成随机类
		Random random = new Random () ;
		
		// 设定背景色
		g.setColor ( getRandColor ( 200 , 250 ) ) ;
		g.fillRect ( 0 , 0 , width , height ) ;
		
		// 设置字体  
        g.setFont(new Font("宋体", Font.BOLD, 20)); 
		//generated for linux
		//g.setFont ( new Font ( "Times New Roman" , Font.PLAIN , 15 ) ) ;
		
		
		//画边框
		//g.setColor(new Color());
		//g.drawRect(0,0,width-1,height-1);
		
		// 随机产生156条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor ( getRandColor ( 160 , 200 ) ) ;
		for ( int i = 0 ; i < 156 ; i++ )
		{
			int x = random.nextInt ( width ) ;
			int y = random.nextInt ( height ) ;
			int xl = random.nextInt ( 12 ) ;
			int yl = random.nextInt ( 12 ) ;
			g.drawLine ( x , y , x + xl , y + yl ) ;
		}
		

		//随机字符验证码
		StringBuffer sb = new StringBuffer();
		 int x=10;
		for ( int i = 0 ; i < 2 ; i++ )
		{
			String rand = String.valueOf(createHexCode());
			sb.append(rand);	
			// 将认证码显示到图象中
			g.setColor ( new Color ( 20 + random.nextInt ( 110 ) ,
					20 + random.nextInt ( 110 ) ,
					20 + random.nextInt ( 110 ) ) ) ; //调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			// 设置字体旋转角度  
            int degree = new Random().nextInt() % 30;   
            // 正向角度  
            int y = 28;
            g.rotate(degree * Math.PI / 180, x, y);  
			g.drawString ( rand ,x , y ) ;
		    g.rotate(-degree * Math.PI / 180, x, y);  
		    x += 50;
		}
		sRand = sb.toString();
		
		// 图象生效
		g.dispose () ;
		return image ;
	}
	
	/**
	 * 根据随机数产生字符
	 * @param rand
	 * @return
	 */
	public String getRandomString(int rand){
		
		int i = rand%allString.length();
//		return "A";
		return allString.charAt(i)+"";
	}
	
	//去掉了容易引起看不清的1:I,0:O,Q
	private final String allString = "QWERTYUIOPLKJHGFDSAZXCVBNM123456789zxcvbnmlkjhgfdsaqwertyuiop";
	
	/**
	 * 随机生成100以内的加减法
	 * @return
	 */
	public Map<String,Object> getRadomDigitalVerification(){
		Random random = new Random () ;
		Map<String,Object> map = new HashMap<String,Object>();
		int aNum = 0;
		int bNum = 0;
		int result = 0;
		String str;
		aNum = random.nextInt(random.nextInt()>0.5?100:10);
		if(aNum<10){  //如果 a 为 1位数则 b=两位数并且相加不大于100
			bNum = random.nextInt(100-aNum);
		}else{  //a为 2位数则 b = 一位数 
			bNum = random.nextInt(10);
		}
		int ope = random.nextInt()>0.5?1:0;   // 1加 2减
		if(ope == 1){
			result = aNum + bNum;
			map.put("rand1", aNum);
			map.put("rand2", "+");
			map.put("rand3", bNum);
			map.put("result", result);
		}else{ 
			if(aNum<bNum){ //减法需要被减数少于减速
				int temp = aNum;
				aNum = bNum;
				bNum = temp;
			}
			result = aNum - bNum;
			map.put("rand1", aNum);
			map.put("rand2", "-");
			map.put("rand3", bNum);
			map.put("result", result);
		}	
		map.put("rand4", "=");
		map.put("rand5", "?");
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

