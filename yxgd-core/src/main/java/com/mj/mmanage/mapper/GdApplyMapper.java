package com.mj.mmanage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import com.mj.mmanage.model.GdApply;

public interface GdApplyMapper extends Mapper<GdApply>, MySqlMapper<GdApply>  {
	
	@Select("select distinct wxUserId from t_gd_apply where gdid = #{gdId} ")
	public List<GdApply> findGdApplyWxUserId(@Param("gdId") int gdId);

}
