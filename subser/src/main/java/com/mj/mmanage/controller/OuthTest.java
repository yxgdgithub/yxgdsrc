package com.mj.mmanage.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hthl.jwt.sdk.api.TokenMgr;
import com.hthl.jwt.sdk.config.Constant;
import com.hthl.jwt.sdk.model.SubjectModel;
import com.hthl.jwt.sdk.utils.UUIDGenerator;

@Controller
public class OuthTest {
	
	private static Logger logger = Logger.getLogger(OuthTest.class);

	@RequestMapping(value = "/wxcontenttest")
	public void wxcontent(RedirectAttributes attributes, HttpServletResponse servletResponse) {

		SubjectModel sub = new SubjectModel("oC98LuC7T2U_B4Juy6HBO17kZfaE", "李海龙");//用户信息
		String userToken = TokenMgr.createJWT(UUIDGenerator.getUUID(),Constant.JWT_ISS,TokenMgr.generalSubject(sub), Constant.JWT_TTL);
		logger.info("获取token成功,userToken->" + userToken);
		try {
			attributes.addFlashAttribute("userToken", userToken);
			servletResponse.sendRedirect("http://127.0.0.1" + "/index.html?userToken="+userToken);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
			
	}
}