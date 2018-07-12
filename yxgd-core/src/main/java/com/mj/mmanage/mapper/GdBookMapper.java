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

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import com.mj.mmanage.model.GdBook;
import com.mj.mmanage.model.GdBookRescource;

/**
 * <p> Description:图书维护业务逻辑 </p>
 * <p> Copyright: Copyright (c) 2017 </p>
 * <p> Create Date: 2018-1-2</p>
 * <p> Company: YUSYS </p> 
 *@author:YX-LiAnDong
 *@version:GdBookMapper,v1.0 YX-LiAnDong
 */
public interface GdBookMapper extends Mapper<GdBook>, MySqlMapper<GdBook> {
	
/**
 * 当前日期时段下该用户可读的图书章节具体内容
 * @param wxUserId
 * @param currentDate
 * @param currentPeriod
 * @return
 */
	@Select("select " + 
			"res.filePath as filePath, " + 
		    "res.fileName as fileName, " + 
		    "res.fileSize as fileSize, " + 
		    "res.fileType as fileType, " + 
		    "sign.wxUserId as wxUserId " + 
		  "from t_gd_chapter_per_day_rel rel " + 
		  "left join t_gd_book_resource res " + 
			  "on  rel.bookId = res.bookId " + 
			  "and rel.bookChapter = res.bookChapter " + 
		   "left join t_gd_sign_in sign " + 
			  "on  sign.signInDate = rel.gdDate " + 
		      "and sign.signInPeriod = rel.gdPeriod " + 
		      "and sign.wxUserId = #{wxUserId} " + 
		  "where rel.gdDate = #{currentDate} " + 
		    "and rel.gdPeriod = #{currentPeriod} ")
	public List<GdBookRescource> findGdBookResource(@Param("wxUserId") String wxUserId, 
								   @Param("currentDate") String currentDate, 
								   @Param("currentPeriod") String currentPeriod);
	
}
