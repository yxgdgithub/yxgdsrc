package com.mj.mmanage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import com.mj.mmanage.model.GdApply;

public interface GdApplyMapper extends Mapper<GdApply>, MySqlMapper<GdApply>  {
	
	
	/**
	 * @param gdId 共读编号
	 * @param signInDate 签到日期
	 * @return 取得签到日期未签到的微信用户ID
	 */
	@Select(" select distinct apply.wxUserId from t_gd_apply apply "
			+ " where apply.wxUserId not in (select wxUserId from t_gd_sign_in where signInDate = #{signInDate} and gdId = #{gdId} ) "
			+ " and apply.gdId = #{gdId} ")
	public List<GdApply> findGdApplyWxUserId(@Param("gdId") int gdId, @Param("signInDate") String signInDate);

}
