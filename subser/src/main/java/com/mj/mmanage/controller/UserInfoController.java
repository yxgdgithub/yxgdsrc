/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.mj.mmanage.controller;

import com.github.pagehelper.PageInfo;
import com.mj.mmanage.core.ApiLoginAuth;
import com.mj.mmanage.core.ApiParamCk;
import com.mj.mmanage.core.AppException;
import com.mj.mmanage.core.ApiLoginAuth.LoginAuth;
import com.mj.mmanage.model.UserInfo;
import com.mj.mmanage.service.UserInfoService;
import com.mj.mmanage.util.RetMessageVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 用户信息管理
 * @author 郭保利
 * @since 2017-9-22
 */
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiLoginAuth(LoginAuth.NO)
    @ResponseBody
    @RequestMapping
    public PageInfo getAll(UserInfo userInfo) {
        List<UserInfo> userInfoList = userInfoService.getAll(userInfo);
        PageInfo<UserInfo> pageInfo = new PageInfo<UserInfo>(userInfoList);
        return pageInfo;
    }

    @ResponseBody
    @RequestMapping(value = "/view/{id}")
    public UserInfo view(@ApiParamCk(desc = "ID",length=2) @PathVariable Integer id) throws AppException {
        UserInfo userInfo = userInfoService.getById(id);
        return userInfo;
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{id}")
    public ModelMap delete(@PathVariable Integer id) {
        ModelMap result = new ModelMap();
        userInfoService.deleteById(id);
        result.put("msg", "删除成功!");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(UserInfo userInfo,String phoneOrEmail) {
    	ModelAndView result = new ModelAndView("index");
        String msg = userInfo.getId() == null ? "新增成功!" : "更新成功!";
        
        if(phoneOrEmail.matches("\\d{11}")){//手机号
        	//userInfo.setPhone(phoneOrEmail);
        }else{//邮箱
        	userInfo.setEmail(phoneOrEmail);
        }
        
        userInfoService.save(userInfo);
        
        result.addObject("userInfo", userInfo);
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/token")
    public  String  getToken() {
    	 
        return "sdfffffffffffffffffs";
    }
    
    
}
