package com.ts.common.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class TsConfig {

	@Autowired
    private  Environment environment;
	
    private static Environment env;
	
    @PostConstruct
	public void init(){
		TsConfig.env = environment;
	}
	

	public  static String getUploadPath() {
		return env.getProperty("ts.uploadPath");
	}

	public static String getUsername() {
		return env.getProperty("ts.username", "admin");
	}

	public static String getPassword() {
		return env.getProperty("ts.username","12qwaszx");
	}
	
	public static String getProperty(String key){
		return env.getProperty(key, "");
	}
	
}
