package com.springboot.games.interceptor;

import java.util.Base64;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.games.controller.GamesController;


@Component
public class GamesInterceptor implements HandlerInterceptor {
	
	private static Logger log = LoggerFactory.getLogger(GamesInterceptor.class);
	
//	timestamp~randomstring~randomstring

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("preHandle called");
		
		String auth = request.getHeader("authorization");
		log.info("authorization " + auth);
		byte[] decodedBytes = Base64.getDecoder().decode(auth);
		String decodedString = new String(decodedBytes);
		
		String arr[] = decodedString.split("~");
		
		byte[] decodedBytesTime = Base64.getDecoder().decode(arr[0]);
		String timeStampBase64 = new String(decodedBytesTime);
		
		byte[] decodedBytesStringOne = Base64.getDecoder().decode(arr[1]);
		String stringOneBase64 = new String(decodedBytesStringOne);
		
		byte[] decodedBytesStringTwo = Base64.getDecoder().decode(arr[2]);
		String stringTwoBase64 = new String(decodedBytesStringTwo);
		
		Date date = new Date(Long.parseLong(timeStampBase64));
		log.info("new Date sec - " + new Date().getSeconds());
		log.info("old Date sec - " + date.getSeconds());
		
		if((new Date().getTime() - date.getTime())>50){
			return false;
		}
		
		if(!("Hello".equals(stringOneBase64))) {
			return false;
		}
		
		if(!("Hello".equals(stringTwoBase64))) {
			return false;
		}
		return HandlerInterceptor.super.preHandle(request, response, handler);
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
	
	

}
