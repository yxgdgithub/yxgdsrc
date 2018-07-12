package com.mj.mmanage.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mj.mmanage.core.ApiLoginAuth;
import com.mj.mmanage.core.ApiLoginAuth.LoginAuth;
import com.mj.mmanage.core.AppException;
import com.mj.mmanage.model.UserInfo;
import com.mj.mmanage.util.Constants;
import com.mj.mmanage.util.JsonUtil;
import com.mj.mmanage.util.RetMessageVo;

/**
 * 登陆拦截器
 * @author 郭保利
 *
 */
public class LoginCheckHandler extends HandlerInterceptorAdapter{
	private static Logger logger = Logger.getLogger(LoginCheckHandler.class);
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,
			Object obj) throws Exception {
		String currentUrl = req.getServletPath();
		MDC.put("currentUrl",currentUrl);
		if(obj instanceof HandlerMethod){
			HandlerMethod method = (HandlerMethod)obj;
			ApiLoginAuth auth = method.getMethodAnnotation(ApiLoginAuth.class);
			//不需要登陆，放行
//			if(null==auth || LoginAuth.NO.equals(auth.value())){
//				return true;
//			}
			//需要登陆，校验session中的值
			HttpSession session = req.getSession();
			UserInfo userInfo = (UserInfo) session.getAttribute(Constants.SESSION_USER);
			
//			String token = req.getParameter("token");
			if(null==userInfo ){
				throw new AppException("IMBK006");
			}
			//已经登陆，继续交易   
			return true;
		}else{
			return true;
		}
//		UserInfo userInfo = new UserInfo();
//		userInfo.setId(1001+"");
//		HttpSession session = req.getSession();
//		 session.setAttribute(Constants.SESSION_USER,userInfo);
//		 session.setAttribute(Constants.SESSION_USERID,userInfo.getId());
//		return true;
		
	}
	
	/**
     * 重定向
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

       
        super.postHandle(request, response, handler, modelAndView);
    }
	
	   /**
     * 方法结束时做的操作
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        //获取请求URL
        HandlerMethod method = (HandlerMethod)handler;
        if(method.isVoid()){
        	RetMessageVo vo = new RetMessageVo();
        	response.getWriter().print(JsonUtil.voToJson(vo));
        }
    	
        super.afterCompletion(request, response, handler, ex);
    }
}
