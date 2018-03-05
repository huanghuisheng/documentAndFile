package com.tone.tool.qrcodeutils;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * @功能说明 zxing二维码生成、识别
 * @author chaoyuhui
 * @创建时间：2016年4月20日
 */
public class QRCodeUtil {

	private static final String CHARSET = "UTF-8";
	private static final String FORMAT_NAME = "JPG";
	private static final int QRCODE_SIZE = 300;
	private static final int WIDTH = 60;
	private static final int HEIGHT = 60;
	
    /**
     * 生成二维码
     * @param content  二维码内容
     * @param imgPath  二维码logo图片路径
     * @param destPath	生成二维码路径
     * @param needCompress	是否压缩LOGO图片
     * @throws Exception
     */
    public static void encode(String content, String imgPath, String destPath,
            boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath,
                needCompress);
        mkdirs(destPath);
        ImageIO.write(image, FORMAT_NAME, new File(destPath));
    }
    
    /**
     * 生成二维码
     * @param content  二维码内容
     * @param imgPath  二维码logo图片路径
     * @param output	FileOutputStream
     * @param needCompress	是否压缩LOGO图片
     * @throws Exception
     */
    public static void encode(String content, String imgPath,
            FileOutputStream output, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath,
                needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
    }
    /**
     * 识别二维码
//     * @param qrcodePicfilePath
     * @return
     * @throws Exception
     */
    public static String decode(FileInputStream input) throws Exception {
    	/* 读取二维码图像数据 */
        BufferedImage image;
        image = ImageIO.read(input);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(
                image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable hints = new Hashtable();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();
        return resultStr;
    }

    /**
     * 识别二维码
     * @param qrcodePicfilePath
     * @return
     * @throws Exception
     */
    public static String decode(String qrcodePicfilePath) throws Exception {

    	/* 读取二维码图像数据 */
    	File file = new File(qrcodePicfilePath);
        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(
                image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable hints = new Hashtable();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();
        return resultStr;
    }
    
	/**
	 * 创建图片
	 * @param content
	 * @param imgPath
	 * @param needCompress
	 * @return
	 * @throws Exception
	 */
	private static BufferedImage createImage(String content,String imgPath,
			boolean needCompress) throws Exception{
		
		Hashtable hints= new Hashtable();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        // 插入图片
        QRCodeUtil.insertImage(image, imgPath, needCompress);
        return image;
	}
	
	/**
	 * 插入图片
	 * @param source
	 * @param imgPath
	 * @param needCompress
	 * @throws Exception
	 */
	private static void insertImage(BufferedImage source, String imgPath,
            boolean needCompress) throws Exception {
        File file = new File(imgPath);
        if (!file.exists()) {
            System.err.println(""+imgPath+"   该文件不存在！");
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

   
    /**
     * 创建文件夹
     * @param destPath
     */
    public static void mkdirs(String destPath) {
        File file =new File(destPath);   
        //当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

	
	public static void main(String[] args) throws Exception {
		//二维码内容
		String text = "https://www.baidu.com";
		//LOGO路径
		String imagesPath = "E:/test/logo.jpg";
		//生成二维码路径
		String destPath = "E:/test/logo_qrcode.jpg";

		FileOutputStream out = new FileOutputStream(destPath);
		FileInputStream input = new FileInputStream(destPath);
		
		//生成带logo 的二维码
        //QRCodeUtil.encode(text, imagesPath, destPath, true);
		QRCodeUtil.encode(text, imagesPath, out, true);

        //生成不带logo 的二维码
		//QRCodeUtil.encode(text,"",destPath,true);
//		QRCodeUtil.encode(text,"",out,true);

        //识别二维码
		//System.out.println(QRCodeUtil.decode(destPath));
        System.out.println(QRCodeUtil.decode(input));
	}
}
