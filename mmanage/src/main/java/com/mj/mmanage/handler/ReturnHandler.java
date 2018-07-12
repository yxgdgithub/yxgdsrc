package com.mj.mmanage.handler;

import org.apache.log4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.mj.mmanage.util.RetMessageVo;

/**
 * 登陆拦截器
 * @author 郭保利
 *
 */
public class ReturnHandler implements HandlerMethodReturnValueHandler{
	private static Logger logger = Logger.getLogger(ReturnHandler.class);

	/* (non-Javadoc)
	 * @see org.springframework.web.method.support.HandlerMethodReturnValueHandler#supportsReturnType(org.springframework.core.MethodParameter)
	 */
	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		// TODO Auto-generated method stub
		return returnType.getParameterType()==RetMessageVo.class;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.method.support.HandlerMethodReturnValueHandler#handleReturnValue(java.lang.Object, org.springframework.core.MethodParameter, org.springframework.web.method.support.ModelAndViewContainer, org.springframework.web.context.request.NativeWebRequest)
	 */
	@Override
	public void handleReturnValue(Object returnValue,
			MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		System.out.println("dddddddffffff");
		mavContainer.setRequestHandled(true);
		//HttpServletResponse response
		
	}
	
}
