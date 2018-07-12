package com.mj.mmanage.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mj.mmanage.core.ApiParamCheckUtils;

public class ApiParamCheckHandler implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse arg1,
			Object obj) throws Exception {
		
		if(obj instanceof HandlerMethod){
			HandlerMethod method = (HandlerMethod) obj;
			return ApiParamCheckUtils.paramCheckUtil(req,method);
		}else{
			return true;
		}
		
	}

}
