package com.springboot.games.interceptor;

import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.games.configuration.AESUtil;
import com.springboot.games.controller.GamesController;


@Component
public class GamesInterceptor implements HandlerInterceptor {
	
	private static Logger log = LoggerFactory.getLogger(GamesInterceptor.class);
	
//	timestamp~randomstring~randomstring

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("preHandle called");
		
//		String auth = request.getHeader("authorization");
//		log.info("authorization " + auth);
//		byte[] decodedBytes = Base64.getDecoder().decode(auth);
//		String decodedString = new String(decodedBytes);
//		
//		String arr[] = decodedString.split("~");
//		
//		byte[] decodedBytesTime = Base64.getDecoder().decode(arr[0]);
//		String timeStampBase64 = new String(decodedBytesTime);
//		
//		byte[] decodedBytesStringOne = Base64.getDecoder().decode(arr[1]);
//		String stringOneBase64 = new String(decodedBytesStringOne);
//		
//		byte[] decodedBytesStringTwo = Base64.getDecoder().decode(arr[2]);
//		String stringTwoBase64 = new String(decodedBytesStringTwo);
//		
//		Date date = new Date(Long.parseLong(timeStampBase64));
//		log.info("new Date sec - " + new Date().getSeconds());
//		log.info("old Date sec - " + date.getSeconds());
//		
//		if((new Date().getTime() - date.getTime())>50){
//			return false;
//		}
//		
//		if(!("Hello".equals(stringOneBase64))) {
//			return false;
//		}
//		
//		if(!("Hello".equals(stringTwoBase64))) {
//			return false;
//		}
		
		String auth =  new String((request.getHeader("authorization")));
		 String password = "";
        if (auth != null) {
            log.info("Password decrypted successfully for username - " + auth);
             password = decryptCode(auth);
            log.info("password - " + password);
        }
        String[] arr = password.split("~");
        Date myDate = new Date();
        long myDateTime = myDate.getTime();
        log.info("myDate - " + myDate + " myDateTime -" + myDateTime);
        Date authDate = new Date(Long.parseLong(arr[0]));
        long authDateTime = authDate.getTime();
        log.info("authDate - " + authDate + " authDateTime -" + authDateTime);
        long diff = myDateTime-authDateTime;
        log.info("DIff - " + diff);
        if(diff>10*1000) {
        	log.info("TIme diff is great");
        	return false;
        }
        if(!arr[1].equals("Authorization123@"))
        	return false;
        
        
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("postHandle called");
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("afterCompletion called");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
	public static String decryptCode(String cipherText) {
		String message="";
		String secret = "ArpitIsTheBest";
		
		byte[] cipherData = Base64.getDecoder().decode(cipherText);
		byte[] saltData = Arrays.copyOfRange(cipherData, 8, 16);
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			final byte[][] keyAndIV = GenerateKeyAndIV(32, 16, 1, saltData, secret.getBytes(StandardCharsets.UTF_8), md5);
			SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
			IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);

			byte[] encrypted = Arrays.copyOfRange(cipherData, 16, cipherData.length);
			Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
			aesCBC.init(Cipher.DECRYPT_MODE, key, iv);
			byte[] decryptedData = aesCBC.doFinal(encrypted);
			String decryptedText = new String(decryptedData, StandardCharsets.UTF_8);

			log.info("decryptedText called - " + decryptedText);
			message = decryptedText;
			
		} catch (Exception e) {
			log.info("Exception in decryption -" + e.getMessage());
		}

		
		
		return message;
	}
	
	public static byte[][] GenerateKeyAndIV(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password, MessageDigest md) {

	    int digestLength = md.getDigestLength();
	    int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;
	    byte[] generatedData = new byte[requiredLength];
	    int generatedLength = 0;

	    try {
	        md.reset();

	        // Repeat process until sufficient data has been generated
	        while (generatedLength < keyLength + ivLength) {

	            // Digest data (last digest if available, password data, salt if available)
	            if (generatedLength > 0)
	                md.update(generatedData, generatedLength - digestLength, digestLength);
	            md.update(password);
	            if (salt != null)
	                md.update(salt, 0, 8);
	            md.digest(generatedData, generatedLength, digestLength);

	            // additional rounds
	            for (int i = 1; i < iterations; i++) {
	                md.update(generatedData, generatedLength, digestLength);
	                md.digest(generatedData, generatedLength, digestLength);
	            }

	            generatedLength += digestLength;
	        }

	        // Copy key and IV into separate byte arrays
	        byte[][] result = new byte[2][];
	        result[0] = Arrays.copyOfRange(generatedData, 0, keyLength);
	        if (ivLength > 0)
	            result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);

	        return result;

	    } catch (DigestException e) {
	        throw new RuntimeException(e);

	    } finally {
	        // Clean out temporary data
	        Arrays.fill(generatedData, (byte)0);
	    }
	}
	

}
