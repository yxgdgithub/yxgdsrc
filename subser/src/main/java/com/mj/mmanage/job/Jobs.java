package com.mj.mmanage.job;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mj.mmanage.mapper.GdChapterPerDayRelMapper;
import com.mj.mmanage.model.GdChapterPerDayRel;
import com.mj.mmanage.model.GdPlan;
import com.mj.mmanage.util.DateUtil;

@Component
public class Jobs {
	
	private static Logger logger = Logger.getLogger(Jobs.class);
    
	@Autowired
    private GdChapterPerDayRelMapper gdChapterPerDayRelMapper;
    
	@Scheduled(cron = "* * 1 * * ?")
	public void fixedRateJob(){
		    int intBeginDate = 0;
		    int intEndDate = 0;

			String signupEndDate = DateUtil.now("yyyyMMdd");
			logger.info("signupEndDate->"+ signupEndDate);	
		   //查询出下个月的共读开始日期、共读结束日期
		   GdPlan gdPlan = gdChapterPerDayRelMapper.getNextFirstEndDay(signupEndDate);
		   
		   if (gdPlan != null) {
			   intBeginDate = Integer.parseInt(gdPlan.getGdBeginDate());
		   	   intEndDate = Integer.parseInt(gdPlan.getGdEndDate());
		   }
		   int days = intEndDate - intBeginDate + 1;
		   //查询出共度图书章节信息
		   List<GdChapterPerDayRel> lstDayRel = new ArrayList<GdChapterPerDayRel>();
		   lstDayRel = gdChapterPerDayRelMapper.getGdBookChapterRelInfo(signupEndDate);
		   logger.info("days->"+ days);
		   
		   //删除下个月已存在的t_gd_chapter_per_day_rel表的数据
		   String gdDate = gdPlan.getGdBeginDate().substring(0,6);
		   logger.info("gdDate->"+ gdDate);		   
		   Integer gdId1 = gdPlan.getGdId();
		   gdChapterPerDayRelMapper.deleteGdChapterPerDayRelData(gdDate,gdId1);
			int lsSize = lstDayRel.size();
			GdChapterPerDayRel gr = new GdChapterPerDayRel();
			GdChapterPerDayRel gr2 = new GdChapterPerDayRel();
			for (int i = 1; i <= days; i++) {
				if (i <= lsSize) {
					GdChapterPerDayRel gr1 = lstDayRel.get(i-1);
					gr.setGdId(gr1.getGdId());
					gr.setBookId(gr1.getBookId());
					gr.setBookChapter(gr1.getBookChapter());
					gr.setGdDate(String.valueOf(intBeginDate));
					gr.setGdPeriod("AM");
					gr.setRecordTime(Timestamp.valueOf(DateUtil.now()));
					
					gr2.setGdId(gr1.getGdId());
					gr2.setBookId(gr1.getBookId());
					gr2.setBookChapter(gr1.getBookChapter());
					gr2.setGdDate(String.valueOf(intBeginDate));
					gr2.setGdPeriod("PM");
					gr2.setRecordTime(Timestamp.valueOf(DateUtil.now()));
					
				}else{
					gr.setGdId(gdPlan.getGdId());
					gr.setBookId(null);
					gr.setBookChapter(null);
					gr.setGdDate(String.valueOf(intBeginDate));
					gr.setGdPeriod("AM");
					gr.setRecordTime(Timestamp.valueOf(DateUtil.now()));
					
					gr2.setGdId(gdPlan.getGdId());
					gr2.setBookId(null);
					gr2.setBookChapter(null);
					gr2.setGdDate(String.valueOf(intBeginDate));
					gr2.setGdPeriod("PM");
					gr2.setRecordTime(Timestamp.valueOf(DateUtil.now()));
				}
				intBeginDate++;
				gdChapterPerDayRelMapper.insert(gr);
				gdChapterPerDayRelMapper.insert(gr2);
			}
			
	}
}
