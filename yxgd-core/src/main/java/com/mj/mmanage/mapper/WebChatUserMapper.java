package com.mj.mmanage.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import com.mj.mmanage.model.WebChatUser;

public interface WebChatUserMapper extends Mapper<WebChatUser>, MySqlMapper<WebChatUser> {
	
}
