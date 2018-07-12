package com.mj.jwt.utils;

import org.apache.log4j.Logger;

import com.hthl.jwt.sdk.api.TokenMgr;
import com.hthl.jwt.sdk.model.CheckResult;
import com.hthl.jwt.sdk.model.SubjectModel;
import com.hthl.jwt.sdk.utils.GsonUtil;

import io.jsonwebtoken.Claims;

public class CheckToken {
	
	private static Logger logger = Logger.getLogger(CheckToken.class);
	
	/**
	 * @param userToken  用户Token
	 * @return 与userToken对应的用户微信ID
	 */
	public static String getWebChatUserId(String userToken) {
		
		String webChatUserId = "";
		
		// 验证JWT的签名，返回CheckResult对象
		CheckResult checkResult = TokenMgr.validateJWT(userToken);
		
		if (checkResult.isSuccess()) {
			Claims claims = checkResult.getClaims();
			logger.info("token校检通过checkResult："+GsonUtil.objectToJsonStr(checkResult));
			SubjectModel user = GsonUtil.jsonStrToObject(claims.getSubject(), SubjectModel.class);
			logger.info("token校检通过user："+GsonUtil.objectToJsonStr(user));
			webChatUserId = user.getUserId();
		}
		else {
			logger.error("user token校检未通过");
		}
		
		return webChatUserId;
	}

}
