package com.mj.mmanage.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.util.StringUtil;
import com.hthl.jwt.sdk.api.TokenMgr;
import com.hthl.jwt.sdk.config.Constant;
import com.hthl.jwt.sdk.model.SubjectModel;
import com.hthl.jwt.sdk.utils.UUIDGenerator;
import com.mj.jwt.utils.CheckToken;
import com.mj.mmanage.core.AppException;
import com.mj.mmanage.model.GdApply;
import com.mj.mmanage.model.GdBook;
import com.mj.mmanage.model.GdBookRescource;
import com.mj.mmanage.model.GdPlan;
import com.mj.mmanage.model.GdSignIn;
import com.mj.mmanage.model.MySign;
import com.mj.mmanage.model.SignBack;
import com.mj.mmanage.service.GdPlanService;
import com.mj.mmanage.util.ConstantErr;
import com.mj.mmanage.util.Constants;
import com.mj.mmanage.util.DateUtil;

/**
 * <p>
 * Description:共读计划维护 包括：查询、报名情况查询等
 * </p>
 * <p>
 * Copyright: Copyright (c) 2017
 * </p>
 * <p>
 * Create Date: 2017-12-27
 * </p>
 * <p>
 * Company: YUSYS
 * </p>
 * 
 * @author:YX-LiHaiLong
 * @version:GdPlanController,v1.0 YX-LiHaiLong
 */
@RestController
@RequestMapping("/gdplan")
@ResponseBody
public class GdPlanController {

	private static Logger logger = Logger.getLogger(GdPlanController.class);

	@Autowired
	private GdPlanService gdPlanService;

	/**
	 * 查询当前后台系统日期下，宇信共读可报名的共读计划详细信息。
	 * 
	 * @param wxUserID
	 *            微信用户ID
	 * @return 公共返回信息
	 * @throw:
	 */
	@ResponseBody
	@RequestMapping(value = "/now",method=RequestMethod.POST)
	public GdPlan getGdPlansByWXUserID(HttpServletRequest request) throws AppException {
		String userToken = request.getParameter("userToken");
		logger.info("getGdPlansByWXUserID->execute->userToken->" + userToken);
		if (StringUtil.isNotEmpty(userToken)) {
			userToken = userToken.split("&")[0];
		}
		
		logger.info("getGdPlansByWXUserID->execute->userToken.split[&][0]->" + userToken);
		
		String wxUserId = CheckToken.getWebChatUserId(userToken);
		
		String currentDate = DateUtil.getCurrentDate2Str("yyyyMMdd");
		
		logger.info("currentDate->"+currentDate);
		GdPlan gdPlan = gdPlanService.getGdPlansByWXUserID(wxUserId, currentDate);

		if (gdPlan != null) {
			
			// 设置日期格式
			if(gdPlan.getApplyNum()==null){
				gdPlan.setApplyNum("0");
			}
			gdPlan.setGdBeginDate(DateUtil.formatDate(gdPlan.getGdBeginDate()));
			gdPlan.setGdEndDate(DateUtil.formatDate(gdPlan.getGdEndDate()));
			gdPlan.setSignupBeginDate(DateUtil.formatDate(gdPlan.getSignupBeginDate()));
			gdPlan.setSignupEndDate(DateUtil.formatDate(gdPlan.getSignupEndDate()));
			gdPlan.setUserToken(userToken);

			// 0-未报名;1-已报名
			if (gdPlan.getWxUserId() != null && !gdPlan.getWxUserId().equals("")) {
				gdPlan.setApplyFlag("1");
			} else {
				gdPlan.setApplyFlag("0");
			}
			logger.info("applyFlag->"+ gdPlan.getApplyFlag());
			int gdId = gdPlan.getGdId();

			List<GdBook> gdbooks = gdPlanService.findGdBookInfos(gdId);
			if (gdbooks != null) {
			
				logger.info("gdbooks->"+ gdbooks.size());
			}
			else {
				logger.info("gdbooks is null");
			}
			
			gdPlan.setGdBook(gdbooks);
		}

		return gdPlan;
	}

