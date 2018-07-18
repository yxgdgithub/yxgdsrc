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

package com.mj.mmanage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import com.mj.mmanage.model.GdApply;
import com.mj.mmanage.model.GdBook;
import com.mj.mmanage.model.GdPlan;

/**
 * @author lihailong
 * @since 2018-01-02
 */
public interface GdPlanMapper extends Mapper<GdPlan>, MySqlMapper<GdPlan> {
	
	/**
	 * @param wxUserID 微信用户ID
	 * @param currentDate 当前系统日期,格式YYYYMMDD
	 * @return 返回当前处于报名中的共读计划信息
	 */
	@Select ("select " + 
			"plan.gdId as gdId, " + 
			"plan.gdTitle as gdTitle, " + 
			"plan.signupBeginDate as signupBeginDate, " + 
			"plan.signupEndDate as signupEndDate, " + 
			"plan.gdBeginDate as gdBeginDate, " + 
			"plan.gdEndDate as gdEndDate, " + 
			"plan.gdState as gdState, " + 
			"plan.sponsorUser as sponsorUser, " + 
			"plan.gdSlogan  as gdSlogan, " + 
			"plan.gdInstr  as gdInstr, " +
			"apply.wxUserId as wxUserId, " + 
			"applyNum.applyNum as applyNum " + 
		"from t_gd_plan plan " + 
		"left join t_gd_apply apply on apply.gdId = plan.gdId and apply.wxUserId = #{wxUserID} " + 
		"left join (select gdId as gdId, count(1) as applyNum from t_gd_apply group by gdId) as applyNum on applyNum.gdId = plan.gdId " + 
		"where plan.gdState = '1' and " + 
			  "plan.signupBeginDate <= #{currentDate} " + 
		  "and plan.signupEndDate >= #{currentDate} " + 
		  "limit 1 " )
	public GdPlan getGdPlansByWXUserID(@Param("wxUserID") String wxUserID, @Param("currentDate") String currentDate);
	
	/**
	 * 查询未来共度
	 * @param wxUserID 微信用户ID
	 * @param currentDate 当前系统日期,格式YYYYMMDD
	 * @return 返回当前处于报名中的共读计划信息
	 */
	@Select ("select " + 
			"plan.gdId as gdId, " + 
			"plan.gdTitle as gdTitle, " + 
			"plan.signupBeginDate as signupBeginDate, " + 
			"plan.signupEndDate as signupEndDate, " + 
			"plan.gdBeginDate as gdBeginDate, " + 
			"plan.gdEndDate as gdEndDate, " + 
			"plan.gdState as gdState, " + 
			"plan.sponsorUser as sponsorUser, " + 
			"plan.gdSlogan  as gdSlogan, " + 
			"plan.gdInstr  as gdInstr, " +
			"apply.wxUserId as wxUserId, " + 
			"applyNum.applyNum as applyNum " + 
		"from t_gd_plan plan " + 
		"left join t_gd_apply apply on apply.gdId = plan.gdId and apply.wxUserId = #{wxUserID} " + 
		"left join (select gdId as gdId, count(1) as applyNum from t_gd_apply group by gdId) as applyNum on applyNum.gdId = plan.gdId " + 
		"where plan.gdState = '1'  " + 
			  "and plan.signupBeginDate >= #{currentDate} " + 
		  "order by signupBeginDate " + 
		  "limit 1 " )
	public GdPlan getGdPlansByWXUserIDNext(@Param("wxUserID") String wxUserID, @Param("currentDate") String currentDate);
	
	
	/**
	 * @param wxUserID 微信用户ID
	 * @param currentDate 当前系统日期,格式YYYYMMDD
	 * @return 返回当前处于报名中的共读计划信息
	 */
	@Select ("select " + 
			"plan.gdId as gdId, " + 
			"plan.gdTitle as gdTitle, " + 
			"plan.signupBeginDate as signupBeginDate, " + 
			"plan.signupEndDate as signupEndDate, " + 
			"plan.gdBeginDate as gdBeginDate, " + 
			"plan.gdEndDate as gdEndDate, " + 
			"plan.gdState as gdState, " + 
			"plan.sponsorUser as sponsorUser, " + 
			"plan.gdSlogan  as gdSlogan, " + 
			"plan.gdInstr  as gdInstr, " +
			"apply.wxUserId as wxUserId, " + 
			"applyNum.applyNum as applyNum " + 
		"from t_gd_plan plan " + 
		"left join t_gd_apply apply on apply.gdId = plan.gdId and apply.wxUserId = #{wxUserID} " + 
		"left join (select gdId as gdId, count(1) as applyNum from t_gd_apply group by gdId) as applyNum on applyNum.gdId = plan.gdId " + 
		"where plan.gdState = '1'  " + 
		  "and plan.signupBeginDate <= #{currentDate} order by signupBeginDate desc " + 
		  "limit 1 " )
	public GdPlan getGdPlansByWXUserIDLast(@Param("wxUserID") String wxUserID, @Param("currentDate") String currentDate);
	
