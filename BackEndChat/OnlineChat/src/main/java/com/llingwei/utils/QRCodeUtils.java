package com.llingwei.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Component
public class QRCodeUtils {
	
	public void createQRCode(String filePath, String content) {
		int width=300;      		//width of picture
        int height=300;     		//height of picture
        String format="png";    	//format of picture

        /**
         * define parameters of QR code
         */
        HashMap hints=new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET,"utf-8");    //set encode as “utf-8”
        hints.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.M);  //set ErrorCorrectionLevel as medium
        hints.put(EncodeHintType.MARGIN, 2);    //set margin of picture

        /**
         * generate QR Code
         */
        try {
            BitMatrix bitMatrix=new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height,hints);
            Path file=new File(filePath).toPath();
            MatrixToImageWriter.writeToPath(bitMatrix, format, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public String getContentFromQRCode(String filePath) {
		MultiFormatReader formatReader=new MultiFormatReader();
        File file=new File(filePath);
        BufferedImage image;
        try {
            image = ImageIO.read(file);
            BinaryBitmap binaryBitmap=new BinaryBitmap(new HybridBinarizer
                                    (new BufferedImageLuminanceSource(image)));
            HashMap hints=new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET,"utf-8");
            Result result=formatReader.decode(binaryBitmap,hints);
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}
}
