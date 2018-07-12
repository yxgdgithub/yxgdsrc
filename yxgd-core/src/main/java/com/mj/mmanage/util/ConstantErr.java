package com.mj.mmanage.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 错误码常量
 * @author 郭保利
 *
 */
public class ConstantErr {
	public static Map<String,String> errorMap = new HashMap<String,String>();
	static{
		errorMap.put("-999", "数据库操作异常");
		errorMap.put("000", "测试异常");
		errorMap.put("IMBK001", "文件上传失败");
		errorMap.put("IMBK002", "图书图书编号不能为空");
		errorMap.put("IMBK003", "用户名或密码不能为空！");
		errorMap.put("IMBK004", "用户名或密码错误！");
		errorMap.put("IMBK005", "原始密码输入错误!");
		errorMap.put("IMBK006", "用户会话过期，请重新登录!");
		errorMap.put("IMBK1001", "保存失败！共读名称已经存在！");
		errorMap.put("IMBK100", "保存失败！共读标题已经存在！");
		errorMap.put("IMBK101", "保存失败！本期共读报名起始日期与【#value#】冲突！");
		errorMap.put("IMBK102", "保存失败！本期共读起始日期与【#value#】冲突！");
		errorMap.put("IMBK103", "保存失败！共读报名日期或共读日期区间大于【#value#】天。");
		
		errorMap.put("WXAPPLY001", "您未报名本期共读！");
		errorMap.put("WXAPPLY002", "您还未报名过宇信共读！");
		errorMap.put("SIGN_NO", "签到失败！");
		errorMap.put("SUBS001", "已签到！");
		
	}
	
	public static final String E0001 = "E0001"; // 
	public static final String NO_APPLY = "WXAPPLY001"; // 
	public static final String NO_APPLY_ALWAYS = "WXAPPLY002"; // 
	
}
