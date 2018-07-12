package com.mj.mmanage.handler;

import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 记录日志
 * @author 郭保利
 *
 */
public class Log4jHandler extends HandlerInterceptorAdapter{

	private static Logger logger = Logger.getLogger(Log4jHandler.class);
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.info("afterCompletion");
		
		if(handler instanceof HandlerMethod){
			HandlerMethod hm = (HandlerMethod)handler;
			Method method = hm.getMethod();
			String methodName = method.getName();
			logger.info("开始访问方法:" + hm.toString()+"."+methodName + ",出参：");
		}
		
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		logger.info("afterConcurrentHandlingStarted");
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("postHandle");
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		logger.info("preHandle");
		
		if(handler instanceof HandlerMethod){
			HandlerMethod hm = (HandlerMethod)handler;
			Method method = hm.getMethod();
			String methodName = method.getName();
			Class<? extends Method> class1 = method.getClass();
			
			Enumeration<String> parameterNames = request.getParameterNames();
			StringBuilder sb = new StringBuilder();
			if(parameterNames != null && parameterNames.hasMoreElements()){
				String element = parameterNames.nextElement();
				String string = request.getParameter(element);
				sb.append(element).append(":").append(string).append(",");
			}
			
			logger.info("开始访问方法:" + hm.toString()+"."+methodName + ",参数："+sb.toString());
		}
		
		return super.preHandle(request, response, handler);
	}

	
}
