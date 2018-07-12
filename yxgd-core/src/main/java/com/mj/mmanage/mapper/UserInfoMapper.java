package com.mj.mmanage.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.mj.mmanage.model.UserInfo;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 用户信息管理持久化mapper接口
 * @author 郭保利
 * @since 2017-09-22 
 */
public interface UserInfoMapper extends Mapper<UserInfo>, MySqlMapper<UserInfo> {
	
	/**
	 * 根据手机号或者邮箱登陆
	 * @param phoneOrEmail
	 * @param password
	 * @return
	 */
	@Select("select * from t_user_info where password=#{password}")
	public UserInfo getUserInfo(@Param("phoneOrEmail") String phoneOrEmail,@Param("password") String password);
}
