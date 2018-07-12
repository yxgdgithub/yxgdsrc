package com.mj.mmanage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import com.mj.mmanage.model.GdBookRescource;

public interface GdBookRescourceMapper extends Mapper<GdBookRescource>, MySqlMapper<GdBookRescource>  {
	   
	
	 
	
	@Select (" select "
			+ " gdPlan.gdid as  gdId, "
			+ " br.filePath as filePath, "
			+ " br.fileName as  fileName,"
			+ " br.fileSize as  fileSize, "
			+ " br.fileType as  fileType, "
			+ " br.bookChapter as bookChapter, "
			+ " br.bookId as bookId, "
			+ " br.fileId as fileId, "
			+ " ifnull(signId.wxUserId,'0') as signFlag "
			+ " from t_gd_plan gdPlan  "
			+ " inner join  t_gd_chapter_per_day_rel  dayrel  on gdPlan.gdid = dayrel.gdid and dayrel.gdDate = #{signInDate} and dayrel.gdPeriod = #{signPeroid} "
			+ " left join t_gd_book_resource br on dayrel.bookId = br.bookId and dayrel.bookChapter = br.bookChapter "
			+ " left join t_gd_sign_in signId on  signId.signInDate = dayrel.gdDate and  signId.signInPeriod = dayrel.gdPeriod "
			+ "   and signId.wxUserId=#{wxUserId} and signId.gdid = dayrel.gdId "
			+ " where  gdPlan.gdBeginDate <= #{signInDate}  "
			+ " and gdPlan.gdEndDate >= #{signInDate} "
			+ " and gdPlan.gdState = '1' " )
	public List<GdBookRescource> queryGDBookResource(@Param("signInDate") String signInDate, 
													 @Param("signPeroid") String signPeroid, 
													 @Param("wxUserId") String wxUserId);
}
