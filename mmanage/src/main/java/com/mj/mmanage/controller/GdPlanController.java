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

package com.mj.mmanage.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mj.mmanage.core.AppException;
import com.mj.mmanage.core.BaseController;
import com.mj.mmanage.model.GdBook;
import com.mj.mmanage.model.GdPlan;
import com.mj.mmanage.service.GdBookService;
import com.mj.mmanage.service.GdPlanService;
import com.mj.mmanage.util.ConstantErr;
import com.mj.mmanage.util.Constants;
import com.mj.mmanage.util.DateUtil;
import com.mj.mmanage.util.StringUtil;

/**
 * <p> Description:共读计划维护  包括：新增、修改、删除、查询、报名情况查询等 </p>
 * <p> Copyright: Copyright (c) 2017 </p>
 * <p> Create Date: 2017-12-27</p>
 * <p> Company: YUSYS </p> 
 *@author:YX-LiAnDong
 *@version:GdPlanController,v1.0 YX-LiAnDong
 */
@RestController
@RequestMapping("/gdplan")
@SessionAttributes(Constants.SESSION_USERID)
@ResponseBody
public class GdPlanController extends BaseController{
  
    
    @Autowired
    private GdPlanService gdPlanService;
    /**
     * 新增或修改共读计划基本信息 根据共读编号存在与否判断
     *@param gdBook 图书基本信息对象
     *@return 公共返回信息
     *@throw:
     */
    @RequestMapping(value = "/addOrModify")
    public String addOrModify(GdPlan gdPlan ,@ModelAttribute(Constants.SESSION_USERID) String userId )throws AppException {
        
    	//处理日期格式 
    	if(!StringUtil.isEmpty(gdPlan.getSignupBeginDate().isEmpty()))
    		gdPlan.setSignupBeginDate(gdPlan.getSignupBeginDate().replaceAll("-", ""));
    	if(!StringUtil.isEmpty(gdPlan.getSignupEndDate()))
    		gdPlan.setSignupEndDate(gdPlan.getSignupEndDate().replaceAll("-", ""));
    	if(!StringUtil.isEmpty(gdPlan.getGdBeginDate()))
    		gdPlan.setGdBeginDate(gdPlan.getGdBeginDate().replaceAll("-", ""));
    	if(!StringUtil.isEmpty(gdPlan.getGdEndDate()))
    		gdPlan.setGdEndDate(gdPlan.getGdEndDate().replaceAll("-", ""));
    	gdPlan.setGdState("1");
    	//控制报名日期区间
    	int days = Integer.parseInt(gdPlan.getSignupEndDate())-Integer.parseInt(gdPlan.getSignupBeginDate());
    	if(days>Constants.GD_DATE_RANGE){
    		throw new AppException("IMBK103",ConstantErr.errorMap.get("IMBK103").replace("#value#", Constants.GD_DATE_RANGE+""));
    	}
    	//拼装固定值
    	if (gdPlan.getGdId() != null) {
    		GdPlan gdPlanDB = gdPlanService.getGdPlan(gdPlan.getGdId());
    		gdPlanDB.setGdBeginDate(gdPlan.getGdBeginDate());
    		gdPlanDB.setGdEndDate(gdPlan.getGdEndDate());
    		gdPlanDB.setGdTitle(gdPlan.getGdTitle());
    		gdPlanDB.setSignupBeginDate(gdPlan.getSignupBeginDate());
    		gdPlanDB.setSignupEndDate(gdPlan.getSignupEndDate());
    		gdPlanDB.setSponsorUser(gdPlan.getSponsorUser());
    		gdPlanDB.setGdSlogan(gdPlan.getGdSlogan());
    		gdPlanDB.setGdInstr(gdPlan.getGdInstr());
    		gdPlan = gdPlanDB;
    		//修改
    		gdPlan.setUpdateTime(new Date());
    		gdPlan.setUpdateUserId(userId);
         } else {
        	 //新增 
        	 gdPlan.setCreateTime(new Date());
        	 gdPlan.setCreateUserId(userId);
         }
    	
    	//判断标签是否重复
    	List<GdPlan> list = gdPlanService.getGdPlansByTitle(gdPlan.getGdTitle(),gdPlan.getGdId());
    	//已经存在此时间段共读，报名时间段与其它共读冲突
    	if(null != list && list.size()>0){
    		//获取冲突的共读标题
    		String gdTiles ="";
    		for (GdPlan gdPlanTmp : list) {
    			gdTiles = gdTiles + "," + gdPlanTmp.getGdTitle();
			}
    		gdTiles = gdTiles.substring(1);
    		throw new AppException("IMBK100");
    	}
    	//判断報名时间段是否正确
    	List<GdPlan> list1 = gdPlanService.getGdPlansByBeginDate(gdPlan.getGdBeginDate(),gdPlan.getGdId());
    	//已经存在此时间段共读，报名时间段与其它共读冲突
    	if(null != list1 && list1.size()>0){
    		//获取冲突的共读标题
    		String gdTiles ="";
    		for (GdPlan gdPlanTmp : list1) {
    			gdTiles = gdTiles + "," + gdPlanTmp.getGdTitle();
			}
    		gdTiles = gdTiles.substring(1);
    		throw new AppException("IMBK102",ConstantErr.errorMap.get("IMBK102").replace("#value#", gdTiles));
    	}
    	//判断共读时间段是否正确
    	List<GdPlan> list2 = gdPlanService.getGdPlansBySingupDate(gdPlan.getSignupBeginDate(),gdPlan.getSignupEndDate(),gdPlan.getGdId());
    	//已经存在此时间段共读，报名时间段与其它共读冲突
    	if(null != list && list2.size()>0){
    		//获取冲突的共读标题
    		String gdTiles ="";
    		for (GdPlan gdPlanTmp : list2) {
    			gdTiles = gdTiles + "," + gdPlanTmp.getGdTitle();
			}
    		gdTiles = gdTiles.substring(1);
    		throw new AppException("IMBK101",ConstantErr.errorMap.get("IMBK101").replace("#value#", gdTiles));
    	}
    	return gdPlanService.saveOrUpdate(gdPlan);
    }
    
