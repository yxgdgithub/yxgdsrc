package com.mj.mmanage.handler;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * <p> Description: 打印日志 </p>
 * <p> Copyright: Copyright (c) 2015 </p>
 * <p> Create Date: 2016-1-3</p>
 * <p> Company: CITIC BANK </p> 
 *@author:郭保利
 *@version:version V1.0
 */
public class LogInterceptor implements HandlerInterceptor {
	
	private static Logger logger = Logger.getLogger(LogInterceptor.class);
	
	/**
	 * 在controller后拦截
	 */
	public void afterCompletion(HttpServletRequest request,
	    HttpServletResponse response, Object object, Exception exception)
	    throws Exception {
		
	}

	public void postHandle(HttpServletRequest request,
	    HttpServletResponse response, Object object, ModelAndView modelAndView)
	    throws Exception {
		/*Map<String, Object> map = modelAndView.getModel();
		String str = JSONObject.fromObject(map).toString();

		logger.info("出参：" + str);*/
	}

	/**
	 * 在controller前拦截
	 */
	public boolean preHandle(HttpServletRequest request,
	    HttpServletResponse response, Object object) throws Exception {
		Map<String,String> logmap = new HashMap<String,String>();
		Enumeration<String> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			String value = request.getParameter(key);
			logmap.put(key,value);
		}
		String str = new ObjectMapper().writeValueAsString(logmap);
		logger.info("URL："+MDC.get("currentUrl") +"  入参:" + str);
		return true;
	}
}
