/**
 *@Title：GdPlanService.java
 *@Package:com.mj.mmanage.service
 */
package com.mj.mmanage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.mj.mmanage.core.AppException;
import com.mj.mmanage.mapper.GdBookMapper;
import com.mj.mmanage.mapper.GdPlanBookRelMapper;
import com.mj.mmanage.mapper.GdPlanMapper;
import com.mj.mmanage.model.GdBook;
import com.mj.mmanage.model.GdPlan;
import com.mj.mmanage.model.GdPlanBookRel;
import com.mj.mmanage.util.ConstantErr;
import com.mj.mmanage.util.Constants;
import com.mj.mmanage.util.DateUtil;
import com.mj.mmanage.util.StringUtil;

/**
 * <p> Description:计划读维护业务逻辑类 </p>
 * <p> Copyright: Copyright (c) 2017 </p>
 * <p> Create Date: 2017-12-27</p>
 * <p> Company: YUSYS </p> 
 *@author:YX-LiAnDong
 *@version:GdPlanService,v1.0 YX-LiAnDong 
 */
@Service
public class GdPlanService {

	@Autowired
    private GdPlanMapper gdPlanMapper;
	
	@Autowired
    private GdPlanBookRelMapper gdPlanBookRelMapper;
	
	@Autowired
    private GdBookMapper gdBookMapper;
	/**
     * 新增或修改共读计划
     *@param gdBook 图书信息
     *@throw:
     */
    public String saveOrUpdate(GdPlan gdPlan) {
	    if (gdPlan.getGdId() != null) {
	    	gdPlanMapper.updateByPrimaryKey(gdPlan);
	     } else {
	    	 gdPlanMapper.insert(gdPlan);
	     }
	    return gdPlan.getGdId().toString();
	 }
    /**
     * 判断此报名区间段是否有共读
     *@param year
     *@return
     *@throw:
     */
    public List<GdPlan> getGdPlansByTitle(String title,Integer id){
    	Example example = new Example(GdPlan.class);
    	example.createCriteria().andEqualTo("gdTitle", title.trim()).andNotEqualTo("gdId", id);
    	 
    	return gdPlanMapper.selectByExample(example);
    }
    /**
     * 判断此报名区间段是否有共读
     *@param year
     *@return
     *@throw:
     */
    public List<GdPlan> getGdPlansBySingupDate(String beginDate,String endDate,Integer id){
    	Example example = new Example(GdPlan.class);
    	example.createCriteria().andBetween("signupBeginDate", beginDate, endDate);
    	example.or().andBetween("signupEndDate", beginDate, endDate).andNotEqualTo("gdId", id);
    	return gdPlanMapper.selectByExample(example);
    }
    
    /**
     * 判断此共读阅读时间段是否有日期冲突
     *@param year
     *@return
     *@throw:
     */
    public List<GdPlan> getGdPlansByBeginDate(String beginDate,Integer id){
    	Example example = new Example(GdPlan.class);
    	example.createCriteria().andGreaterThan("gdEndDate", beginDate).andLessThan("gdBeginDate", beginDate).andNotEqualTo("gdId", id);
    	return gdPlanMapper.selectByExample(example);
    }
    /**
     * 查询单笔共读计划
     *@param gdID 共读编号
     *@return 公共返回信息
     *@throw:
     */
    public GdPlan getGdPlan(Integer gdId)throws AppException {
    	//查询共读计划信息
    	GdPlan gdPlan = gdPlanMapper.selectByPrimaryKey(gdId);
    	//查询共读计划关联的图书信息
    	if(null != gdPlan){
	    	List<GdBook> gdBook = gdPlanBookRelMapper.getBooksByGdId(gdId);
	    	gdPlan.setGdBook(gdBook);
    	}
    	
    	//查询报名数和签到情况信息
    	GdPlan gdPlanSum = gdPlanMapper.getApplyNumAndSignNum(gdId);
    	//查询报名数
    	if(!StringUtil.isEmpty(gdPlanSum.getApplyNum())){
    		gdPlan.setApplyNum(gdPlan.getApplyNum());
    	}
    	//查询当日签到数
    	if(!StringUtil.isEmpty(gdPlanSum.getSignNum())){
    		gdPlan.setSignNum(gdPlan.getSignNum());
    	}
    	return gdPlan;
    }
    /**
     * 查询历史共读列表
     *@param year
     *@return
     *@throw:
     */
    public List<GdPlan> getGdPlansByYear(String year){
    	Example example = new Example(GdPlan.class);
    	example.createCriteria().andBetween("gdBeginDate",year+"0101", year+"1231");
    	return gdPlanMapper.selectByExample(example);
    }
    
