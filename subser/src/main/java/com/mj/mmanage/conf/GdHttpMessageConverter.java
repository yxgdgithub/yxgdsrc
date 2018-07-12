package com.mj.mmanage.conf;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
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
		vo.setContent(arg0);
		String str = new ObjectMapper().writeValueAsString(vo);
		logger.debug("返回报文："+str);
		super.writeInternal(vo, vo.getClass(), arg2);
	}

	

}
