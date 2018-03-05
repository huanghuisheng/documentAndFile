//package com.tone.tool.qrcodeutils;
//
//import javax.imageio.ImageIO;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//
///**
// * 校验码生成servlet
// * @author feng.xuan
// *
// */
//public class ValidatorServlet extends HttpServlet {
//	/**
//	 *
//	 */
//	private static final long serialVersionUID = 1L;
//
//	protected void doGet ( HttpServletRequest request ,
//			HttpServletResponse response )
//	throws ServletException , IOException
//	{
//		HttpSession session = request.getSession () ;
//
//		response.setContentType ( "image/jpeg" ) ;//设置类型为image
//		response.setHeader ( "Pragma" , "No-cache" ) ;//设置无缓存
//		response.setHeader ( "Cache-Control" , "no-cache" ) ;
//		response.setDateHeader ( "Expires" , 0 ) ;
//		//随机字符验证码 creatImage,随机加减数验证码createImage1,随机中文验证码createImage2
////		Random random = new Random () ;
////		int aNum = random.nextInt(10)>1?1:0;  //算数验证 80%几率 中文验证20%几率
//		ValidatorGenerator image = new ValidatorGenerator () ;
////		if(aNum==0){
////			ImageIO.write ( image.creatImage() , "JPEG" , response.getOutputStream () ) ;//输出图像到页面
////		}else
////		if(aNum==1){
//			ImageIO.write ( image.creatImage1() , "JPEG" , response.getOutputStream () ) ;//输出图像到页面
////		}else{
////			ImageIO.write ( image.creatImage2() , "JPEG" , response.getOutputStream () ) ;//输出图像到页面
////		}
//		session.setAttribute ( "validatorNumber" , image.sRand ) ;//保存产生的验证码
//	}
//
////	protected void doPost ( HttpServletRequest request ,
////			HttpServletResponse response )
////	throws ServletException , IOException
////	{
////		doGet ( request , response ) ;
////	}
//}
