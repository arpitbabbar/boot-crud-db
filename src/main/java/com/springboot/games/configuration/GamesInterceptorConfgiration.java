package com.springboot.games.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.springboot.games.interceptor.GamesInterceptor;

@Component
public class GamesInterceptorConfgiration extends WebMvcConfigurationSupport {

	@Autowired
	GamesInterceptor gamesInterceptor;
	
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(gamesInterceptor);
	}
	
	

}
