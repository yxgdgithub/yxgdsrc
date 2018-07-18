package com.mj.mmanage.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mj.mmanage.model.WXUserInfo;
import com.mj.mmanage.util.Constants;

import org.apache.log4j.Logger;

/**
 * 登陆拦截器
 * 
 * @author 郭保利
 * 
 */

public class LoginCheckHandler extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(LoginCheckHandler.class);

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,
			Object obj) throws Exception {

		// String token = req.getParameter("token").toString();
		// 解析请求token
		String currentUrl = req.getServletPath();
		MDC.put("currentUrl", currentUrl);
		WXUserInfo wXUserInfo = new WXUserInfo();
		wXUserInfo.setId("12165465sf6d5sd");
		wXUserInfo.setUsername("guobaoli");
		MDC.put(Constants.WECHAT_ID, "12165465sf6d5sd");
		logger.info("拦截请求");
		return true;
	}

}
