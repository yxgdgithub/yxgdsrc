/**
 *@Title：DateUtili.java
 *@Package:com.citic.portal.core.util
 *@version:version V1.0
 */
package com.mj.mmanage.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.mj.mmanage.core.AppException;

/**
 * <p>
 * Description:日期工具类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015
 * </p> 
 * <p>
 * Create Date: 2015-12-7
 * </p>
 * <p>
 * Company: CITIC BANK
 * </p>
 * 
 * @author:YX-LiAnDong
 * @version:version V1.0
 */
public class DateUtil {

	/**
	 * 默认yyyy-MM-dd HH:mm:ss格式输入当前时间
	 * 
	 * @return String
	 */
	public static String now() {
		return now(Constants.DEFAULT_FORMAT_STR);
	}
	
	/**
	 * 给定格式输入当前时间
	 * 
	 * @param formatStr
	 * @return String
	 */
	public static String now(String formatStr) {
		DateFormat format = getFormat(formatStr);
		return format.format(new Date());
	}
	/**
	 * 两个日期之间相隔天数
	 * 
	 * @param startDay,endDay
	 * @return Integer
	 * @throws ParseException 
	 */
	public static int betwDays(String startDay,String endDay) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date start =format.parse(startDay);
		Date end = format.parse(endDay);
		int betDay = (int)((start.getTime()-end.getTime())/(1000*3600*24));
		return betDay;
	}

	/**
	 * 生成一个TimeStamp
	 * 
	 * @return
	 * @throws Exception
	 */
	static public Timestamp getTimeStamp() throws Exception {
		return new java.sql.Timestamp(System.currentTimeMillis());

	}

	/**
	 * 将日期字符串转换成Date
	 * 
	 * @param str
	 *            1
	 * @param formatStr
	 *            日期字符串格式 yyyyMMddhhmmss/yyyyMMdd
	 * @return 日期 str为空返回null
	 */
	public static Date parseDateStr(String str, String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		try {
			return format.parse(str);
		} catch (ParseException e) {

		}
		return null;
	}

	/**
	 * 将日期格式化
	 * 
	 * @param date
	 *            日期
	 * @param formatStr
	 *            格式化字符串
	 * @return
	 * @throw:
	 */
	public static String getDateString(Date date, String formatStr) {
		return new SimpleDateFormat(formatStr).format(date);
	}

	/**
	 * 将14位的字符串转换成Timestamp
	 * 
	 * @param str
	 *            14位日期字符串 yyyyMMddhhmmss 20130923100210
	 * @return 日期 str为空返回null
	 */
	public static Timestamp strToTimestamp(String str, String formatStr) {
		Date d = DateUtil.parseDateStr(str, formatStr);
		Timestamp temdate = new Timestamp(d.getTime());
		return temdate;
	}

	/**
	 * 取得当前以后的某些小时的时间
	 * 
	 * @param count
	 *            几天之后
	 * @return 日期
	 */
	public static Timestamp afterHourOfCurrentHour(int count) {

		// 得到当前时间
		Calendar dt = Calendar.getInstance();
		Timestamp temdate = new Timestamp(dt.getTimeInMillis());
		// 取得当前以后的某些小时的时间
		temdate.setHours(temdate.getHours() + count);
		return temdate;
	}
	
	/**
	 * 取得当前时间以前的某些小时的时间
	 * 
	 * @param count
	 *            几小时以前
	 * @return 日期
	 */
	public static Timestamp beforeHourOfCurrentHour(int count) {

		// 得到当前时间
		Calendar dt = Calendar.getInstance();
		Timestamp temdate = new Timestamp(dt.getTimeInMillis());
		// 取得当前以后的某些小时的时间
		temdate.setHours(temdate.getHours() - count);
		return temdate;
	}

	/**
	 * 取得当前以后的某些分钟的时间
	 * 
	 * @param count
	 *            几天之后
	 * @return 日期
	 */
	public static Timestamp afterMinutesOfCurrentMinutes(int count) {

		// 得到当前时间
		Calendar dt = Calendar.getInstance();
		Timestamp temdate = new Timestamp(dt.getTimeInMillis());
		// 取得当前以后的某些小时的时间
		temdate.setMinutes(temdate.getMinutes() + count);
		return temdate;
	}
	
	/**
	 * 计算年龄
	 * 
	 * @param birthDay
	 *            出生日期
	 * @param dateNow
	 *            截至计算的日期
	 * @return
	 * @throw:
	 */
	public static int getAge(Date birthDay, Date dateNow) {

		Calendar now = Calendar.getInstance();
		now.setTime(dateNow);

		int yearNow = now.get(Calendar.YEAR);
		int monthNow = now.get(Calendar.MONTH) + 1;
		int dayNow = now.get(Calendar.DAY_OF_MONTH);
		Calendar day = Calendar.getInstance();
		day.setTime(birthDay);
		int yearBirth = day.get(Calendar.YEAR);
		int monthBirth = day.get(Calendar.MONTH) + 1;
		int dayBirth = day.get(Calendar.DAY_OF_MONTH);
		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayNow < dayBirth) {
					age--;
				}
			} else {
				age--;
			}
		}
		return age;
	}

	/**
	 * 日志转换成字符串 
	 *@param date 待处理日期
	 *@return 格式后的日期结果字符
	 */
	public static String getDateTime12Str(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String datestr = format.format(date);
		SimpleDateFormat formatTime = new SimpleDateFormat("HHmmss");
		String timestr = formatTime.format(date);
		String datetimestr = datestr + timestr;
		return datetimestr.substring(2);
	}
	/**
	 * 日志转换成字符串 
	 *@param date 待处理日期
	 *@return 格式后的日期结果字符
	 */
	public static String getDateTime8Str(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String datestr = format.format(date);
		return datestr.substring(2);
	}
	
	/**
	 * 计算日期  起始日期+天数
	 *@param begin 起始日期
	 *@param count 天数
	 *@return
	 */
	public static String addDateNormal(String begin , int count) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date beginDate = null;
		try {
			beginDate = sdf.parse(begin);
			Calendar dt = Calendar.getInstance();
	        dt.setTime(beginDate);
	        dt.add(Calendar.DAY_OF_YEAR, count);
	        beginDate = dt.getTime(); 
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return  sdf.format(beginDate);
	}
	/**
	 * 计算日期  起始日期+天数（周六、周日不算）
	 *@param begin 起始日期
	 *@param count 天数
	 *@return
	 */
	public static String addDayNoWeek(String begin , int count) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar dt = Calendar.getInstance();
		Date beginDate;
		try {
			beginDate = sdf.parse(begin);
			dt.setTime(beginDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int mod = count%5;
		int other = count/5*7;
		for (int i = 0; i < mod;) {
			dt.add(Calendar.DAY_OF_WEEK,1);
			switch (dt.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SUNDAY:
			case Calendar.SATURDAY:
			break;	
			default:
				i++;
				break;
			}
		}
		if(other>0){
			dt.add(Calendar.DATE, other);
		}
		return sdf.format(dt.getTime());
	}
	/**
	 * 取得今天以后的某一天
	 * 
	 * @param count
	 *            几天之后
	 * @return 日期
	 */
	public static Timestamp afterDaysOfToday(int count) {
		Calendar dt = Calendar.getInstance();
		dt.add(Calendar.DAY_OF_YEAR, count);
		Timestamp temdate = new Timestamp(dt.getTimeInMillis());
		return temdate;
	}
	/**
	 * 
	 * 比较结束日期(如20151211) 与  开始日期(如20151111) + 时间间隔(天数)的大小
	 *@param endTimeStr 结束时间
	 *@param startTimeSr 开始时间
	 *@param DayCount 时间间隔-天数
	 *@return boolean
	 *@throws ParseException
	 *@throw:
	 */
	public static boolean endCompareToStart(String endDateStr ,String startDateStr,int count) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date endDate = sdf.parse(endDateStr);
		Date startDate = sdf.parse(startDateStr);
		
        Calendar dt = Calendar.getInstance();
        dt.setTime(startDate);
        dt.add(Calendar.DAY_OF_YEAR, count);
        startDate = dt.getTime(); 
        boolean b = endDate.before(startDate);
        return b;
    }
	/**
	 * 
	 * 比较结束日期(如20151211) 与  开始日期(如20151112) + 时间间隔(月份)的大小
	 *@param endDateStr 结束时间
	 *@param startDateStr 开始时间
	 *@param count  时间间隔-月份
	 *@return
	 *@throws ParseException
	 *@throw:
	 */
	public static boolean endCompareToStartByMonth(String endDateStr ,String startDateStr,int count) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date endDate = sdf.parse(endDateStr);
		Date startDate = sdf.parse(startDateStr);
		
        Calendar dt = Calendar.getInstance();
        dt.setTime(startDate);
        dt.add(Calendar.MONDAY, count);
        startDate = dt.getTime(); 
        boolean b = endDate.before(startDate);
        return b;
    }
	/**
	 * 
	 * 比较结束日期 和开始日期  在月份中的号是否相同
	 *@param endDateStr
	 *@param startDateStr
	 *@return
	 *@throws ParseException
	 *@throw:
	 */
	public static boolean compareToDay(String endDateStr ,String startDateStr) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date endDate = sdf.parse(endDateStr);
		Date startDate = sdf.parse(startDateStr);
		int endTimeDay = endDate.getDate();
		int startTimeDay = startDate.getDate();
        boolean b = (endTimeDay == startTimeDay);
        return b;
    }
	/**
	 * 
	 * 比较结束日期与开始日期的周几是否相等
	 *@param endDateStr
	 *@param startDateStr
	 *@return
	 *@throws ParseException
	 *@throw:
	 */
	public static boolean compareToWeek(String endDateStr ,String startDateStr) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date endDate = sdf.parse(endDateStr);
		Date startDate = sdf.parse(startDateStr);
		Calendar dt = Calendar.getInstance();
	    dt.setTime(startDate);
	    int startInWeek = dt.get(Calendar.DAY_OF_WEEK);
	    dt.setTime(endDate);
	    int endInWeek =  dt.get(Calendar.DAY_OF_WEEK);
        boolean b = (endInWeek== startInWeek);
        return b;
    }
	
	/**
	 * 字符串转换成日期格式化
	 *@param s 待转换字符串
	 *@return 日志对象
	 *@throws ParseException 应用异常
	 */
	public static  Date parseDateTime(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.parse(s);
	}
	/**
     * 格式化当前日期时间为12位的字符串，格式为：yyyyMMddHHmmss
     * @return 格式后的时间字符串
     */
    static public String getTodayDate12Str(){
    	Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String datestr = format.format(date);
		SimpleDateFormat formatTime = new SimpleDateFormat("HHmmss");
		String timestr = formatTime.format(date);
		String datetimestr = datestr + timestr;
		return datetimestr;
	}
    /**
     * 获取带格式的当前系统时间
     *@param format 日期格式
     *@return
     *@throw:
     */
    public static String getCurrentDate2Str(String format){
    	Date date = new Date();
    	String dateString = getDateString(date,format);
    	return dateString;
    }
	protected static final ThreadLocal<Map<String, DateFormat>> formats = new ThreadLocal<Map<String, DateFormat>>();

    protected static DateFormat getFormat(String formatStr) {
		if (formats.get() == null) {
			formats.set(new HashMap<String, DateFormat>());
		}
		Map<String, DateFormat> temp = formats.get();
		if (temp.get(formatStr) == null) {
			DateFormat f = new SimpleDateFormat(formatStr);
			temp.put(formatStr, f);
		}
		return temp.get(formatStr);
	}
    public static String dateWeekOpt(Date date, int day, String formatStr){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(5, day);
		DateFormat format = getFormat(formatStr);
		return format.format(gc.getTime());
	
	}
	
	/**
     * 取得某天数月以前的某一天
     * @param count 几月之后或之前
     * @return 日期
     */
    public static Date beforeMonthsOfDate(Date temdate,int count){
    	if(count<0) return temdate;
    	
        Calendar dt = Calendar.getInstance();
        dt.setTime(temdate);
        dt.add(Calendar.MONTH, -count);
        temdate = dt.getTime(); 
        return temdate;
    }
	/**
	 * @see 将8位日期格式化成10位日期 如:20080112--->2008-01-12
	 * @param valueStr
	 *            要处理的日期
	 * @return 处理后的日期
	 * @throws AppException
	 */
	public static String formatDate8ToDate10(String valueStr) {

		if (valueStr == null || (valueStr.trim()).equals(""))
			return valueStr;
		if ("00000000".equals(valueStr))
			return "";

		if (valueStr.matches("[0-9]{8}")) {
			String newValue = valueStr.substring(0, 4) + "-"
					+ valueStr.substring(4, 6) + "-" + valueStr.substring(6);
			return newValue;
		} else {
			return valueStr;
		}
	}
	/**
	 * 字符串转换成日期格式化
	 *@param s 待转换字符串
	 *@return 日志对象
	 *@throws ParseException 应用异常
	 */
	public static  String parseDateTimes(String s) throws ParseException {
		if(StringUtil.isEmpty(s)){
			return s;
		}
		SimpleDateFormat sdf1 = null;
		if(s.length() == 17){
			sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		}else if(s.length() == 14){
			sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		}else{
			return s;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long times = sdf1.parse(s).getTime();
		Calendar cl = Calendar.getInstance();
		cl.setTimeInMillis(times);
		return format.format(cl.getTime());
		
	}
	/**
	 * 日期字符串由格式1转换为格式2
	 *@param s
	 *@param format1 日期格式1
	 *@param format2 日期格式2
	 *@return 转换后的日期字符串
	 *@throws ParseException
	 */
	public static String formatDateStr(String s,String format1,String format2) throws ParseException {
		SimpleDateFormat sdf1 = new SimpleDateFormat(format1);
		SimpleDateFormat sdf2 = new SimpleDateFormat(format2);
		Date date = sdf1.parse(s);
		String dateStr = sdf2.format(date);
		return dateStr;
	}
	
	/**
	 * 返回当前的时间段是AM还是PM,其中AM-0:00-11:59:59; PM:12:00-23:59:59
	 * @return AM:上午;PM:下午
	 * @throws Exception
	 */
	public static String getCurrentPeriod() {
		GregorianCalendar ca = new GregorianCalendar(TimeZone.getTimeZone("GMT+8:00"));
		int time = ca.get(GregorianCalendar.AM_PM);
		if(time == 0){
			return Constants.PERIOD_AM;
		}else{
			return Constants.PERIOD_PM;
		}
	}

	/**
	 * 获取上月的第一天和最后一天
	 * @return
	 */
	public static String getUpMonthDays(){
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);//上个月月份
		String days = getFirstAndLastDay(month);
		return days;
	}
	
	/**
	 * 获取下个月的第一天和最后一天
	 * @return
	 */
	public static String getNextMonthDays(){
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH)+2;//上个月月份
		String days = getFirstAndLastDay(month);
		return days;
	}
	/**
	 * @param int month
	 * @return 根据所传月份获取上个月或下个月的第一天和最后一天
	 */
	public static String getFirstAndLastDay(int month){
		Calendar calendar = Calendar.getInstance();
		int year = 0;
		//设置年月
		if(month <= 0){
			year = calendar.get(Calendar.YEAR)-1;
			month = 12;
		}else if(month >= 12){
			year = calendar.get(Calendar.YEAR)+1;
			month = 1;
		}else{
			year = calendar.get(Calendar.YEAR);
		}
		//设置天数
		String temp = year +"-"+month ;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = null;
		try {
			date1 = format.parse(temp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.setTime(date1);
		int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);//获取最后一天
		int startDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);//获取第一天
		String firstDay = year +"-"+month+"-"+startDay;
		String endDay = year +"-"+month+"-"+lastDay;
		String firstDay1 = "";
		String endDay1 = "";
		try {
			Date dat = format1.parse(firstDay);
			firstDay1 = format1.format(dat);
			Date dat1 = format1.parse(endDay);
			endDay1 = format1.format(dat1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String firstDay2 = firstDay1.replaceAll("-", "");
		String endDay2 = endDay1.replaceAll("-", "");
		String day = firstDay2+";"+endDay2;
		return day;
	}
	/**
	 * 获取昨天的日期（返回yyyyMMdd）
	 * @return  yyyyMMdd
	 * @throws ParseException 
	 */
	public static String getYesterdayString() throws ParseException{
		int count = -1;
		String temdate = afterDaysOfToday(count).toString();
		String dateStr = formatDateStr(temdate, "yyyy-MM-dd HH:mm:ss", "yyyyMMdd");
		return dateStr;
	}
	
	   /**
     * 根据指定的格式格式化时间日期 20081223->2008-12-23
     * 
     * @param inputDate 输入的日期
     * @return 格式化后的日期
     */
    public static String formatDate(String inputDate) {
        String outDate = "";
        if (!StringUtil.isEmpty(inputDate) && inputDate.length() == 8) {
            outDate = inputDate.substring(0, 4) + "-" + inputDate.substring(4, 6) + "-"
                    + inputDate.substring(6, 8);
        }else{
        	outDate = inputDate;
        }         	
        return outDate;
    }

    /**
     * 根据指定的格式格式化时间日期 2008-12-23->20081223 
     * 
     * @param inDate 输入的日期
     * @return 格式化后的日期 格式不符则返回空
     */
    public static String changeDateFormat(String inDate) {
        //change 2008-12-23 to 20081223
        String outDate = "";
        if (!StringUtil.isEmpty(inDate) && inDate.length() == 10) {
            outDate = inDate.substring(0, 4) + inDate.substring(5, 7) + inDate.substring(8, 10);
        }else{
        	outDate = inDate;
        }
        return outDate;
    }
    public static int daysBetween(String startDay,String endDay) throws ParseException{
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar cal= Calendar.getInstance();
    	
			cal.setTime(sdf.parse(startDay));
			long time1 = cal.getTimeInMillis();
	    	cal.setTime(sdf.parse(endDay));
	    	long time2 = cal.getTimeInMillis();
	    	long between = (time2-time1)/(1000*3600*24);
	   
    	return Integer.parseInt(String.valueOf(between));
    
    }
}
