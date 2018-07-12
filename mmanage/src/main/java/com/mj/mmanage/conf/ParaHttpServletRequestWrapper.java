/**
 *@Title：ParaHttpServletRequestWrapper.java
 *@Package:com.mj.mmanage.conf
 */
package com.mj.mmanage.conf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.mj.mmanage.util.StringUtil;

/**
 * <p> Description:add todo </p>
 * <p> Copyright: Copyright (c) 2017 </p>
 * <p> Create Date: 2018-4-18</p>
 * <p> Company: YUSYS </p> 
 *@author:YX-LiAnDong
 *@version:ParaHttpServletRequestWrapper,v1.0 YX-LiAnDong 
 */
public class ParaHttpServletRequestWrapper extends HttpServletRequestWrapper {

	/**
	 * @param request
	 */
	public ParaHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 根据request中的key获取数组类型的value
	 * @param parameter request中的key
	 * @return 处理后数组类型结果
	 */
	public String[] getParameterValues(String parameter){
		String[] values = super.getParameterValues(parameter);
		if(values==null){
			return null;
		}
		int count = values.length;
		String[] encodeValues = new String[count];
		
		
		for (int i = 0; i < count; i++) {
			if(StringUtil.isEmpty(values[i])){
				encodeValues[i] = null;
			}else{
				encodeValues[i] = values[i];
			}
			
		}
		return encodeValues;
	}
	
	/**
	 * 根据request中的key获取数组类型的value
	 * @param parameter request中的key
	 * @return 处理后数组类型结果
	 */
	public String getParameter(String parameter){
		String value = super.getParameter(parameter);
		if(value == null){
			return null;
		}
		if(StringUtil.isEmpty(value)){
			return null;
		}else{
			return value;
		}
	}
	
	/**
	 * 根据请求头中的key获取数组类型的value
	 * @param parameter request中的key
	 * @return 处理后数组类型结果
	 */
	public String getHeader(String name){
		String value = super.getParameter(name);
		if(value == null){
			return null;
		}
		if(StringUtil.isEmpty(value)){
			return null;
		}else{
			return value;
		}
	}
}