	/**
	 * 共读计划报名、取消报名
	 * 
	 * @param gdID
	 *            共读编号
	 * @return 公共返回信息
	 * @throw:
	 */
	@RequestMapping(value = "/apply")
	public GdPlan gdPlanApply(@RequestParam("gdId") int gdId,
			@RequestParam("operateType") String operateType,
			@RequestParam("userToken") String userToken)
			throws AppException {
		GdPlan returnGdPlan = new GdPlan();
		String wxUserId = CheckToken.getWebChatUserId(userToken);
		logger.info("wxUserId->"+ wxUserId);
		logger.info("operateType->"+ operateType);
		logger.info("gdId->"+ gdId);
		logger.info("userToken->"+ userToken);
		if (operateType.equals(Constants.GD_APPLY)) {
			gdPlanService.insertGdApplyInfo(wxUserId, gdId);
			returnGdPlan.setApplyFlag(Constants.APPLY_YES);
		} else if (operateType.equals(Constants.GD_APPLY_CANCEL)) {
			gdPlanService.deleteGdApplyInfo(wxUserId, gdId);
			returnGdPlan.setApplyFlag(Constants.APPLY_YES_CANCEL);
		}
		
		Integer applyNum = gdPlanService.getGdPlanApplyNum(gdId);
		returnGdPlan.setApplyNum(String.valueOf(applyNum));
		return returnGdPlan;
	}

	// lhl 20180410重定义方法及查询内容
	/**
	 * @return 当天共读图书章节信息及是否签到标识
	 * @throws AppException 
	 */
	@RequestMapping(value = "/queryGDBookResource")
	public GdBookRescource queryGDBookResource(String userToken) throws AppException {
		GdBookRescource returnGdBookRescource = new GdBookRescource();
		String wxUserId = CheckToken.getWebChatUserId(userToken);
		logger.info("wxUserId->"+ wxUserId);
		String signInDate = DateUtil.getCurrentDate2Str("yyyyMMdd");
		String signPeroid = DateUtil.getCurrentPeriod();
		
		// 判断当前用户是否报名了本期共读。
		GdPlan gdplan = gdPlanService.getGdApplyFlagByWXUserID(wxUserId, signInDate);
		
		if (gdplan == null || StringUtil.isEmpty(gdplan.getWxUserId())) {
			returnGdBookRescource.setErrorCode(ConstantErr.NO_APPLY);
			returnGdBookRescource.setErrorMessage(ConstantErr.errorMap.get(ConstantErr.NO_APPLY));		
		}
		else {
		
			returnGdBookRescource = gdPlanService.queryGDBookResource(signInDate, signPeroid, wxUserId);
		}
		return returnGdBookRescource;
	}

