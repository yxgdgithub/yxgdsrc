package com.mj.mmanage.controller;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.mj.mmanage.model.GdApply;
import com.mj.mmanage.model.GdPlan;
import com.mj.mmanage.service.GdPlanService;
import com.mj.mmanage.service.WebChatPushService;
import com.mj.mmanage.util.Constants;
import com.mj.mmanage.util.DateUtil;
import com.mj.webchat.util.AccessTokenUtil;

@Component
public class WebChatPushController {
	
	private static Logger logger = Logger.getLogger(WebChatPushController.class);
	
	@Autowired
    private GdPlanService gdPlanService;
	
	/*
	 * 上午9点、下午5点各推送一次
	 */
	@Scheduled(cron = "0 0 8,18 * * ?")
//	@Scheduled(cron = "0 25 17 * * ?")
	public void authoPushWechatmsgToUser() {
		
//		您有一条新的阅读任务待签到
//
//		共读名称：{{gdTitle.DATA}} 
//		共读口号：{{gdSlogan.DATA}} 
//		共读发起人：{{sponsorUser.DATA}}
//		计划开始时间：{{gdBeginDate.DATA}}
//		计划结束时间：{{gdEndDate.DATA}}
//
//		坚持阅读、一起克服读书惰性。加油！

		// bookName gdTitle   gdBeginDate  gdEndDate  sponsorUser   gdSlogan
		GdPlan gdPlan = gdPlanService.getGdPushInfo(DateUtil.getCurrentDate2Str("yyyyMMdd"), DateUtil.getCurrentPeriod());
		
		if (gdPlan != null) {
		
			logger.info("推送的共读信息，共读编号为：" + gdPlan.getGdId());
			
			// 推送的信息
			JSONObject pushGddata = packJsonMsg(gdPlan);
			
			logger.info("推送信息封装完毕->" + pushGddata.toString());
			
			sendWechatmsgToUser(gdPlan.getGdId(),
							    Constants.PUSH_TEMPLAT_ID, 
							    Constants.PUSH_TEMPLAT_CLICKURL, 
							    Constants.PUSH_TEMPLAT_TOPCOLOR, 
							    pushGddata);
		}
		else {
			logger.info("查无需要推送的共读信息");
		}
	}
	
	private JSONObject packJsonMsg(GdPlan gdPlan) {
		
		JSONObject json = new JSONObject();
		
        try {
            
        	if (gdPlan != null) {
    			
                JSONObject jsonGdTitle = new JSONObject();
                jsonGdTitle.put("value", gdPlan.getGdTitle());
                jsonGdTitle.put("color", "#173177");
                json.put("gdTitle", jsonGdTitle);
                
                JSONObject jsonGdBeginDate = new JSONObject();
                jsonGdBeginDate.put("value", DateUtil.getDateString(DateUtil.parseDateStr(gdPlan.getGdBeginDate(), "yyyyMMdd"), "yyyy-MM-dd"));
                jsonGdBeginDate.put("color", "#173177");
                json.put("gdBeginDate", jsonGdBeginDate);
    			
                JSONObject jsonGdEndDate = new JSONObject();
                jsonGdEndDate.put("value", DateUtil.getDateString(DateUtil.parseDateStr(gdPlan.getGdEndDate(), "yyyyMMdd"), "yyyy-MM-dd"));
                jsonGdEndDate.put("color", "#173177");
                json.put("gdEndDate", jsonGdEndDate);
                
                JSONObject jsonSponsorUser = new JSONObject();
                jsonSponsorUser.put("value", gdPlan.getSponsorUser());
                jsonSponsorUser.put("color", "#173177");
                json.put("sponsorUser", jsonSponsorUser);
                
                JSONObject jsonGdSlogan = new JSONObject();
                jsonGdSlogan.put("value", gdPlan.getGdSlogan());
                jsonGdSlogan.put("color", "#173177");
                json.put("gdSlogan", jsonGdSlogan);
    		}
        } 
        catch (JSONException e) {
            e.printStackTrace();
        }
        
        return json;
    }
	 
    public String sendWechatmsgToUser(int gdId,
										  String templat_id, 
										  String clickurl, 
										  String topcolor, 
										  JSONObject data) {
        
        String token = AccessTokenUtil.ACCESS_TOKEN;
        logger.info("access_token->" + token);
        
        if (!StringUtils.isEmpty(token)) {
        
	        String templateURL = Constants.TEMPLATE_URL.replace("ACCESS_TOKEN", token);
	        
	        // 当天需要签到，而未签到的
	        List<GdApply> lstGdApply =  gdPlanService.findGdApplyWxUserId(gdId, DateUtil.getCurrentDate2Str("yyyyMMdd"));
	        
	        if (lstGdApply != null) {
	        	
	        	logger.info("今日本次需要推送的人数->" + lstGdApply.size());
	        	
		        ExecutorService executorService = Executors.newFixedThreadPool(10);
		        
		        for (int i = 0; i < lstGdApply.size(); i++) {
		        
		        	String toWxUserId = lstGdApply.get(i).getWxUserId();
		        	
		        	// 测试只给李海龙、陈翔、宁兆路发送推送消息
		        	//if (toWxUserId.equals("oC98LuC7T2U_B4Juy6HBO17kZfaE") || toWxUserId.equals("oC98LuIFhx8kBL5W1K4uFGgIkD2g") || toWxUserId.equals("oC98LuEf5GFwBTYRzPF6eXCeGr-Q")) {
		        
				        JSONObject json = new JSONObject();
				        try {
				            json.put("touser", toWxUserId);
				            json.put("template_id", templat_id);
				            logger.info("clickurl->" + clickurl);
				            json.put("url", clickurl);
				            json.put("topcolor", topcolor);
				            json.put("data", data);
				        } catch (JSONException e) {
				            e.printStackTrace();
				        }
				        
				        WebChatPushService webChatPushService =  new WebChatPushService();
				        webChatPushService.setJsonObject(json);
				        webChatPushService.setTemplateURL(templateURL);
				        
				        executorService.execute(webChatPushService);
		        	}
		         //}
		        
		        try {  
		        	
		        	// 通知关闭
		        	executorService.shutdown();  
		       
		            // 所有的任务都结束的时候，返回TRUE
		            if(!executorService.awaitTermination(Constants.EXECUTOR_SERVICE_AWAIT_TIME, TimeUnit.MILLISECONDS)){  
		                
		            	// 超时的时候向线程池中所有的线程发出中断(interrupted)。  
		            	executorService.shutdownNow();  
		            }  
		        } catch (InterruptedException e) {  
		        	
		            // awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。  
		            logger.error("awaitTermination interrupted: " + e);  
		            executorService.shutdownNow();  
		        }  
	        }
	        else {
	        	logger.info("今日无需要推送的人员");
	        }
        }
        else {
        	logger.error("access_token未获取，未推送消息!");
        }
        
        return "success";
    }
    
}