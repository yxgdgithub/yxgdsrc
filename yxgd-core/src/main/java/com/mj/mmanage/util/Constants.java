package com.mj.mmanage.util;

/**
 * <p> Description:系统常量类 </p>
 * <p> Copyright: Copyright (c) 2017 </p>
 * <p> Create Date: 2018-1-2</p>
 * <p> Company: YUSYS </p> 
 *@author:YX-LiAnDong
 *@version:Constants,v1.0 YX-LiAnDong
 */
public class Constants {
	
	 public final static String SESSION_USER = "USERINFO";//session用户信息
	 public final static String SESSION_USERID = "SESSION_USERID";//session用户编号
	 /**
	  * 上传文件类型
	  */
	 public final static String UPLOAD_FILE_TYPE_TXT = "01";//文本文件
	 public final static String UPLOAD_FILE_TYPE_VOICE = "02";//音频文件
	 public final static String UPLOAD_FILE_TYPE_VIDEO = "03";//视频文件
	 
	 public final static String GD_APPLY_CANCEL = "0"; // 取消报名
	 public final static String GD_APPLY = "1"; // 报名
	 
	 public final static String PERIOD_AM = "AM"; // 上午00:00-11:59:59
	 public final static String PERIOD_PM = "PM"; // 下午12:00-23:59:59
	 public final static String SIGN_YES = "签到成功！"; // 签到成功！
	 public final static String APPLY_YES = "报名成功！"; // 报名成功！
	 public final static String APPLY_YES_CANCEL = "取消报名成功！"; // 取消报名成功！
	 public final static String GD_PLAN_TYPE_NOW = "0"; // 共读计划状态为1：进行中
	 public final static String GD_PLAN_TYPE_FINISH= "1"; // 共读计划状态为2：已完成
	 public final static String GD_PLAN_TYPE_NEXT  = "2"; // 共读计划状态为0：下期
	 
		/**日期样式  yyyy-MM-dd*/
		public static final String DEFAULT_DATE_FORMAT_STR = "yyyy-MM-dd";
		/**日期样式  yyyyMMdd*/
		public static final String SHORT_DATE_FORMAT_STR = "yyyyMMdd";
		/**日期样式  yyyy/MM/dd*/
		public static final String DATE_S_FORMAT_STR = "yyyy/MM/dd";
		/**日期样式  yyyy/MM/dd*/
		public static final String TIME_S_FORMAT_STR = "yyyy/MM/dd HH:mm:ss";
		/**日期样式  yyyy-MM-dd HH:mm:ss*/
		public static final String DEFAULT_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
		/**日期样式  yyyy-MM-dd HH:mm:ss SSS*/
		public static final String FULL_FORMAT_STR = "yyyy-MM-dd HH:mm:ss SSS";
		/**日期样式  yyyyMMdd HH:mm:ss*/
		public static final String FULL_TIMES_FORMAT_STR = "yyyyMMdd HH:mm:ss";
		/**日期样式  yyMMdd*/
		public static final String DATE_SIX_FORMAT_STR="yyMMdd";
		public static final String DATE_FULL_FORMAT_STR="yyyyMMdd";
		/**日期样式  yyyyMMddHHmmss*/
		public static final String FULL_NO_FORMAT_STR="yyyyMMddHHmmss";
		/**日期样式  HH:mm:ss*/
		public static final String TIME_FORMAT_STR="HH:mm:ss";
		/**日期样式  HHmmss*/
		public static final String TIME_FORMATJ_STR="HHmmss";
		/**日期样式  HHmm*/
		public static final String TIME_FOUR_FORMATJ_STR="HHmm";
		
		public static final String SHORT_NO_FORMAT_STR="yyMMddHHmm";
		
	 /** 系统编码  UTF-8 */
	 public static final String CODE_TYPE = "UTF-8";
	 
	 public static final String SUCCESS = "AAAAAAA"; //默认成功返回话述
	 
	 public static final String WECHAT_ID = "WECHAT_ID"; //微信客户操作信息id临时存储 
	 
	 public static final String WEB_PROJECT_PATH = "/Users/LiAnDong/Desktop/project/gongdu/yxsubs/";
			 
	 //图书上传路径
	 public static final String BOOK_IMG_PATH = "/uploadfile/bookimg/";
	 //文本上传路径
	 public static final String BOOK_TXT_PATH = "/uploadfile/booktxt/";
	 //音频上传路径
	 public static final String BOOK_VOICE_PATH = "/uploadfile/bookvoice/";
	 //视频上传路径
	 public static final String BOOK_DIDEO_PATH = "/uploadfile/bookvoice/";
	 
	 public final static int GD_DATE_RANGE = 100; // 报名区间限制天数	 
	 
	 public final static int GD_DAY_EVERY_WEEK = 5; // 每周那几天是共读日期周一至周五	
	 
	 public final static String WEBCHAT_TOKEN = "yxgd";
	 
	 // 获取code的请求地址,授权使用
	 public static String Get_Code 
	 	= "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=STAT#wechat_redirect";

	 // 获取Web_access_token https的请求地址,授权使用
	 public static String Web_access_tokenhttps 
	 	= "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code"; 
	
	 // 拉取用户信息的请求地址,授权使用
	 public static String User_Message 
	 	= "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

	 // 获取ACCESS_TOKEN_URL,分享使用
	 public static String ACCESS_TOKEN_URL 
	 	= "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + Constants.APPID + "&secret=" +  Constants.SECRET;
	 
	 // 获取TICKET_URL,分享使用
	 public static String TICKET_URL 
	 	= "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
	 
	 public final static String REDIRECT_URI = "http://127.0.0.1";
//	 public final static String REDIRECT_URI = "http://yxgd.yusys.com.cn";

     public final static String APPID = "wxb89606f58305f38d";
     public final static String SECRET = "9cf587bf826a8c75cebaceb0f4c0e063";
     public final static String WEBCHAT_GRANT_SCOPE = "snsapi_userinfo";	
     
     // 每日定时推送使用的信息
     public final static String PUSH_TEMPLAT_ID = "qi8tgvQUveG4h5Bzpn2eH7iGFW3T8Z6EnW";
     public final static String PUSH_TEMPLAT_CLICKURL = "qi8tgvQUveG4h5Bzpn2eH7iGFW3T8Z6EnW";
     public final static String PUSH_TEMPLAT_TOPCOLOR = "#173177";
     
     public final static String TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
     
}