	/**
	 * 共读签到
	 * 
	 * @return
	 * @throws AppException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/sign")
	public SignBack gdSignIn(String userToken) throws AppException {
		try {
	    
				String wxUserId = CheckToken.getWebChatUserId(userToken);
				String gdDate = DateUtil.getCurrentDate2Str("yyyyMMdd");
				logger.info("gdDate->" + gdDate);
				logger.info("wxUserId->" + wxUserId);
				String gdPeriod = DateUtil.getCurrentPeriod();
				logger.info("gdPeriod->" + gdPeriod);
				Date signInTime = new Date();
				//根据当前日期获取共读ID
				int gdId = gdPlanService.getGdId(gdDate);
				logger.info("gdId->" + gdId);
				GdSignIn gdSignIn = new GdSignIn();
				gdSignIn.setGdId(gdId);
				gdSignIn.setWxUserId(wxUserId);
				gdSignIn.setSignInDate(gdDate);
				gdSignIn.setSignInPeriod(gdPeriod);
				gdSignIn.setSignInTime(signInTime);
				String date = DateUtil.getYesterdayString();
		        //判断是否已经签到过
				if(null != gdPlanService.getGdSignIn(gdSignIn)){
					 new AppException("SUBS001");
				}
				GdSignIn gdSignRow = gdPlanService.getGdSignRow(date, wxUserId);// 签到时间
				// //获取该用户当期报名记录
				GdApply gdApply = gdPlanService.getGdApplyInfo(wxUserId, gdId); // 签到次数
				
				Integer signNum = 0;
				Integer signContinuousDays = 0;
				if (gdApply != null) {
					if (gdApply.getSignNum() != null) {
						signNum = gdApply.getSignNum() + 1;
					}
					if (gdApply.getSignContinuousDays() != null) {
						
						// 获取该用户当期报名记录中的“当期连续签到天数”值
						signContinuousDays = gdApply.getSignContinuousDays();
					}
				}
				if (gdSignRow != null) {
					// 更新t_gd_apply表中的signNum值以及更新signContinuousDays为+1的值
					signContinuousDays = signContinuousDays + 1;
					gdPlanService.updateGdApplyInfo(wxUserId, gdId, signNum,
							signContinuousDays);
				} else {
					// 更新t_gd_apply表中的signNum的值以及将signContinuousDays置为1
					signContinuousDays = 1;
					gdPlanService.updateGdApplyInfo(wxUserId, gdId, signNum,
							signContinuousDays);
				}
				try {
					gdPlanService.saveGdSignIn(gdSignIn);
					// 获取当前是第几个签到的
					// String ranks = gdPlanService.getGdSignRanks(gdId, wxUserId);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
					throw new AppException(ConstantErr.errorMap.get("SIGN_NO"));
				}
				// 签到返回数据
				SignBack signback = gdPlanService
						.getGdSignRanks(gdId, wxUserId, gdDate, DateUtil.getCurrentPeriod());
				String totalSign = signback.getTotalSign();
				logger.info("totalSign->" + totalSign);
				logger.info("headUrl->" + signback.getHeadUrl());
				// 待写：返回弹出框第jijiji个签到的。
				return signback;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		// return Constants.SIGN_YES;
	}

	/**
	 * 我的签到
	 * 
	 * @return
	 * @throws AppException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/mysign")
	public MySign mySignIn(String userToken) throws AppException, ParseException {
		
		MySign returnMysign = new MySign();
		String wxUserId = CheckToken.getWebChatUserId(userToken);
		logger.info("wxUserId->" + wxUserId);
		String gdDate = DateUtil.getCurrentDate2Str("yyyyMMdd");
		
		// 签到汇总信息,连续签到天数、签到率
		returnMysign = gdPlanService.getMySign(wxUserId, gdDate);
		
		if (returnMysign != null) {
		
			logger.info("wxUserId->" + wxUserId + "共读编号[" + returnMysign.getGdId() + "]" + "的连续签到率=" + returnMysign.getSignRate());
			
			if (StringUtil.isNotEmpty(returnMysign.getGdEndDate())) {
				
				gdDate = returnMysign.getGdEndDate();
				
				// 放空，前台判断使用
				returnMysign.setErrorCode("");
			}
			else {
				
				// 前段日历组件初始加载使用
				returnMysign.setGdEndDate(gdDate);
				
				returnMysign.setErrorCode(ConstantErr.NO_APPLY_ALWAYS);
				returnMysign.setErrorMessage(ConstantErr.errorMap.get(ConstantErr.NO_APPLY_ALWAYS));
			}
			
			SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
			
			Date date = simple.parse(gdDate);
			
			// 获取本月签到返回日期
			Calendar cal_1 = Calendar.getInstance();
			cal_1.setTime(date);
			cal_1.add(Calendar.MONTH, 0);
			cal_1.set(Calendar.DAY_OF_MONTH, 1);
			// 获取本月第一天
			String firstDay = simple.format(cal_1.getTime());
			
			Calendar cale = Calendar.getInstance();
			cale.setTime(date);
			cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));
			// 获取本月最后一天
			String lastDay = simple.format(cale.getTime());
			
			List<String> list = gdPlanService.signDte(returnMysign.getGdId(), firstDay, lastDay, wxUserId);
			
			// 本期共读具体签到的日期
			returnMysign.setList(list);
		}
		
		return returnMysign;
	}

	@RequestMapping(value = "/content/{userToken}")
	public List<GdBookRescource> gdContent(
			@PathVariable("userToken") String userToken) throws AppException {
		String wxUserId = CheckToken.getWebChatUserId(userToken);
		String currentDate = DateUtil.getCurrentDate2Str("yyyyMMdd");
		String currentPeriod = DateUtil.getCurrentPeriod();
		return gdPlanService.findGdBookResource(wxUserId, currentDate, currentPeriod);
	}

	/**
	 * 查询共读计划列表信息。包含：正在进行、已完成、下期预告三种状态。
	 * 
	 * @param planType
	 *            共度计划列表状态
	 * @return 共读计划列表信息
	 * @throw:
	 */
	@RequestMapping(value = "/list/{planType}")
	public List<GdPlan> getGdPlanListByPlanType(
			@PathVariable("planType") String planType,HttpServletRequest request) throws AppException {
		String userToken = request.getParameter("userToken");
		logger.info("userToken->"+ userToken);
		String wxUserId = CheckToken.getWebChatUserId(userToken);
		String currentDate = DateUtil.getCurrentDate2Str("yyyyMMdd");
		logger.info("planType->"+ planType);
		logger.info("wxUserId->"+ wxUserId);
		logger.info("currentDate->"+ currentDate);
		try {
			List<GdPlan> gdPlans = gdPlanService.getGdPlanList(currentDate, planType, wxUserId);
			if (null != gdPlans && gdPlans.size() > 0) {
				logger.info("gdPlans.size->"+ gdPlans.size());
				for (int i = 0; i < gdPlans.size(); i++) {
					GdPlan gdPlan = gdPlans.get(i);
					gdPlan.setGdBeginDate(DateUtil.formatDate(gdPlan.getGdBeginDate()));
					gdPlan.setGdEndDate(DateUtil.formatDate(gdPlan.getGdEndDate()));
					gdPlan.setSignupBeginDate(DateUtil.formatDate(gdPlan.getSignupBeginDate()));
					gdPlan.setSignupEndDate(DateUtil.formatDate(gdPlan.getSignupEndDate()));
					int gdId = gdPlan.getGdId();
					logger.info("gdId->"+ gdId);
					List<GdBook> gdbooks = gdPlanService.findGdBookInfos(gdId);
					
					if (gdbooks != null) {
						logger.info("gdbooks.size->"+ gdbooks.size());
					}
					
					gdPlan.setGdBook(gdbooks);
				}
			}
			return gdPlans;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询某个时间段签到记录， 在【我的】里没啥用
	 * 
	 * @param beginDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @param wxUserId
	 *            登录的用户
	 * @return 签到记录
	 * @throw:
	 */
	@RequestMapping(value = "/signdetail")
	public List<GdSignIn> gdSignDetail(
			@RequestParam("beginDate") String beginDate,
			@RequestParam("endDate") String endDate,
			HttpServletRequest request
			) throws AppException {
		
		String userToken = request.getParameter("userToken");
		logger.info("userToken->"+ userToken);
		String wxUserId = CheckToken.getWebChatUserId(userToken);
		logger.info("wxUserId->"+ wxUserId);
		logger.info("beginDate->"+ beginDate);
		logger.info("endDate->"+ endDate);
		
		List<GdSignIn> lstGdSignIn =  gdPlanService.getSignDetial(beginDate.replaceAll("-", ""),
				endDate.replaceAll("-", ""), wxUserId);
		logger.info("lstGdSignIn.size()->"+ lstGdSignIn.size());
		return lstGdSignIn;
	}
}
