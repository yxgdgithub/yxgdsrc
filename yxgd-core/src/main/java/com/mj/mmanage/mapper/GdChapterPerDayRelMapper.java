package com.mj.mmanage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.mj.mmanage.model.GdChapterPerDayRel;
import com.mj.mmanage.model.GdPlan;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface GdChapterPerDayRelMapper extends Mapper<GdChapterPerDayRel>, MySqlMapper<GdChapterPerDayRel> {
	
	@Select("select "
			+ "plan.gdBeginDate,plan.gdEndDate,plan.gdId "
			+ "from  t_gd_plan plan "
			+ "where plan.gdState = '1' and plan.signupEndDate = #{signupEndDate}")
	public GdPlan getNextFirstEndDay(@Param("signupEndDate") String signupEndDate);
	@Select("select distinct "
			+ "p.gdId, br.bookChapter,br.bookId "
			+ "from t_gd_plan p "
			+ "left join t_gd_plan_book_rel brel on p.gdId = brel.gdId "
			+ "left join t_gd_book book on brel.bookid = book.bookid "
			+ "left join t_gd_book_resource br on book.bookId = br.bookId "
			+ "where "
			+ "p.gdState = '1' and p.signupEndDate = #{signupEndDate} "
			+ "order by p.gdid,br.bookid,br.bookChapter asc")
	public List<GdChapterPerDayRel> getGdBookChapterRelInfo(@Param("signupEndDate") String signupEndDate);
	/**
	 * @param gdDate
	 */
	@Delete("delete from t_gd_chapter_per_day_rel where gdDate like '${gdDate}%' and gdId = #{gdId}")
	public void deleteGdChapterPerDayRelData(@Param("gdDate") String gdDate, @Param("gdId") int gdId);

}
