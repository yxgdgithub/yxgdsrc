package com.mj.mmanage.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mj.mmanage.core.AppException;
import com.mj.mmanage.core.BaseController;
import com.mj.mmanage.model.UserInfo;
import com.mj.mmanage.service.UserInfoService;
import com.mj.mmanage.util.ConstantErr;
import com.mj.mmanage.util.Constants;
import com.mj.mmanage.util.UtilEncrypt;

/**
 * 用户登录
 * @author 郭保利
 * @since 2017-09-25
 */
@RestController
@RequestMapping("/login")
@SessionAttributes
public class LoginController extends BaseController{

	@Autowired
	private UserInfoService userInfoService;
	
	
	/**
	 * 用户登录
	 *@param username 用户名
	 *@param password 密码
	 *@param session 
	 *@return 用户信息
	 */
    @RequestMapping(value = "")
    public UserInfo login(@RequestParam("username") String username ,@RequestParam("password") String password,HttpSession session) throws AppException{
        //非空校验
    	if(username.isEmpty() || password.isEmpty()){
    		//用户名或密码不能为空
    		throw new AppException("IMBK003");
    	}
    	//md5加密
    	password = UtilEncrypt.md5Encrypt(password, Constants.CODE_TYPE);
        UserInfo userInfo = userInfoService.getUserInfo(username, password);
        if(null == userInfo){
        	//用户名或密码错误
        	throw new AppException("IMBK004");
        }
        //信息存入session
        session.setAttribute(Constants.SESSION_USER,userInfo);
		session.setAttribute(Constants.SESSION_USERID,userInfo.getId());
		userInfo.setPassword("");
        return userInfo;
    }
    
    /**
     * 修改密码
     *@param summary 图书说明
     *@param bootId  图书编号
     *@return 公共返回信息
     *@throw:
     */
    @RequestMapping(value = "/mofifyPwd")
    public String mofifyPwd(@RequestParam("oldPassword") String oldPassword,@RequestParam("password") String password,HttpSession session ) throws AppException{
    	String userId = (String)session.getAttribute(Constants.SESSION_USERID);
    	oldPassword = UtilEncrypt.md5Encrypt(oldPassword, Constants.CODE_TYPE);
    	UserInfo userInfo = userInfoService.getUserInfoById(userId, oldPassword);
	    if(null == userInfo){
	    	//原始密码输入错误 	
	    	throw new AppException("IMBK005");
        }else{
        	userInfoService.updatePassword(userId, UtilEncrypt.md5Encrypt(password, Constants.CODE_TYPE));
        }
	    //修改密码
        return SUCCESS;
    }
	
    /**
     * 退出登录
     *@param session
     *@return
     */
	@RequestMapping(value = "/toLogin")
	public String toLogin(HttpSession session){
		Enumeration enumeration = session.getAttributeNames();
		while (enumeration.hasMoreElements()) {
			String s = (String) enumeration.nextElement();
			session.removeAttribute(s);
		}
		 
		return SUCCESS;
	}
}
