package com.mj.mmanage.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.hthl.jwt.web.interceptor.AppInterceptor;
import com.mj.mmanage.handler.ApiFieldsCheckHandler;
import com.mj.mmanage.handler.ApiParamCheckHandler;
import com.mj.mmanage.handler.LogInterceptor;
import com.mj.mmanage.handler.LoginCheckHandler;
/**
 * 拦截器
 * @author 郭保利
 *
 */
@Configuration
public class InterceptorConfig  extends WebMvcConfigurerAdapter{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//登陆校验
	InterceptorRegistration interceptor = registry.addInterceptor(new LoginCheckHandler());
//	InterceptorRegistration interceptor = registry.addInterceptor(new AppInterceptor());
	 	
		interceptor.excludePathPatterns("/error");
		interceptor.excludePathPatterns("/login**");
		interceptor.addPathPatterns("/**"); 
		//字段校验
		registry.addInterceptor(new ApiFieldsCheckHandler()).addPathPatterns("/**");
		registry.addInterceptor(new ApiParamCheckHandler()).addPathPatterns("/**");
		//super.addInterceptors(registry);
		InterceptorRegistration interceptorLog = registry.addInterceptor(new LogInterceptor());
		//字段校验
		registry.addInterceptor(new ApiFieldsCheckHandler()).addPathPatterns("/**");
		registry.addInterceptor(new ApiParamCheckHandler()).addPathPatterns("/**");
	}
	
}
