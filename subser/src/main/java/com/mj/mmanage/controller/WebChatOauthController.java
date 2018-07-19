package com.mj.mmanage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hthl.jwt.sdk.api.TokenMgr;
import com.hthl.jwt.sdk.config.Constant;
import com.hthl.jwt.sdk.model.SubjectModel;
import com.hthl.jwt.sdk.utils.UUIDGenerator;
import com.mj.mmanage.model.WebChatUser;
import com.mj.mmanage.service.UserInfoService;
import com.mj.mmanage.util.Constants;
import com.mj.mmanage.util.StringUtil;
import com.mj.webchat.share.HttpsUtil;

@Controller
public class WebChatOauthController {
	
	private static Logger logger = Logger.getLogger(WebChatOauthController.class);

	@Autowired
	private UserInfoService userInfoService;
	
	private static Map<String, String> tmpMap = new HashMap<String, String>();
	private static Map<String, String> tmpCodeUserTokenMap = new HashMap<String, String>();
	private int intcount = 0;
	
	// 替换字符串
	public static String getOauth2Code(String APPID, String REDIRECT_URI, String SCOPE) {
		return String.format(Constants.Get_Oauth2_Code, APPID, REDIRECT_URI, SCOPE);
	}

	// 替换字符串
	public static String getOauth2WebAccess(String APPID, String SECRET, String CODE) {
		return String.format(Constants.Web_oauth2_access_tokenhttps, APPID, SECRET, CODE);
	}

	// 替换字符串
	public static String getUserMessage(String access_token, String openid) {
		return String.format(Constants.User_Message, access_token, openid);
	}

	@RequestMapping(value = "/wxcontent")
	public void wxcontent(@RequestParam(name = "code", required = false) String code,
			              @RequestParam(name = "state") String state, 
			              RedirectAttributes attributes, 
			              HttpServletResponse servletResponse) {

		logger.info("收到请求，请求数据为：code-->"+code + ";state-->"+state);
		
		intcount++;
		logger.info("intcount-->"+intcount);
		
		String userId = tmpMap.get(code);
		if (!StringUtil.isEmpty(userId)) {
			try {
				logger.info("直接转向》》》》》》》》》");
				String userToken = tmpCodeUserTokenMap.get(code);
				servletResponse.sendRedirect(Constants.REDIRECT_URI + "/index.html?userToken=" + userToken);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		else {
			try {
				// 通过code换取网页授权web_access_token
				if (!StringUtil.isEmpty(code)) {

					String APPID = Constants.APPID;
					String SECRET = Constants.SECRET;
					String CODE = code;
					String oauth2WebAccessToken = "";
					String openId = "";
					String nickName, sex, openid = "";
					String REDIRECT_URI = Constants.REDIRECT_URI;
					String SCOPE = Constants.WEBCHAT_GRANT_SCOPE;

					String getOauth2CodeUrl = WebChatOauthController.getOauth2Code(APPID, REDIRECT_URI, SCOPE);
					logger.info("getOauth2CodeUrl-->"+getOauth2CodeUrl);
					
					// 替换字符串，获得请求URL
					String oauth2WebAccessTokenURL = WebChatOauthController.getOauth2WebAccess(APPID, SECRET, CODE);
					logger.info("oauth2WebAccessTokenURL为：-->"+oauth2WebAccessTokenURL);
					
					// 通过https方式请求获得web_access_token
					String oauth2WebAccessTokenURLResponse = HttpsUtil.httpsRequestToString(oauth2WebAccessTokenURL, "GET", null);
					JSONObject jsonObject = JSON.parseObject(oauth2WebAccessTokenURLResponse);
					logger.info("jsonObject -->"+jsonObject);
					if (null != jsonObject) {
						try {
							oauth2WebAccessToken = jsonObject.getString("access_token");
							openId = jsonObject.getString("openid");
							logger.info("获取oauth2access_token成功-oauth2WebAccessToken->"+oauth2WebAccessToken+ " ;openId->" + openId);
							
							// -----------------------拉取用户信息...替换字符串，获得请求URL
							String userMessageURL = WebChatOauthController.getUserMessage(oauth2WebAccessToken, openId);
							logger.info("userMessageURL -->"+userMessageURL);
							// 通过https方式请求获得用户信息响应
							String userMessageResponse = HttpsUtil.httpsRequestToString(userMessageURL, "GET", null);

							JSONObject userMessageJsonObject = JSON.parseObject(userMessageResponse);
							logger.info("userMessageJsonObject -->"+userMessageJsonObject);
							
							if (userMessageJsonObject != null) {
								try {
									// 用户昵称
									nickName = userMessageJsonObject.getString("nickname");
									// 用户性别
									sex = userMessageJsonObject.getString("sex");
									// sex = (sex.equals("1")) ? "男":"女";
									// 用户唯一标识
									openid = userMessageJsonObject.getString("openid");
									logger.info("用户昵称-------->"+nickName);
									logger.info("用户性别-------->"+sex);
									logger.info("用户的唯一标识 -->"+openid);
								
									// lhl 20180411 add 保存微信用户信息 begin
									userInfoService.deleteWebChatUserById(openId);
									WebChatUser webChatUser = new WebChatUser();
									webChatUser.setWxUserId(openid);
									webChatUser.setNickName(nickName);
									
									// 1男2女0未知
									webChatUser.setSex(sex);
									webChatUser.setHeadUrl(userMessageJsonObject.getString("headimgurl"));
									userInfoService.saveWebChatUser(webChatUser);
									// lhl 20180411 add 保存微信用户信息 end
									
									SubjectModel sub = new SubjectModel(openid, nickName);//用户信息
									
									String userToken = TokenMgr.createJWT(UUIDGenerator.getUUID(),Constant.JWT_ISS,TokenMgr.generalSubject(sub), Constant.JWT_TTL);
									attributes.addFlashAttribute("userToken", userToken);
									logger.info("获取token成功,userToken->" + userToken);
									logger.info("页面转向地址->" + Constants.REDIRECT_URI + "/index.html?userToken=" + userToken);
									
									tmpMap.put(code, openid);
									tmpCodeUserTokenMap.put(code, userToken);
									
									servletResponse.sendRedirect(Constants.REDIRECT_URI + "/index.html?userToken=" + userToken);
								} catch (Exception e) {
									logger.info("获取用户信息失败");
									e.printStackTrace();
								}
							}
						} catch (Exception e) {
							oauth2WebAccessToken = "";// 获取code失败
							logger.error("获取WebAccessToken失败");
							e.printStackTrace();
						}
					}
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}