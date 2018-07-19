package com.mj.webchat.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.mj.mmanage.util.Constants;
import com.mj.webchat.share.GetRandomStr;
import com.mj.webchat.share.HttpsUtil;
import com.mj.webchat.share.Ticket;

public class AccessTokenUtil {
	
	private static Logger logger = Logger.getLogger(AccessTokenUtil.class);
	
	public static String ACCESS_TOKEN = "";
	public static Ticket JSAPI_TICKET = null;
	
	public static void getWebChatAccessToken() {
		System.out.println("getWebChatAccessToken 开始执行...");
		String response = HttpsUtil.httpsRequestToString(Constants.ACCESS_TOKEN_URL, "GET", null);
        
        JSONObject jsonObject = JSON.parseObject(response);
        logger.info("WebChatAccessToken JsonObject-->" + jsonObject);
        ACCESS_TOKEN = jsonObject.getString("access_token");
        logger.info("WebChatAccessToken 获取完毕! ACCESS_TOKEN->" + ACCESS_TOKEN);
        System.out.println("getWebChatAccessToken 结束执行...");
	}
	
	public static void getJSAPITicket() throws IOException {
		System.out.println("getJSAPITicket 开始执行...");
		// 获取签名随即字符串
		GetRandomStr randomStr = new GetRandomStr();
		String noncestr = randomStr.getRandomString(15);
		// 获取签名时间戳
		String timestamp = Long.toString(System.currentTimeMillis());
		
		if (StringUtils.isEmpty(ACCESS_TOKEN)) {
			getWebChatAccessToken();
		}
		
		// 请求accessToken
		String shareAccessToken = ACCESS_TOKEN;
		logger.info("shareAccessToken-->"+shareAccessToken);
		
		if (!StringUtils.isEmpty(ACCESS_TOKEN)) {
		
			// 获取标签
			String ticketURL = String.format(Constants.TICKET_URL, shareAccessToken);
			logger.info("ticketURL-->"+ticketURL);
			String ticketJson = HttpsUtil.httpsRequestToString(ticketURL, "GET", null);
			logger.info("ticketJson-->"+ticketJson);
			
			Gson gson = new Gson();
			Ticket ticket = gson.fromJson(ticketJson, Ticket.class);
			String strTicket = ticket.getTicket();
			logger.error("JSAPI_TICKET生成成功!strTicket->" + strTicket);
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String acquiretime = format.format(new Date());
			
			ticket.setAcquiretime(acquiretime);
			ticket.setTimestamp(timestamp);
			ticket.setNoncestr(noncestr);
			
			JSAPI_TICKET = ticket;
			
		}
		else {
			logger.error("access_token获取失败，未能生成JSAPI_TICKET");
		}
		System.out.println("getJSAPITicket 结束执行...");
	}
}