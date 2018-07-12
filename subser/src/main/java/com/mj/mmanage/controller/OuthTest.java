package com.mj.mmanage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hthl.jwt.sdk.api.TokenMgr;
import com.hthl.jwt.sdk.config.Constant;
import com.hthl.jwt.sdk.model.SubjectModel;
import com.hthl.jwt.sdk.utils.UUIDGenerator;
import com.mj.mmanage.service.UserInfoService;
import com.mj.mmanage.util.Constants;

@Controller
public class OuthTest {
	
	private static Logger logger = Logger.getLogger(OuthTest.class);

	@Autowired
	private UserInfoService userInfoService;
	
	private static Map<String, String> tmpMap = new HashMap<String, String>();
	private int intcount = 0;
	
	// 替换字符串
	public static String getCode(String APPID, String REDIRECT_URI, String SCOPE) {
		return String.format(Constants.Get_Code, APPID, REDIRECT_URI, SCOPE);
	}

	// 替换字符串
	public static String getWebAccess(String APPID, String SECRET, String CODE) {
		return String.format(Constants.Web_access_tokenhttps, APPID, SECRET, CODE);
	}

	// 替换字符串
	public static String getUserMessage(String access_token, String openid) {
		return String.format(Constants.User_Message, access_token, openid);
	}

	@RequestMapping(value = "/wxcontenttest")
	public void wxcontent(RedirectAttributes attributes, HttpServletResponse servletResponse) {

		SubjectModel sub = new SubjectModel("oC98LuC7T2U_B4Juy6HBO17kZfaE", "李海龙");//用户信息
		String token = TokenMgr.createJWT(UUIDGenerator.getUUID(),Constant.JWT_ISS,TokenMgr.generalSubject(sub), Constant.JWT_TTL);
		logger.info("获取token成功,token->" + token);
		try {
			attributes.addFlashAttribute("token", token);
			servletResponse.sendRedirect(Constants.REDIRECT_URI + "/index.html?token="+token);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
			
	}
}