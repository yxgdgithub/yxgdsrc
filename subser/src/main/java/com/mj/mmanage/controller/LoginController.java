package com.mj.mmanage.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.mj.mmanage.core.ApiParamCk;
import com.mj.mmanage.model.UserInfo;
import com.mj.mmanage.service.UserInfoService;

/**
 * 用户登录
 * @author 郭保利
 * @since 2017-09-25
 */
@RestController
@RequestMapping("/login")
@SessionAttributes
public class LoginController {

	@Autowired
	private UserInfoService userInfoService;
	
	@RequestMapping
	public ModelAndView login(String username,@ApiParamCk(desc="密码",length=10) String password
			,HttpSession session) {
		UserInfo userInfo = userInfoService.getUserInfo(username, password);
		ModelAndView result = null;
		if(null == userInfo){
			result = new ModelAndView("login");
			result.addObject("errorMsg", "用户名或密码错误，请重新登录");
		}else{
			session.setAttribute("userInfo", userInfo);
			result = new ModelAndView("index");
			result.addObject("userInfo", userInfo);
		}
		return result;
	}
	
	@RequestMapping(value = "/toLogin")
	public ModelAndView toLogin(HttpSession session){
		session.removeAttribute("userInfo");
		ModelAndView result = new ModelAndView("login");
		return result;
	}
}
