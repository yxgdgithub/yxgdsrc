package com.mj.mmanage.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mj.mmanage.util.Constants;

/**
 * 微信初始验证
 * @author lhl_fighting
 *
 */
@Controller
public class WebChatVerifyController {
	
	private static Logger logger = Logger.getLogger(GdPlanController.class);

	private String TOKEN = Constants.WEBCHAT_TOKEN;

	@ResponseBody
	@RequestMapping(value = "/wxverify", method = RequestMethod.GET)
	public String wxverify(@RequestParam("signature") String signature, 
						   @RequestParam("timestamp") String timestamp,
						   @RequestParam("nonce") String nonce, 
						   @RequestParam("echostr") String echostr) {

		// 排序
		String sortString = sort(TOKEN, timestamp, nonce);
		
		// 加密
		String myString = sha1(sortString);
		
		// 校验
		if (myString != null && myString != "" && myString.equals(signature)) {
			
			logger.info("签名校验通过");
			// 如果检验成功原样返回echostr，微信服务器接收到此输出，才会确认检验完成。
			return echostr;
		} 
		else {
			logger.info("签名校验失败");
			return "";
		}
	}

	public String sort(String token, String timestamp, String nonce) {
		String[] strArray = { token, timestamp, nonce };
		Arrays.sort(strArray);
		StringBuilder sb = new StringBuilder();
		for (String str : strArray) {
			sb.append(str);
		}
		return sb.toString();
	}

	public String sha1(String str) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(str.getBytes());
			byte messageDigest[] = digest.digest();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}