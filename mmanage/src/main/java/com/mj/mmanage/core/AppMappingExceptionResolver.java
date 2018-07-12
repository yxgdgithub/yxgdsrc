package com.mj.mmanage.core;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mj.mmanage.util.ConstantErr;
import com.mj.mmanage.util.RetMessageVo;
import com.mj.mmanage.util.StringUtil;


/**
 * 重写异常处理
 * @author guobaoli
 * @date 2017年3月5日
 */
@Component
public class AppMappingExceptionResolver extends SimpleMappingExceptionResolver{

	private static Logger logger = Logger.getLogger(AppMappingExceptionResolver.class);
	
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
				ex = new AppException("-999") ;
			}
			
			return getModelAndView(viewName, ex, request);
		}
		return null;
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		if(handler instanceof HandlerMethod){
			HandlerMethod method = (HandlerMethod) handler;
			//不知道以下代码何意？
//			ResponseBody annotation = method.getMethodAnnotation(ResponseBody.class);
//			if(null == annotation){
//				return super.resolveException(request, response, handler, ex);
//			}
			
			
			//json格式返回
			RetMessageVo vo = new RetMessageVo();
			
			
			response.setStatus(HttpStatus.OK.value());
			//设置ContentType
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			//避免乱码
			response.setCharacterEncoding("UTF-8");
			
			response.setHeader("Cache-Control", "no-cache,must-revalidate");
			try{
				PrintWriter writer = response.getWriter();
				
				String msg =ex.getMessage() ;
				//错误码和信息都传的情况
				if(!StringUtil.isEmpty(msg) && msg.contains("|")){
					String[] split = msg.split("\\|");
					vo.setRetCode(split[0] );
					vo.setRetMsg(split[1]);
				}else{
					//只传错误码的情况
					msg = ConstantErr.errorMap.get(ex.getMessage());
					msg = !StringUtil.isEmpty(msg) ? msg : "系统异常，请联系后台管理员！";
					vo.setRetCode(ex.getMessage());
					vo.setRetMsg(msg);
				}
				String str = new ObjectMapper().writeValueAsString(vo);
				logger.info("返回报文："+str);
				writer.write(str);
				writer.close();
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			
			return null;
		}else{
			return super.resolveException(request, response, handler, ex);
		}
		
	}
	
}