    /**
     * 查询单笔共读计划
     *@param gdID 共读编号
     *@return 公共返回信息
     *@throw:
     */
    @RequestMapping(value = "/getGdPlan/{gdId}")
    public GdPlan getGdPlan(@PathVariable("gdId") String gdId)throws AppException {
    	GdPlan gdPlan = gdPlanService.getGdPlan(Integer.parseInt(gdId));
    	gdPlan.setSignupBeginDate(DateUtil.formatDate(gdPlan.getSignupBeginDate()));
    	gdPlan.setSignupEndDate(DateUtil.formatDate(gdPlan.getSignupEndDate()));
    	gdPlan.setGdEndDate(DateUtil.formatDate(gdPlan.getGdEndDate()));
    	gdPlan.setGdBeginDate(DateUtil.formatDate(gdPlan.getGdBeginDate()));
    	
    	return gdPlan;
    }
    /**
     * 查询历史共读列表
     *@param year 年份
     *@return 公共返回信息
     *@throw:
     */
    @RequestMapping(value = "/history/{year}")
    public List<GdPlan> getGdPlansByYear(@PathVariable("year") String year)throws AppException {
    	 
        return gdPlanService.getGdPlansByYear(year);
    }
    
    /**
     * 删除共读计划
     *@param gdID 共读编号
     *@return 公共返回共读结束日期
     *@throw:
     */
    @RequestMapping(value = "/remove/{gdId}")
    public String gdplanRemove(@PathVariable("gdId") Integer gdId)throws AppException {
    	gdPlanService.deleteGdplan(gdId);
    	int chapterSum = 0;
    	List<GdBook> books = gdPlanService.getBooksByGdId(gdId);
    	if(null != books && books.size()>0){
    		for (GdBook book : books) {
    			chapterSum = chapterSum + book.getChapterSum();
			}
    	}
        //修改共读结束日期
        return	gdPlanService.endDateModify(chapterSum, gdId);
    }
    
    /**
     * 关联共读计划与图书信息关联
     *@param gdId 共读计划编号
     *@param books 图书信息集合
     *@param userId 用户编号
     *@return 结束日期
     */
    @RequestMapping(value = "/bookRel")
    public String planBookeRel(@PathVariable("gdId") Integer gdId,@RequestParam("books") String books ,@ModelAttribute(Constants.SESSION_USERID) String userId)throws AppException {
    	//获取关联图书数组
    	String[] booksArr = books.split(",");
    	int chapterSum = gdPlanService.planBookeRel(gdId , booksArr,userId);
    	 
    	//更新共读结束时间
    	return	gdPlanService.endDateModify(chapterSum, gdId);
    }
    
   
    @RequestMapping(value = "/delBookRel")
    public String planBookRelRemove(@RequestParam("gdId") Integer gdId,@RequestParam("bookId") Integer bookId)throws AppException {
    	//
    	gdPlanService.deletePlanBookRel(gdId,bookId);
    	return SUCCESS;
    }
    
}
