package com.mj.mmanage.core;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;

import com.mj.mmanage.core.ApiFieldsCheck.BooleanEnum;
import com.mj.mmanage.core.ApiFieldsCheck.FieldTypeEnum;
import com.mj.mmanage.util.StringUtil;

public class ApiParamCheckUtils {
	
	public static boolean paramCheckUtil(HttpServletRequest req,HandlerMethod method) throws AppException {
		
		MethodParameter[] parameters = method.getMethodParameters();
		
		for(MethodParameter parameter : parameters){
			if(parameter.getParameterAnnotation(ApiParamCk.class) != null){
				
				//获取字段的校验规则
				ApiParamCk api = parameter.getParameterAnnotation(ApiParamCk.class);
				
				String name = parameter.getParameterName();
				if(!StringUtils.isEmpty(name)){
					
					String value = req.getParameter(name);
					
					String desc = api.desc();
					
					//是否必输
					BooleanEnum isNull = api.isNull();
					if(BooleanEnum.Yes.equals(isNull) && StringUtil.isEmpty(value)){
						throw new AppException("101", desc + "字段为必输项");
					}
					//字段长度
					int length = api.length();
					if(length!=-1 && !StringUtil.isEmpty(value) && value.toString().length() > length){
						throw new AppException("102", desc + "字段长度最大为"+length);
					}
					
					//正则表达式
					String matches = api.matches();
					if(!StringUtil.isEmpty(matches) && !StringUtil.isEmpty(value) &&
							!value.toString().matches(matches)){
						throw new AppException("103", desc + "字段格式不符合");
					}
					
					//字段类型
					FieldTypeEnum type = api.type();
				}
				
			}
		}
		
		return true;
	}
}
