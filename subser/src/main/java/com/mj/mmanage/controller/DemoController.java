package com.mj.mmanage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hthl.jwt.sdk.api.TokenMgr;
import com.hthl.jwt.sdk.config.Constant;
import com.hthl.jwt.sdk.model.CommonResult;
import com.hthl.jwt.sdk.model.SubjectModel;
import com.hthl.jwt.sdk.utils.UUIDGenerator;

@RestController
@RequestMapping("/demo")
public class DemoController {
	
	@RequestMapping("/test")
	@ResponseBody
	public String test(HttpServletRequest request,HttpServletResponse response, Model model) throws Exception {
		System.out.println("122123");
		return "112";
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public CommonResult login(HttpServletRequest request,HttpServletResponse response, String userName,String password,Model model) throws Exception {
		//*****登陆逻辑开始......结束*****
		//登陆验证通过后
		SubjectModel sub = new SubjectModel("1001", "admin");//用户信息
		String token = TokenMgr.createJWT(UUIDGenerator.getUUID(),Constant.JWT_ISS,TokenMgr.generalSubject(sub), Constant.JWT_TTL);
		System.out.println("DemoController->login->token" + token);
		CommonResult commonResult = new CommonResult(Constant.RESCODE_SUCCESS, null, "成功", token);
		return commonResult;
	}
}