	/**
	 * @param gdId 共读计划编号
	 * @return 当前共读图书信息列表
	 */
	@Select ("select " + 
			"book.bookId as bookId, " + 
			"book.bookName as bookName, " + 
		    "book.bookAuthor as bookAuthor, " + 
		    "book.bookTag as bookTag, " + 
		    "book.bookSltPath as bookSltPath, " + 
		    "book.bookSummary as bookSummary " + 
		  "from  t_gd_plan_book_rel bookrel " + 
		  "left join t_gd_book book on book.bookId = bookrel.bookId " + 
		  "where bookrel.gdId = #{gdId} " )
	public List<GdBook> findGdBookInfos(@Param("gdId") int gdId);
	
	/**
	 * @param wxUserId
	 * @param gdId
	 */
	@Insert("insert into t_gd_apply(gdId,wxUserId,applyTime,signNum,signContinuousDays)" +
			" values (#{gdId},#{wxUserId},CURRENT_TIMESTAMP,0,0)")
	public void insertGdApplyInfo(@Param("wxUserId") String wxUserId, @Param("gdId") int gdId);
	
	/**
	 * @param wxUserId
	 * @param gdId
	 */
	@Delete("delete from t_gd_apply where gdId = #{gdId} and wxUserId = #{wxUserId}")
	public void deleteGdApplyInfo(@Param("wxUserId") String wxUserId, @Param("gdId") int gdId);
	
	/**
	 * @param wxUserId
	 * @param gdId
	 */
	@Select("select "
			+ "apply.signNum as signNum,"
			+ "apply.signContinuousDays as signContinuousDays"
			+ " from t_gd_apply apply where gdId = #{gdId} and wxUserId = #{wxUserId}")
	public GdApply getGdApplyInfo(@Param("wxUserId") String wxUserId, @Param("gdId") int gdId);
	
	/**
	 * @param wxUserId
	 * @param gdId
	 */
	@Update("update  "
			+ "t_gd_apply apply set apply.signNum = #{signNum},apply.signContinuousDays = #{signContinuousDays} "
			+ "where gdId = #{gdId} and wxUserId = #{wxUserId}")
	public void updateGdApplyInfo(@Param("wxUserId") String wxUserId, @Param("gdId") int gdId,@Param("signNum") int signNum,@Param("signContinuousDays") int signContinuousDays);
	/**
	 * @param currentDate 当前系统日期,格式YYYYMMDD
	 * @return 返回正在进行中的共读计划信息
	 */
	@Select ("select  " +
			"plan.gdId as gdId, " +
			"plan.gdTitle as gdTitle, " +
			"plan.signupBeginDate as signupBeginDate, " +
			"plan.signupEndDate as signupEndDate, " +
			"plan.gdBeginDate as gdBeginDate,  " +
			"plan.gdEndDate as gdEndDate, " + 
			"plan.gdState as gdState, " + 
			"plan.sponsorUser as sponsorUser, " +
			"plan.gdSlogan  as gdSlogan, " +
			"sign.signInPeriod as isSign " +
			"from t_gd_plan plan " +
			"left join t_gd_sign_in sign on plan.gdId=sign.gdId and sign.wxUserId = #{wxUserId} and sign.signInDate= #{currentDate}"+
	"where plan.gdState = '1' and " + 
	 "plan.gdBeginDate <= #{currentDate} " +
			  "and plan.gdEndDate >= #{currentDate} "+
			 " limit 1 ")
	public List<GdPlan> getGdPlanNow(@Param("currentDate") String currentDate,@Param("wxUserId") String wxUserId);
	
	
	/**
	 * @param currentDate 当前系统日期,格式YYYYMMDD
	 * @param wxUserId 当前用户，用于查询签到率
	 * @return 返回已完成的共读计划信息列表
	 */
	@Select ("select  " +
			"plan.gdId as gdId, " +
			"plan.gdTitle as gdTitle, " +
			"plan.signupBeginDate as signupBeginDate, " +
			"plan.signupEndDate as signupEndDate, " +
			"plan.gdBeginDate as gdBeginDate,  " +
			"plan.gdEndDate as gdEndDate, " + 
			"plan.gdState as gdState, " + 
			"plan.sponsorUser as sponsorUser, " +
			"plan.gdSlogan  as gdSlogan, " +
			"cast(plan.gdEndDate as signed)-cast(plan.gdBeginDate as signed) + 1 as signExpectNum, " + 
			"format(apply.signNum/(cast(plan.gdEndDate as signed)-cast(plan.gdBeginDate as signed) + 1) * 100,2) as signRate, " + 
			"apply.signNum as signNum, " +
			"apply.signContinuousDays as signContinuousDays " +
			"from t_gd_plan plan " +
			" left join t_gd_apply apply on plan.gdid = apply.gdid and apply.wxUserId = #{wxUserId} " +
	"where plan.gdState = '1' and " + 
			  "plan.gdEndDate <= #{currentDate} " )
	public List<GdPlan> getGdPlanFinish(@Param("currentDate") String currentDate, @Param("wxUserId") String wxUserId);
	/**
	 * @param currentDate 当前系统日期,格式YYYYMMDD
	 * @return 返回下期的共读计划信息**********************完事201801171629
	 */
	@Select ("select  " +
			"plan.gdId as gdId, " +
			"plan.gdTitle as gdTitle, " +
			"plan.signupBeginDate as signupBeginDate, " +
			"plan.signupEndDate as signupEndDate, " +
			"plan.gdBeginDate as gdBeginDate,  " +
			"plan.gdEndDate as gdEndDate, " + 
			"plan.gdState as gdState, " + 
			"plan.sponsorUser as sponsorUser, " +
			"plan.gdSlogan  as gdSlogan " +
			"from t_gd_plan plan " +
	"where plan.gdState = '1' and " + 
			  "plan.gdBeginDate >= #{currentDate} order by plan.signupBeginDate asc") 
	public List<GdPlan> getGdPlanNextMonth(@Param("currentDate") String currentDate);
	
