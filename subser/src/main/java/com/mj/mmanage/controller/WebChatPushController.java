package com.mj.mmanage.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.mj.mmanage.model.GdApply;
import com.mj.mmanage.model.GdPlan;
import com.mj.mmanage.service.GdPlanService;
import com.mj.mmanage.service.WebChatPushService;
import com.mj.mmanage.util.Constants;
import com.mj.mmanage.util.DateUtil;
import com.mj.webchat.share.HttpsUtil;

public class WebChatPushController {
	
	private static Logger logger = Logger.getLogger(WebChatPushController.class);
	
	@Autowired
    private GdPlanService gdPlanService;
	
	public void authoPushWechatmsgToUser() {
		
		// 名称：书名（期数/期数)
		// 所属项目：宇信xx期共读
		// 项目发起人：xxx部门
		// 计划开始日期： yyyy/mm/dd
		// 计划结束日期：yyyy/mm/dd
		// bookName gdTitle   gdBeginDate  gdEndDate  sponsorUser   gdSlogan
		GdPlan gdPlan = gdPlanService.getGdPushInfo(DateUtil.getCurrentDate2Str("yyyyMMdd"), DateUtil.getCurrentPeriod());
		
		// 推送的信息
		JSONObject pushGddata = packJsonMsg(gdPlan);
		
		sendWechatmsgToUser(gdPlan.getGdId(),
						    Constants.PUSH_TEMPLAT_ID, 
						    Constants.PUSH_TEMPLAT_CLICKURL, 
						    Constants.PUSH_TEMPLAT_TOPCOLOR, 
						    pushGddata);
	}
	
	private JSONObject packJsonMsg(GdPlan gdPlan) {
		
		JSONObject json = new JSONObject();
		
        try {
            
        	if (gdPlan != null) {
    			
    			JSONObject jsonBooName = new JSONObject();
    			jsonBooName.put("value", gdPlan.getBookName());
    			jsonBooName.put("color", "#173177");
                json.put("bookName", jsonBooName);
                
                JSONObject jsonGdTitle = new JSONObject();
                jsonGdTitle.put("value", gdPlan.getGdTitle());
                jsonGdTitle.put("color", "#173177");
                json.put("gdTitle", jsonGdTitle);
                
                JSONObject jsonGdBeginDate = new JSONObject();
                jsonGdBeginDate.put("value", gdPlan.getGdBeginDate());
                jsonGdBeginDate.put("color", "#173177");
                json.put("gdBeginDate", jsonGdBeginDate);
    			
                JSONObject jsonGdEndDate = new JSONObject();
                jsonGdEndDate.put("value", gdPlan.getGdEndDate());
                jsonGdEndDate.put("color", "#173177");
                json.put("gdEndDate", jsonGdEndDate);
                
                JSONObject jsonSponsorUser = new JSONObject();
                jsonSponsorUser.put("value", gdPlan.getSponsorUser());
                jsonSponsorUser.put("color", "#173177");
                json.put("sponsorUser", jsonSponsorUser);
                
                JSONObject jsonGdSlogan = new JSONObject();
                jsonGdSlogan.put("value", gdPlan.getGdSlogan());
                jsonGdSlogan.put("color", "#173177");
                json.put("sponsorUser", jsonGdSlogan);
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
        
        String response = HttpsUtil.httpsRequestToString(Constants.ACCESS_TOKEN_URL, "GET", null);
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response);
        logger.info("jsonObject-->"+jsonObject);
        String token = jsonObject.getString("access_token");
        String templateURL = Constants.TEMPLATE_URL.replace("ACCESS_TOKEN", token);
        
        List<GdApply> lstGdApply =  gdPlanService.findGdApplyWxUserId(gdId);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        
        for (int i = 0; i < lstGdApply.size(); i++) {
        
        	String toWxUserId = lstGdApply.get(i).getWxUserId();
        
	        JSONObject json = new JSONObject();
	        try {
	            json.put("touser", toWxUserId);
	            json.put("template_id", templat_id);
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
        
        return "success";
    }
	    
}