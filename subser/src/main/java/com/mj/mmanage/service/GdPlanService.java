package com.mj.mmanage.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import com.mj.mmanage.mapper.GdApplyMapper;
import com.mj.mmanage.mapper.GdBookMapper;
import com.mj.mmanage.mapper.GdBookRescourceMapper;
import com.mj.mmanage.mapper.GdPlanMapper;
import com.mj.mmanage.mapper.GdSignInMapper;
import com.mj.mmanage.model.GdApply;
import com.mj.mmanage.model.GdBook;
import com.mj.mmanage.model.GdBookRescource;
import com.mj.mmanage.model.GdPlan;
import com.mj.mmanage.model.GdSignIn;
import com.mj.mmanage.model.MySign;
import com.mj.mmanage.model.SignBack;
import com.mj.mmanage.util.Constants;

/**
 * @author lihailong
 * @since 2018-01-02
 */
@Service
public class GdPlanService {
	
	private static Logger logger = Logger.getLogger(GdPlanService.class);

    @Autowired
    private GdPlanMapper gdPlanMapper;
    
    @Autowired
    private GdSignInMapper gdSignInMapper;
    
    @Autowired
    private GdBookMapper gdBookMapper;
    
    @Autowired
    private GdApplyMapper gdApplyMapper;
    
    @Autowired
    private GdBookRescourceMapper gdBookRescourceMapper;
    
    
    public GdPlan getGdPlansByWXUserID(String wxUserID, String currentDate) {
    	
    	GdPlan gdPlan = null ;
    	String nowFlag = "0";
    	//查询当前共度
    	gdPlan = gdPlanMapper.getGdPlansByWXUserID(wxUserID, currentDate);
    	//查询未来共度
    	if(gdPlan == null ){
    		gdPlan = gdPlanMapper.getGdPlansByWXUserIDNext(wxUserID, currentDate);
    		nowFlag = "1";
    	}
    	//查询过去的最近共度
    	if(gdPlan == null ){
    		gdPlan = gdPlanMapper.getGdPlansByWXUserIDLast(wxUserID, currentDate);
    		nowFlag = "2";
    	}
    	gdPlan.setNowFlag(nowFlag);
    	return gdPlan;
    }
    
   public List<GdBook> findGdBookInfos(int gdId) {
    	
	   return gdPlanMapper.findGdBookInfos(gdId);
   }
   /**
    * 共读计划报名
    *@param gdID 共读编号
    *@return 无
    *@throw:
    */
   public void insertGdApplyInfo(String wxUserId, int gdId) {
	   
//	   Example example = new Example(GdPlan.class);
//	   example.createCriteria().andEqualTo("wxUserId", wxUserId).andEqualTo("gdId", gdId);
//	   gdApplyMapper.selectByExample(example);
	   gdPlanMapper.insertGdApplyInfo(wxUserId, gdId);
   }
   /**
    * 共读计划取消报名
    *@param gdID 共读编号
    *@return 无
    *@throw:
    */
   public void deleteGdApplyInfo(String wxUserId, int gdId) {
	   gdPlanMapper.deleteGdApplyInfo(wxUserId, gdId);
   }
   /**
    * 根据gdId和wxUserId查询一条共读计划记录
    *@param gdID 共读编号;wxUserId 微信id
    *@return GdApply
    *@throw:
    */
   public GdApply getGdApplyInfo(String wxUserId, int gdId) {
	   GdApply gdApply = gdPlanMapper.getGdApplyInfo(wxUserId, gdId);
	   return gdApply;
   }
   /**
    * 根据gdId和wxUserId更新一条共读计划记录
    *@param gdID 共读编号;wxUserId 微信id
    *@throw:
    */
   public void updateGdApplyInfo(String wxUserId, int gdId,int signNum,int signContinuousDays) {
	   gdPlanMapper.updateGdApplyInfo(wxUserId,gdId,signNum,signContinuousDays);
   }
   
   
   public List<GdBookRescource> findGdBookResource(String wxUserId, String currentDate, String currentPeriod) {
	   
	   return gdBookMapper.findGdBookResource(wxUserId, currentDate, currentPeriod);
   }
   
   /**
    * 根据微信id和上次应该签到的日期查询上次签到记录（例如当前是PM，就去查今天的AM；当前是AM就去查昨天下午的PM）
    *@param gdSignIn 签到信息
    *@throw:
    */
   public GdSignIn getGdSignRow(String date,String wxUserId) {
	   return gdSignInMapper.getGdSignRow(wxUserId, date);
   }
   
   /**
    * 共读签到
    *@param gdSignIn 签到信息
    *@throw:
    */
   public void saveGdSignIn(GdSignIn gdSignIn) {
	   gdSignInMapper.insert(gdSignIn);
   }
   
   /**
    * 查询签到
    *@param gdSignIn 签到信息
    *@throw:
    */
   public List getGdSignIn(GdSignIn gdSignIn) {
	   gdSignIn.setSignInTime(null);
	  return  gdSignInMapper.select(gdSignIn);
   }
   /**
    * 共读签到标识
    *@param gdSignIn 签到排名
    *@throw:
    */
   public String isGdSignIn(int gdId,String wxUserId,String signInDate) {
	   return  gdSignInMapper.isGdSignin(gdId, wxUserId, signInDate);
   }
   /**
    * 共读签到返回
    *@param gdSignIn 签到排名
    *@throw:
    */
   public SignBack getGdSignRanks(int gdId,String wxUserId,String signInDate, String gdPeriod) {
	   logger.info("gdId=>"+gdId+"//wxUserId=>"+wxUserId+"//signInDate=>"+signInDate+"//gdPeriod=>"+gdPeriod);
	   return  gdSignInMapper.getGdSignRanks(gdId, wxUserId,signInDate, gdPeriod);
   }
   /**
    * 我的签到
    *@param gdSignIn 我的签到
    *@throw:
    */
   public MySign getMySign(String wxUserId,String signInDate) {
	   return  gdSignInMapper.mySign(wxUserId,signInDate);
   }
   /**
    * 我的签到日期
    *@param gdSignIn 我的签到
    *@throw:
    */
   public List<String> signDte(Integer gdId, String firstDay,String lastDay,String wxUserId) {
	   return  gdSignInMapper.signDate(gdId, firstDay, lastDay,wxUserId);
   }

