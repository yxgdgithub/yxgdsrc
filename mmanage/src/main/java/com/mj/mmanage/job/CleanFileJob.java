package com.mj.mmanage.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <p> Description:用户删除文件如文本和音频操作时，只是将数据库状态进行修改，以避免真实试试删除文件时间过长。
 * 之后由定时任务来根据数据库数据进行数据删除和文件删除 </p>
 * <p> Copyright: Copyright (c) 2017 </p>
 * <p> Create Date: 2017-12-26</p>
 * <p> Company: yusys </p> 
 *@author:YX-LiAnDong
 *@version:CleanFileJob,v1.0 YX-LiAnDong
 */
@Component
public class CleanFileJob {

	public final static long ONE_MINUTE = 60 * 10000;
	
	@Scheduled(fixedDelay = ONE_MINUTE)
	public void fixedDelayJob(){
		System.out.println("查询并删除需要删除的文件");
	}
	
	 
	
}
