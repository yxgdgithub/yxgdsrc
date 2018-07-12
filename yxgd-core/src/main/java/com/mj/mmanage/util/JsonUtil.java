/**
 *@Title：JsonUtil.java
 *@Package:com.citic.portal.core.util
 *@version:version V1.0
 */
package com.mj.mmanage.util;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 *@Descripion:通用Json操作
 *@author:YX-LiAnDong
 *@date:2015-12-3
 */
public class JsonUtil{
	
	/**
	 * 将vo转成json窜
	 *@param obj 待处理对象
	 *@return json格式的字符串
	 *@throws Exception
	 */
	public static String voToJson(Object obj) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		if(obj !=null){
			return mapper.writeValueAsString(obj);
		}else{
			return "";
		}
	}
	
	/**
	 * 将json转成vo
	 *@param obj 待处理对象
	 *@return json格式的字符串
	 *@throws Exception
	 */
	public static <T> T jsonToVo(String json, Class<T> t) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		if(json !=null){
			return mapper.readValue(json, t);
		}else{
			return null;
		}
	}
}