   /**
    * 共读签到
    *@param gdSignIn 签到信息
    *@throw:
    */
   public List<GdSignIn> getSignDetial(String beginDate ,String endDate,String wxUserId) {
		Example example = new Example(GdSignIn.class);
    	example.createCriteria().andBetween("signInDate",beginDate, endDate).andEqualTo("wxUserId",wxUserId);
    	return gdSignInMapper.selectByExample(example);
   }
   /**
    * 共读计划主表查询(t_gd_plan)
    *@param currentDate 当前系统时间
    *@throw:
    */  
   public List<GdPlan> getGdPlanList(String currentDate,String planType,String wxUserId) {
	   	List<GdPlan> gdPlans = new ArrayList<GdPlan>();
		if(Constants.GD_PLAN_TYPE_NOW.equals(planType)){
			//当期进行中的共度计划查询
			gdPlans = gdPlanMapper.getGdPlanNow(currentDate,wxUserId);
    	}else if(Constants.GD_PLAN_TYPE_FINISH.equals(planType)){
    		//已完成的共度计划查询
    		gdPlans = gdPlanMapper.getGdPlanFinish(currentDate,wxUserId);
    		return gdPlans;
    	}else{
    		//下一期的共度计划查询
    		GdPlan gdPlan = new GdPlan();
    		gdPlans = gdPlanMapper.getGdPlanNextMonth(currentDate);
    		if (gdPlans.size()>1){
    			gdPlan = gdPlans.get(0);
    			gdPlans.clear();
    			gdPlans.add(gdPlan);
    		}
    	}
   	return gdPlans;
   }
   
   
   public GdBookRescource queryGDBookResource(String signInDate, String signPeroid, String wxUserId) {
	   
	   List<GdBookRescource> lstBR = gdBookRescourceMapper.queryGDBookResource(signInDate, signPeroid, wxUserId);
	   // 将查询出来的文本、音频、视频数据转换到一个对象 
	   GdBookRescource returnObject = new GdBookRescource();
	   
	   String fileType = "";
	   String fllePathAndName = "";
	   double fileSize = 0;
	   
	   if (lstBR != null) {
		   
		   for (int i = 0; i <lstBR.size(); i++ ) {
			   
			   GdBookRescource br = lstBR.get(i);
			   
			   fllePathAndName = br.getFilePath() + br.getFileName();
			   fileSize = br.getFileSize();
			   
			   fileType = br.getFileType();
			   
			   if (fileType != null) {
			   
				   if (fileType.equals(Constants.UPLOAD_FILE_TYPE_TXT)) { 
					   returnObject.setTxtFilePathAndName(fllePathAndName);
					   returnObject.setTxtFileSize(fileSize);
				   }
				   else if (fileType.equals(Constants.UPLOAD_FILE_TYPE_VOICE)) { 
					   returnObject.setVoiceFilePathAndName(fllePathAndName);
					   returnObject.setVoiceFileSize(fileSize);
				   }
				   else if (fileType.equals(Constants.UPLOAD_FILE_TYPE_VIDEO)) { 
					   returnObject.setVideoFilePathAndName(fllePathAndName);
					   returnObject.setVideoFileSize(fileSize);
				   }
				   
				   // 只第一次便利即可
				   if (i == 0) {
					   
					   returnObject.setBookId(br.getBookId());
					   returnObject.setBookChapter(br.getBookChapter());
					   returnObject.setGdId(br.getGdId());
					   returnObject.setSignFlag(br.getSignFlag());
				   }
				   
			   }
			  
		   }
	   }
	   
	   return returnObject;
   }
   
   /**
	 * @param currentDate 当天日期
	 * @param gdPeriod AM or PM
	 * @return 系统自动推送时的当天共读信息
	 */
	public GdPlan getGdPushInfo(String currentDate, String gdPeriod) {
		   
		return gdPlanMapper.getGdPushInfo(currentDate, gdPeriod);
	}
	
	/**
	 * @param gdId
	 * @return 根据共读ID获得报名的微信用户ID
	 */
	public List<GdApply> findGdApplyWxUserId(int gdId, String signInDate) {
		return gdApplyMapper.findGdApplyWxUserId(gdId, signInDate);
	}
	/**
	 * @param gdPeriod
	 * @return 根据当前日期获得共读ID
	 */
	public int getGdId(String gdDate) {
		return gdPlanMapper.getGdId(gdDate);
	}
	
	/**
	 * @param gdId 共读编号
	 * @return 根据共读编号返回共读已经报名的人数
	 */
	public Integer getGdPlanApplyNum(Integer gdId) {
		return gdPlanMapper.getGdPlanApplyNum(gdId);
	}
	
	/**
	 * @param wxUserID 微信用户ID
	 * @param currentDate 当前日期
	 * @return 报名信息
	 */
	public GdPlan getGdApplyFlagByWXUserID(String wxUserID, String currentDate) {
		return gdPlanMapper.getGdApplyFlagByWXUserID(wxUserID, currentDate);
	}
   
}