	/**
	 * @param wxUserId
	 * @param gdId
	 */
	@Select("select "
			+ "count(1) as applyNum,"
			+ "sum(apply.signNum) as signNum"
			+ " from t_gd_apply apply where gdId = #{gdId}")
	public GdPlan getApplyNumAndSignNum( @Param("gdId") int gdId);
	
	
	/**
	 * @param currentDate
	 * @param gdPeriod
	 * @return 推送的共读信息
	 */
	@Select("select "
			+ " gdplan.gdId as gdId "
			+ " ,gdplan.gdTitle as gdTitle "
			+ " ,gdbook.bookName as bookName "
			+ " ,gdplan.sponsorUser as sponsorUser "
			+ " ,gdplan.gdSlogan as gdSlogan"
			+ " ,gdplan.gdBeginDate as gdBeginDate"
			+ " ,gdplan.gdEndDate as gdEndDate "
			+ " FROM t_gd_chapter_per_day_rel drel "
			+ " left join t_gd_book gdbook on drel.bookId = gdbook.bookid "
			+ " left join t_gd_plan gdplan on drel.gdid = gdplan.gdid "
			+ " where drel.gdDate = #{currentDate} "
			+ " and drel.gdPeriod  = #{gdPeriod}")
	public GdPlan getGdPushInfo(@Param("currentDate") String currentDate, @Param("gdPeriod") String gdPeriod);
	
	/**
	 * @param gdPeriod
	 */
	@Select("select "
			+ "gdId "
			+ "from t_gd_plan "
			+ "where gdBeginDate <= #{gdDate} and gdEndDate >= #{gdDate}")
	public Integer getGdId( @Param("gdDate") String gdDate);
	
	
	/**
	 * @param gdId 共读编号
	 * @return 根据共读编号返回共读已经报名的人数
	 */
	@Select("select count(1) as applyNum from t_gd_apply where gdid = #{gdId} ")
	public Integer getGdPlanApplyNum(@Param("gdId") Integer gdId);
	
	/**
	 * @param wxUserID 微信用户ID
	 * @param currentDate 当前系统日期,格式YYYYMMDD
	 * @return 返回当前处于报名中的共读计划信息
	 */
	@Select ("select " +
			"apply.wxUserId as wxUserId " + 
		"from t_gd_plan plan " + 
		"left join t_gd_apply apply on apply.gdId = plan.gdId and apply.wxUserId = #{wxUserID} " + 
		"where plan.gdState = '1' and " + 
			  "plan.gdBeginDate <= #{currentDate} " + 
		  "and plan.gdEndDate >= #{currentDate} " + 
		  "limit 1 " )
	public GdPlan getGdApplyFlagByWXUserID(@Param("wxUserID") String wxUserID, @Param("currentDate") String currentDate);

}
