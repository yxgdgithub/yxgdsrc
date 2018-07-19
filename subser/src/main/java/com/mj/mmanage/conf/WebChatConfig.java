package com.mj.mmanage.conf;

import java.io.IOException;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mj.webchat.util.AccessTokenUtil;

@Configuration
@AutoConfigureAfter(MyBatisConfig.class)
public class WebChatConfig {
	
	@Bean
	public String init() throws Exception {
		AccessTokenUtil.getWebChatAccessToken();
		
		try {
			AccessTokenUtil.getJSAPITicket();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}

}
