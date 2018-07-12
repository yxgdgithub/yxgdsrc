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

package com.mj.mmanage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.mj.mmanage.core.AppException;
import com.mj.mmanage.mapper.UserInfoMapper;
import com.mj.mmanage.mapper.WebChatUserMapper;
import com.mj.mmanage.model.UserInfo;
import com.mj.mmanage.model.WebChatUser;

/**
 * @author 郭保利
 * @since 2017-09-25
 */
@Service
public class UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    
    @Autowired
    private WebChatUserMapper webChatUserMapper;

    public List<UserInfo> getAll(UserInfo UserInfo) {
        if (UserInfo.getPage() != null && UserInfo.getRows() != null) {
            PageHelper.startPage(UserInfo.getPage(), UserInfo.getRows(), "id");
        }
        return userInfoMapper.selectAll();
    }
    /**
     * 根据用户名密码查询用户
     * @param phoneOrEmail
     * @param password
     * @return
     * @throws AppException 
     */
    public UserInfo getUserInfo(String phoneOrEmail,String password){
		UserInfo userInfo = userInfoMapper.getUserInfo(phoneOrEmail, password);
		return userInfo;
    }

    public UserInfo getById(Integer id) throws AppException {
    	throw new AppException("000");
        //return userInfoMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        userInfoMapper.deleteByPrimaryKey(id);
    }

    public void save(UserInfo user) {
        if (user.getId() != null) {
            userInfoMapper.updateByPrimaryKey(user);
        } else {
            userInfoMapper.insert(user);
        }
    }
    
    public void deleteWebChatUserById(String wxUserId) {
    	webChatUserMapper.deleteByPrimaryKey(wxUserId);
    }
    
    public void saveWebChatUser(WebChatUser webChatUser) {
        webChatUserMapper.insert(webChatUser);
    }
}
