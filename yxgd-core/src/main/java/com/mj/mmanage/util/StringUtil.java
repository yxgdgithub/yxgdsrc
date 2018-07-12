/**
 *@Title：StringUtil.java
 *@Package:com.citic.portal.core.util
 *@version:version V1.0
 */
package com.mj.mmanage.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * <p>
 * Description:字符串工具类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015
 * </p>
 * <p>
 * Create Date: 2015-12-7
 * </p>
 * <p>
 * </p>
 * 
 * @author:YX-LiAnDong
 * @version:version V1.0
 */
public class StringUtil {
	
	
	/**
	 * 字符串的处理，如果是null则返回"",否则除空格返回
	 * 
	 * @param str
	 *            待处理字符串
	 * @return 结果字符串
	 */
	public static String trim(String str) {
		if (isEmpty(str)) {
			return "";
		} else {
			return str.trim();
		}
	}

	/**
	 * 去掉左侧空格
	 * 
	 * @param value
	 *            待处理字符串
	 * @return 结果字符串
	 */
	public static String trimLeft(String value) {
		if (value == null)
			return "";
		String result = value;
		char ch[] = result.toCharArray();
		int index = -1;
		for (int i = 0; i < ch.length; i++) {
			if (Character.isWhitespace(ch[i])) {
				index = i;
			} else {
				break;
			}
		}
		if (index != -1) {
			result = result.substring(index + 1);
		}
		return result;
	}

	/**
	 * 去掉右侧空格
	 * 
	 * @param value
	 *            待处理字符串
	 * @return 结果字符串
	 */
	public static String trimRight(String value) {
		if (value == null)
			return "";
		String result = value;
		char ch[] = result.toCharArray();
		int endIndex = -1;
		for (int i = ch.length - 1; i > -1; i--) {
			if (Character.isWhitespace(ch[i])) {
				endIndex = i;
			} else {
				break;
			}
		}
		if (endIndex != -1) {
			result = result.substring(0, endIndex);
		}
		return result;
	}

