package com.mj.mmanage.conf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * 重写异常处理
 * @author guobaoli
 * @date 2017年3月5日
 */
public class SelfMappingExceptionResolver extends SimpleMappingExceptionResolver{

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		String viewName = determineViewName(ex, request);
		if (viewName != null) {
			Integer statusCode = determineStatusCode(request, viewName);
			if (statusCode != null) {
				applyStatusCodeIfPossible(request, response, statusCode.intValue());
			}
			
			if(ex instanceof DataAccessException){//数据库异常
				ex = new ManSelfException("-999","数据库异常!") ;
			}
			
			return getModelAndView(viewName, ex, request);
		}
		return null;
	}

	
	
}
