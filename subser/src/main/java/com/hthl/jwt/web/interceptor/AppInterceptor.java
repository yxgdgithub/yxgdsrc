package com.hthl.jwt.web.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hthl.jwt.sdk.api.ResponseMgr;
import com.hthl.jwt.sdk.api.TokenMgr;
import com.hthl.jwt.sdk.config.Constant;
import com.hthl.jwt.sdk.model.CheckResult;
import com.hthl.jwt.sdk.model.SubjectModel;
import com.hthl.jwt.sdk.utils.GsonUtil;

import io.jsonwebtoken.Claims;

/**
 * @author 许喜乐
 * */
public class AppInterceptor implements HandlerInterceptor{
	
	private static final Logger logger = Logger.getLogger(AppInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		logger.info("afterCompletion execute");
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		logger.info("postHandle execute");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler)  throws Exception{
		response.setContentType("text/html;charset=UTF-8"); 
		//请求URL
		String url =request.getServletPath().toString();
		logger.info("url->" + url);	
		//如果是登录则不拦截开始
		if(url.contains("/demo/login") || url.contains("/wxtest")){
			return true;
		}
		String tokenStr = request.getParameter("token");
		logger.info("tokenStr->"+ tokenStr);
		if (tokenStr == null || tokenStr.equals("")) {
			PrintWriter printWriter = response.getWriter();
			printWriter.print(ResponseMgr.noLogin());
			printWriter.flush();
			printWriter.close();
			return false;
		}
		
		// 验证JWT的签名，返回CheckResult对象
		CheckResult checkResult = TokenMgr.validateJWT(tokenStr);
		if (checkResult.isSuccess()) {
			Claims claims = checkResult.getClaims();
			logger.info("token校检通过checkResult："+GsonUtil.objectToJsonStr(checkResult));
			SubjectModel user = GsonUtil.jsonStrToObject(claims.getSubject(), SubjectModel.class);
			logger.info("token校检通过user："+GsonUtil.objectToJsonStr(user));
			return true;
		} else {
			switch (checkResult.getErrCode()) {
			// 签名过期，返回过期提示码
			case Constant.JWT_ERRCODE_EXPIRE:
				PrintWriter printWriter = response.getWriter();
				printWriter.print(ResponseMgr.loginExpire());
				printWriter.flush();
				printWriter.close();
				break;
			// 签名验证不通过
			case Constant.JWT_ERRCODE_FAIL:
				PrintWriter printWriter2 = response.getWriter();
				printWriter2.print(ResponseMgr.noAuth());
				printWriter2.flush();
				printWriter2.close();
				break;
			default:
				break;
			}
			return false;
		}
	}

}