	/**
	 * 判断字符串是否为空或者null(压缩空格后)
	 * 
	 * @param strObj
	 *            待处理的字符串
	 * @return 空，null：true; 不空：false
	 */
	public static boolean isEmpty(Object strObj) {
		if (strObj == null || strObj.toString().trim().equals("null")
				|| strObj.toString().trim().length() < 1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断一个字符串是否为空值（null或者(压缩空格后)）
	 *@param str 待判断的字符串
	 *@return true：空，false：非空
	 */
    public static boolean isStrEmpty(String str) {
        if ((str == null) || (str.trim().length() < 1)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 字符串分隔
     *@param src 待分隔的字符串
     *@param spit 分隔字符
     *@return 分隔后的字符串数组
     */
    public static String[] strSplit(String src, String spit) {
		Object obj = null;
		int index = 0;
		Vector vector = new Vector();
		String as[] = new String[1];

		if (src == null)
			return new String[0];
		if (spit == null)
			return null;
		if (src.trim().equals(""))
			return new String[0];

		index = src.indexOf(spit);
		String tem = "";
		while (index != -1) {
			if (index != 0 && src.substring(index - 1, index).equals("\\")) {
				tem = tem + src.substring(0, index - 1) + spit;
				src = src.substring(index + 1);
				index = src.indexOf(spit);
			} else {
				String tem2 = tem.equals("") ? src.substring(0, index).trim()
						: tem + src.substring(0, index).trim();
				src = src.substring(index + 1);
				vector.addElement(tem2);
				tem = "";
				index = src.indexOf(spit);
			}//end if
		}//end while

		vector.addElement(src.trim());
		as = new String[vector.size()];
		for (int j = 0; j < vector.size(); j++)
			as[j] = (String) vector.elementAt(j);

		return as;
	}
    
    /**
	 * 隐藏字符窜中间的内容用*代替
	 * @param str 待处理字符串
	 * @param start 起始位置
	 * @param end 结束位置
	 * @return 处理后结果字符串
	 */
	public static String decorateStr(String str, int start, int end) {

		StringBuffer str_word = new StringBuffer();
		if (str != null) {
			int length = str.length();
			if (length > start + end) {
				str_word.append(str.substring(0, start));
				String middle = str.substring(start, length - end);

				for (int i = 0; i < middle.length(); i++) {
					str_word.append("*");
				}
				str_word.append(str.substring(length - end, length));
			} else {
				str_word = str_word.append(str);
			}
		}

		return str_word.toString();
	}
	
	/**
     * 根据传入format格式格式化BigDecimal并返回String
     * @param number 带出李数据
     * @param format 处理后格式
     * @return 处理后数据
     */
    public static String formatBigDecimal(BigDecimal number,String format){
		DecimalFormat df=new DecimalFormat(format);
		return df.format(number);
    }
    
 
    /**
     * 过滤特殊字符
     * @param str 待处理字符串
     * @return 处理后结果字符串
     */
    public static  String cleanChars(String str) {
    	if(!StringUtil.isEmpty(str)) {
    		str = str.replaceAll("[\\x07]", "·");
    		str = str.replaceAll("[\\x00-\\x06\\x08\\x0b-\\x0c\\x0e-\\x1f\\x7f]", "");
    	}
		return str;
	}
    
    /**
     * 判断是否存在特殊字符
     * @param str 待处理字符串
     * @return true :没有特殊字符 false:有特殊字符
     */
    public static boolean checkContainSpeChar(String str){
    	if(null == str){
    		return false;
    	}
		String str1  = str.replaceAll("[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]", "");
		if(str1.equals(str)){
			return true;
		}
		return false;
	}
    
    /**
	 * 验证邮箱格式
	 * @param email
	 * @return
	 */
	public static boolean isEmailFormat(String email) {
		Pattern p = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
		if (email==null) {
			return false;
		} else {
			Matcher m = p.matcher(email);
			return m.find();
		}
		
	}
	
	/**
	 * 验证手机格式
	 * @param phone
	 * @return
	 */
	public static boolean isMobileFormat(String phone) {
		Pattern p = Pattern.compile("^1[0-9]{10}$");
		if (phone==null) {
			return false;
		} else {
			Matcher m = p.matcher(phone);
			return m.find();
		}
	}
	
	/**
	 * 验证是否是全数字
	 * @param number
	 * @return
	 */
	public static boolean isNumber(String number) {
		Pattern p = Pattern.compile("^\\d+$");
		if (number==null) {
			return false;
		} else {
			Matcher m = p.matcher(number);
			return m.find();
		}
	}
	

	/**
	 * 前补空格
	 */
	public static String fillspace(String num,int ws){
    	String formatstr = num;
    	for(int i =num.length();i<ws;i++){
    		formatstr=formatstr+" ";
    	}
    	return formatstr;
    }
	/**
	 * 前补0
	 */
	public static String fillzero(String num,int ws){
    	String formatstr = num;
    	for(int i =num.length();i<ws;i++){
    		formatstr="0"+formatstr;
    	}
    	return formatstr;
    }

	
    /**
     * 随机数
     * @param size
     * @return
     */
    public static String getRandomNO(int size){
    	
    	String verifyChars="0123456789abcdefghijklmnopqrstuvwxyz";		
    	char CHARACTERS[] = verifyChars.toCharArray();	
    	int C_SIZE = CHARACTERS.length;
    	
    	Random random = new Random();
    	
		//取随机产生的验证码
		String randomNo="";
		for (int i=0; i<size; i++){
			char c	= CHARACTERS[random.nextInt(C_SIZE)];
			randomNo += c;
		}
		
		return  randomNo;
    }
    
  	/**
  	 * 
  	 * 获取用户机器IP地址
  	 *@param request
  	 *@return
  	 *@throw:
  	 */
  	public static String getIpDddr (HttpServletRequest request){
  		String ip = request.getHeader("X-Forwarded-For");
  		if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
  			ip = request.getHeader("Proxy-Client-IP");
  		}
  		if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
  			ip = request.getHeader("WL-Proxy-Client-IP");
  		}
  		if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
  			ip = request.getHeader("http_Client_IP");
  		}
  		if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
  			ip = request.getHeader("HTTP_X_Forwarded-For");
  		}
  		if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
  			ip = request.getRemoteAddr();
  		}
  		return ip;
  	}
    
	/**
	 * 
	 * 校验输入金额是否合法
	 *@param tranAmt
	 *@return
	 *@throw:
	 */
	public static boolean isMoney(String tranAmt){
		boolean b = false;
		if(!isEmpty(tranAmt)){
			String regx = "^[0-9]{1,13}.[0-9]{2}$";
			String regx1 = "^[0]{1,13}.[0]{2}$";
			
			Pattern pattern = Pattern.compile(regx);
			Matcher match = pattern.matcher(tranAmt);
			
			Pattern pattern1 = Pattern.compile(regx1);
			Matcher match1 = pattern1.matcher(tranAmt);
			
			if(match.matches() && !match1.matches()) {
				b = true;
			}
		}
		return b;
		
	}
  	
}
