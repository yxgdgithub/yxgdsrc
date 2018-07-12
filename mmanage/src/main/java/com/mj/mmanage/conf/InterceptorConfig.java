package com.mj.mmanage.conf;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mj.mmanage.handler.ApiFieldsCheckHandler;
import com.mj.mmanage.handler.ApiParamCheckHandler;
import com.mj.mmanage.handler.LogInterceptor;
import com.mj.mmanage.handler.LoginCheckHandler;
import com.mj.mmanage.handler.ReturnHandler;
/**
 * 拦截器
 * @author 郭保利
 *
 */
@Configuration
public class InterceptorConfig  extends WebMvcConfigurerAdapter{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		//registry.addInterceptor(new Log4jHandler()).addPathPatterns("/**");
		
		//登陆校验
	InterceptorRegistration interceptor = registry.addInterceptor(new LoginCheckHandler());
		
	/*	interceptor.excludePathPatterns("/error");*/
		interceptor.excludePathPatterns("/login**");
		interceptor.excludePathPatterns("/toLoin");
		interceptor.addPathPatterns("/**");
		//字段校验
		registry.addInterceptor(new ApiFieldsCheckHandler()).addPathPatterns("/**");
		registry.addInterceptor(new ApiParamCheckHandler()).addPathPatterns("/**");
		super.addInterceptors(registry);
		
		//请求日志拦截器
		InterceptorRegistration interceptorLog = registry.addInterceptor(new LogInterceptor());
		 
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addReturnValueHandlers(java.util.List)
	 */
	@Override
	public void addReturnValueHandlers(
			List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		ReturnHandler returnHandler  = new ReturnHandler();
		returnValueHandlers.add(returnHandler);
		// TODO Auto-generated method stub
		super.addReturnValueHandlers(returnValueHandlers);
	}
	
	
}
