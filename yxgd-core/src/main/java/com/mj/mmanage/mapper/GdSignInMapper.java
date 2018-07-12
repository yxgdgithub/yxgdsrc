package com.mj.mmanage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.mj.mmanage.model.GdChapterPerDayRel;
import com.mj.mmanage.model.GdPlan;
import com.mj.mmanage.model.GdSignIn;
import com.mj.mmanage.model.IsSign;
import com.mj.mmanage.model.MySign;
import com.mj.mmanage.model.SignBack;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface GdSignInMapper extends Mapper<GdSignIn>, MySqlMapper<GdSignIn>  {
	
	/**
	 * 根据微信id和上次应该签到的日期查询上次签到记录
	 * @param wxUserId
	 * @param currentDate
	 * @return
	 */
	@Select ("select "
			+ "sign.signInTime "
			+ "from t_gd_sign_in sign "
			+ "where wxUserId = #{wxUserId} and signInDate = #{date} order by signInPeriod desc limit 1 " )
	public GdSignIn getGdSignRow(@Param("wxUserId") String wxUserId, @Param("date") String date);
	/**
	 * 查询签到排名
	 * @param wxUserId
	 * @param currentDate
	 * @return
	 */
	// lhl 20180409 调整sql逻辑，查询返回的字段保持不变
	// @Select("SELECT (select count(1) from t_gd_sign_in where gdid =#{gdId} and wxUserId=#{wxUserId} ) as totalSign, (select count(1) AS rownum from t_gd_sign_in where signInDate=#{signInDate})as rownum,tp.gdBeginDate,ti.gdId, tb.bookName,ti.wxUserId, tb.bookSltPath,ti.signIntime, ta.signContinuousDays FROM t_gd_sign_in ti  LEFT JOIN t_gd_book tb ON ti.gdId= tb.bookId LEFT JOIN t_gd_apply ta  ON ti.gdId= ta.gdId LEFT JOIN  T_GD_PLAN TP ON ti.gdId= tp.gdId  WHERE  ti.gdId=#{gdId} AND ti.signInDate=#{signInDate} and  ti.wxUserId=#{wxUserId}")
	@Select (
			" SELECT "
			+ " (select count(1) from t_gd_sign_in where gdid =#{gdId} and wxUserId=#{wxUserId} ) as totalSign,  "
			+ "(select count(1) AS rownum from t_gd_sign_in where signInDate=#{signInDate} and gdid =#{gdId} )as rownum, "
			+ "tp.gdBeginDate,ti.gdId, tb.bookName,ti.wxUserId, tb.bookSltPath,ti.signIntime, ta.signContinuousDays,"
			+ "user.nickName as nikeName, user.headUrl as headUrl "
			+ "FROM t_gd_sign_in ti          "
			+ "left join t_gd_chapter_per_day_rel  dr on ti.gdId = dr.gdId and ti.signInDate = dr.gdDate and dr.gdPeriod = #{gdPeriod}  "
			+ "LEFT JOIN t_gd_book tb ON dr.bookId = tb.bookId "
			+ "LEFT JOIN t_gd_apply ta  ON ti.gdId= ta.gdId and ta.wxUserId = ti.wxUserId "
			+ "LEFT JOIN  T_GD_PLAN TP ON ti.gdId= tp.gdId   "
			+ "left join t_webchat_user user on user.wxUserId = ti.wxUserId "
			+ "WHERE ti.gdId=#{gdId} AND ti.signInDate=#{signInDate} and  ti.wxUserId=#{wxUserId} and  ti.signInPeriod=#{gdPeriod} " )
	public SignBack getGdSignRanks(@Param("gdId") Integer gdId,
			@Param("wxUserId") String wxUserId,
			@Param("signInDate") String signInDate,
			@Param("gdPeriod") String gdPeriod);
	/**
	 * 我的签到
	 * @param wxUserId
	 * @param currentDate
	 * @return
	 */
	// lhl 20180410调整业务逻辑
	//@Select("SELECT (select count(1) from t_gd_sign_in where gdid =#{gdId} and wxUserId=#{wxUserId}) as totalSign, (select count(1) AS rownum from t_gd_sign_in )as rownum,tp.gdBeginDate,ti.gdId,ti.wxUserId, tp.gdTitle,tp.gdSlogan, ta.signContinuousDays FROM t_gd_sign_in ti  LEFT JOIN t_gd_book tb ON ti.gdId= tb.bookId LEFT JOIN t_gd_apply ta  ON ti.gdId= ta.gdId LEFT JOIN  T_GD_PLAN TP ON ti.gdId= tp.gdId  WHERE  ti.gdId=#{gdId} AND ti.signInDate=#{signInDate} and  ti.wxUserId=#{wxUserId}")
	
	@Select ("select  " +
			"plan.gdId as gdId," +
			"plan.gdTitle as gdTitle, " +
			"plan.signupBeginDate as signupBeginDate, " +
			"plan.signupEndDate as signupEndDate, " +
			"plan.gdBeginDate as gdBeginDate,  " +
			"plan.gdEndDate as gdEndDate, " + 
			"plan.gdState as gdState, " + 
			"plan.sponsorUser as sponsorUser, " +
			"plan.gdSlogan  as gdSlogan, " +
			"apply.signContinuousDays as signContinuousDays , " +
			"apply.signNum as signNum, " +
			"format(apply.signNum/(cast(#{signInDate} as signed)-cast(plan.gdBeginDate as signed) + 1) * 100,2) as signRate, " +
			"user.nickName, " +
			"user.headUrl " +
			"from t_webchat_user user  " +
			" left join t_gd_apply apply on user.wxUserid = apply.wxUserid and apply.wxUserId = #{wxUserId} " +
			" inner join t_gd_plan plan on apply.gdid = plan.gdid and plan.gdBeginDate <= #{signInDate} " + 
			" where user.wxUserid = #{wxUserId}  " + 
			" order by apply.applyTime desc limit 1  ") 
	public MySign mySign(@Param("wxUserId") String wxUserId, @Param("signInDate") String signInDate);

	
	/**
	 * 我的签到返回日期
	 * @param wxUserId
	 * @param currentDate
	 * @return
	 */
	@Select("select distinct signInDate from t_gd_sign_in where signInDate>=#{firstDay} and signInDate <=#{lastDay} and wxUserId=#{wxUserId} and gdId=#{gdId} ")
	public List<String> signDate(
			@Param("gdId") Integer gdId,
			@Param("firstDay") String firstDay,
			@Param("lastDay") String lastDay,
			@Param("wxUserId") String wxUserId);

	/**
	 * 查询是否签约过
	 * @param wxUserId
	 * @param currentDate
	 * @return
	 */
	@Select("select " +
			"count(1) as signFlag " +
			"from t_gd_sign_in ti " +
			"where gdId = #{gdId} and wxUserId = #{wxUserId} and signInDate = #{signInDate}")
	public String isGdSignin(@Param("gdId") Integer gdId,
			@Param("wxUserId") String wxUserId,
			@Param("signInDate") String signInDate);
	
}