    /**
     * 删除共读计划信息
     *@param gdId 共读ID
     *@throw:
     */
    public void deleteGdplan( Integer gdId){
    	
    	//删除共读图书关联关系
    	Example example = new Example(GdPlanBookRel.class);
    	example.createCriteria().andEqualTo("gdId",gdId);
    	gdPlanBookRelMapper.deleteByExample( example);
    	
    	//删除共读计划
    	gdPlanMapper.deleteByPrimaryKey(gdId);
    	
    }
    
    /**
     * 关联共读计划和图书
     *@param gdId 共读计划编号
     *@param books 图书编号数组集合
     *@throw:
     */
    public int planBookeRel(Integer gdId,String[] books,String userId){
    	if(books == null || books.length<1){
    		new AppException(ConstantErr.errorMap.get("IMBK002"));
    	}
    	GdPlanBookRel gdPlanBookRel= null;
    	//章节数
    	int chapterSum = 0;
    	for (int i = 0; i < books.length; i++) {
    		//
    		GdBook gdBook = gdBookMapper.selectByPrimaryKey(books[i]);
    		chapterSum = chapterSum + (gdBook.getChapterSum()==null?0:gdBook.getChapterSum());
    		gdPlanBookRel = new GdPlanBookRel();
    		gdPlanBookRel.setGdId(gdId);
    		gdPlanBookRel.setCreateUserId(userId);
    		gdPlanBookRel.setBookId(Integer.parseInt(books[i]));
    		gdPlanBookRelMapper.insert(gdPlanBookRel);
		}
    	return chapterSum;
    }
    
    /**
     * 查询单笔共读计划
     *@param gdID 共读编号
     *@return 公共返回信息
     *@throw:
     */
    public List<GdBook> getBooksByGdId(Integer gdId)throws AppException {
    	//查询共读计划信息
    	return gdPlanBookRelMapper.getBooksByGdId(gdId);
    }
    
    /**
     * 删除共读计划和图书的关联关系
     *@param gdId 共读ID
     *@throw:
     */
    public void deletePlanBookRel( Integer gdId,Integer bookId){
    	
    	//删除共读图书关联关系
    	Example example = new Example(GdPlanBookRel.class);
    	example.createCriteria().andEqualTo("gdId",gdId).andEqualTo("bookId",bookId);
    	gdPlanBookRelMapper.deleteByExample( example);
    	
    }
    
    /**
     * 修改共读结束日期
     *@param summary 图书说明
     *@param bootId  图书编号
     *@throw:
     */
    public String endDateModify(int chapterSum , Integer gdId) {
    	
    	GdPlan gdPlan = gdPlanMapper.selectByPrimaryKey(gdId);
    	//计算共读结束日期
    	String begin = gdPlan.getGdBeginDate();
    	String  endDate = "";
    	if(chapterSum>0){
			if(Constants.GD_DAY_EVERY_WEEK==5){
				endDate = DateUtil.addDayNoWeek(begin, chapterSum);
			}else{
				endDate = DateUtil.addDateNormal(begin, chapterSum);
			}
    	}
		gdPlan.setGdEndDate(endDate);
    	gdPlanMapper.updateByPrimaryKey(gdPlan);
    	return endDate;
	    
	 }
    
}
