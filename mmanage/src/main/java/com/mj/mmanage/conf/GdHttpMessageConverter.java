package com.mj.mmanage.conf;

import java.io.IOException;
import java.lang.reflect.Type;

import org.apache.log4j.Logger;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mj.mmanage.util.RetMessageVo;

@Component
public class GdHttpMessageConverter extends MappingJackson2HttpMessageConverter{

	private static Logger logger = Logger.getLogger(GdHttpMessageConverter.class);

	/**
	 * 重写 返回对象
	 */
	@Override
	protected void writeInternal(Object arg0, Type arg1, HttpOutputMessage arg2)
			throws IOException, HttpMessageNotWritableException {
		RetMessageVo vo = new RetMessageVo();
		if(arg0 instanceof RetMessageVo){
			vo = (RetMessageVo)arg0;
		}else{
			vo.setContent(arg0);
		}
		String str = new ObjectMapper().writeValueAsString(vo);
		logger.info("返回报文："+str);
		super.writeInternal(vo, vo.getClass(), arg2);
	}	

}
