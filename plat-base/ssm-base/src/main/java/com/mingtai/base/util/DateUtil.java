package com.mingtai.base.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期类型与字符串类型相互转换
 */
public class DateUtil {
	
	/**
	 * Base ISO 8601 Date format yyyyMMdd i.e., 20021225 for the 25th day of December in the year 2002
	 */
	public static final String ISO_DATE_FORMAT = "yyyyMMdd";
	
	/**
	 * Expanded ISO 8601 Date format yyyy-MM-dd i.e., 2002-12-25 for the 25th day of December in the year 2002
	 */
	public static final String ISO_EXPANDED_DATE_FORMAT = "yyyy-MM-dd";
	
	/**
	 * yyyy-MM-dd hh:mm:ss
	 */
	public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	//public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm";
	public static final String DATETIME_PATTERN_L = "yyyyMMddHHmmss";

	/**
	 * Default lenient setting for getDate.
	 */
	private static boolean LENIENT_DATE = false;

	/**
	 * 暂时不用
	 * 
	 * @param JD
	 * @return
	 */
	protected static final float normalizedJulian(float JD) {
		float f = Math.round(JD + 0.5f) - 0.5f;
		return f;
	}

	/**
	 * 浮点值转换成日期格式<br>
	 * 暂时不用 Returns the Date from a julian. The Julian date will be converted to noon GMT, such that it matches the nearest half-integer (i.e., a julian date of 1.4 gets changed to 1.5, and 0.9 gets changed to 0.5.)
	 * 
	 * @param JD the Julian date
	 * @return the Gregorian date
	 */
	public static final Date toDate(float JD) {
		/*
		 * To convert a Julian Day Number to a Gregorian date, assume that it is for 0 hours, Greenwich time (so that it ends in 0.5). Do the following calculations, again dropping the fractional part of all multiplicatons and divisions. Note: This method will not give dates accurately on the Gregorian Proleptic Calendar, i.e., the calendar you get by extending the Gregorian calendar backwards to years earlier than 1582. using the Gregorian leap year rules. In particular, the method fails if Y<400.
		 */
		float Z = (normalizedJulian(JD)) + 0.5f;
		float W = (int) ((Z - 1867216.25f) / 36524.25f);
		float X = (int) (W / 4f);
		float A = Z + 1 + W - X;
		float B = A + 1524;
		float C = (int) ((B - 122.1) / 365.25);
		float D = (int) (365.25f * C);
		float E = (int) ((B - D) / 30.6001);
		float F = (int) (30.6001f * E);
		int day = (int) (B - D - F);
		int month = (int) (E - 1);
		if (month > 12) {
			month = month - 12;
		}
		int year = (int) (C - 4715); // (if Month is January or February) or
		// C-4716 (otherwise)
		if (month > 2) {
			year--;
		}
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1); // damn 0 offsets
		c.set(Calendar.DATE, day);
		return c.getTime();
	}

	/**
	 * Returns the days between two dates. Positive values indicate that the second date is after the first, and negative values indicate, well, the opposite. Relying on specific times is problematic.
	 * 
	 * @param early the "first date"
	 * @param late the "second date"
	 * @return the days between the two dates
	 */
	public static final int daysBetween(Date early, Date late) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(early);
		c2.setTime(late);
		return daysBetween(c1, c2);
	}

	/**
	 * Returns the days between two dates. Positive values indicate that the second date is after the first, and negative values indicate, well, the opposite.
	 * 
	 * @param early
	 * @param late
	 * @return the days between two dates.
	 */
	public static final int daysBetween(Calendar early, Calendar late) {
		return (int) (toJulian(late) - toJulian(early));
	}

	/**
	 * Return a Julian date based on the input parameter. This is based from calculations found at <a href="http://quasar.as.utexas.edu/BillInfo/JulianDatesG.html">Julian Day Calculations (Gregorian Calendar)</a>, provided by Bill Jeffrys.
	 * 
	 * @param c a calendar instance
	 * @return the julian day number
	 */
	public static final float toJulian(Calendar c) {
		int Y = c.get(Calendar.YEAR);
		int M = c.get(Calendar.MONTH);
		int D = c.get(Calendar.DATE);
		int A = Y / 100;
		int B = A / 4;
		int C = 2 - A + B;
		float E = (int) (365.25f * (Y + 4716));
		float F = (int) (30.6001f * (M + 1));
		float JD = C + D + E + F - 1524.5f;
		return JD;
	}

	/**
	 * 暂时不用 Return a Julian date based on the input parameter. This is based from calculations found at <a href="http://quasar.as.utexas.edu/BillInfo/JulianDatesG.html">Julian Day Calculations (Gregorian Calendar)</a>, provided by Bill Jeffrys.
	 * 
	 * @param date
	 * @return the julian day number
	 */
	public static final float toJulian(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return toJulian(c);
	}

	/**
	 * 日期增加
	 * 
	 * @param isoString 日期字符串
	 * @param fmt 格式
	 * @param field 年/月/日 Calendar.YEAR/Calendar.MONTH/Calendar.DATE
	 * @param amount 增加数量
	 * @return
	 * @throws ParseException
	 */
	public static final String dateIncrease(String isoString, String fmt, int field, int amount) {
		try {
			Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
			cal.setTime(stringToDate(isoString, fmt, true));
			cal.add(field, amount);
			return dateToString(cal.getTime(), fmt);
		} catch (Exception ex) {
			return null;
		}
	}
	public static String format(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
	/**
	 * Time Field Rolling function. Rolls (up/down) a single unit of time on the given time field.
	 * 
	 * @param isoString
	 * @param field the time field.
	 * @param up Indicates if rolling up or rolling down the field value.
	 * @param up use formating char's
	 * @exception ParseException if an unknown field value is given.
	 */
	public static final String roll(String isoString, String fmt, int field, boolean up) throws ParseException {
		Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
		cal.setTime(stringToDate(isoString, fmt));
		cal.roll(field, up);
		return dateToString(cal.getTime(), fmt);
	}

	/**
	 * Time Field Rolling function. Rolls (up/down) a single unit of time on the given time field.
	 * 
	 * @param isoString
	 * @param field the time field.
	 * @param up Indicates if rolling up or rolling down the field value.
	 * @exception ParseException if an unknown field value is given.
	 */
	public static final String roll(String isoString, int field, boolean up) throws ParseException {
		return roll(isoString, DATETIME_PATTERN, field, up);
	}

	/**
	 * 字符串转换为日期java.util.Date
	 * 
	 * @param dateText 字符串
	 * @param format 日期格式
	 * @param lenient 日期越界标志
	 * @return
	 */
	public static Date stringToDate(String dateText, String format, boolean lenient) {
		Date date1 = new Date();
		String[] d = dateText.split(" ");
		String[] parts = d[0].split("-");
		if (parts.length >= 3) {
			int years = Integer.parseInt(parts[0]);
			int months = Integer.parseInt(parts[1]) - 1;
			int days = Integer.parseInt(parts[2]);
			int hours = 0;
			int minutes = 0;
			int seconds = 0;

			GregorianCalendar gc = new GregorianCalendar(years, months, days, hours, minutes, seconds);

			date1 = gc.getTime();
		}
		return date1;
	}

	/**
	 * 字符串转换为日期java.util.Date
	 * 
	 * @param dateString 字符串
	 * @param format 日期格式
	 * @return
	 */
	public static Date stringToDate(String dateString, String format) {
		return stringToDate(dateString, format, LENIENT_DATE);
	}

	/**
	 * 字符串转换为日期java.util.Date
	 * 
	 * @param dateString 字符串
	 */
	public static Date stringToDate(String dateString) {
		return stringToDate(dateString, ISO_EXPANDED_DATE_FORMAT, LENIENT_DATE);
	}

	/**
	 * 根据时间变量返回时间字符串
	 * 
	 * @return 返回时间字符串
	 * @param pattern 时间字符串样式
	 * @param date 时间变量
	 */
	public static String dateToString(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		try {
			SimpleDateFormat sfDate = new SimpleDateFormat(pattern);
			sfDate.setLenient(false);
			return sfDate.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 根据时间变量返回时间字符串 yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		return dateToString(date, ISO_EXPANDED_DATE_FORMAT);
	}

	/**
	 * 返回当前时间
	 * 
	 * @return 返回当前时间
	 */
	public static Date getCurrentDateTime() {
		Calendar calNow = Calendar.getInstance();
		Date dtNow = calNow.getTime();
		return dtNow;
	}

	public static String getNowDateTime() {
		Date cur = new Date();
		cur = getCurrentDateTime();
		String dtnow = dateToStringWithTime(cur);
		return dtnow;
	}

	/**
	 * 返回当前日期字符串
	 * 
	 * @param pattern 日期字符串样式
	 * @return
	 */
	public static String getCurrentDateString(String pattern) {
		return dateToString(getCurrentDateTime(), pattern);
	}

	/**
	 * 返回当前日期字符串 yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getCurrentDateString() {
		return dateToString(getCurrentDateTime(), DATETIME_PATTERN);
	}

	/**
	 * 返回当前日期字符串 yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String getCurrentDateSecondString() {
		return dateToString(getCurrentDateTime(), DATETIME_PATTERN_L);
	}

	/**
	 * 返回当前日期+时间字符串 yyyy-MM-dd hh:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToStringWithTime(Date date) {
		return dateToString(date, DATETIME_PATTERN);
	}

	/**
	 * 日期增加-按日增加
	 * 
	 * @param date
	 * @param days
	 * @return java.util.Date
	 */
	public static Date dateIncreaseByDay(Date date, int days) {
		Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	/**
	 * 日期增加-按月增加
	 * 
	 * @param date
	 * @param mnt
	 * @return java.util.Date
	 */
	public static Date dateIncreaseByMonth(Date date, int mnt) {
		Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
		cal.setTime(date);
		cal.add(Calendar.MONTH, mnt);
		return dateIncreaseByDay(cal.getTime(), -1);
	}

	/**
	 * 日期增加-按年增加
	 * 
	 * @param date
	 * @param mnt
	 * @return java.util.Date
	 */
	public static Date dateIncreaseByYear(Date date, int mnt) {
		Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
		cal.setTime(date);
		cal.add(Calendar.YEAR, mnt);

		return dateIncreaseByDay(cal.getTime(), -1);
	}

	/**
	 * 日期增加
	 * 
	 * @param date 日期字符串 yyyy-MM-dd
	 * @param days
	 * @return 日期字符串 yyyy-MM-dd
	 */
	public static String dateIncreaseByDay(String date, int days) {
		return dateIncreaseByDay(date, ISO_EXPANDED_DATE_FORMAT, days);
	}

	/**
	 * 日期增加
	 * 
	 * @param date 日期字符串
	 * @param fmt 日期格式
	 * @param days
	 * @return
	 */
	public static String dateIncreaseByDay(String date, String fmt, int days) {
		return dateIncrease(date, fmt, Calendar.DATE, days);
	}

	/**
	 * 比较两个日期
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareDate(String date1, String date2) {
		return stringToDate(date1, "yyyy-MM-dd").compareTo(stringToDate(date2, "yyyy-MM-dd"));
	}

	/**
	 * 日期字符串格式转换
	 * 
	 * @param src 日期字符串
	 * @param srcfmt 源日期格式
	 * @param desfmt 目标日期格式
	 * @return
	 */
	public static String stringToString(String src, String srcfmt, String desfmt) {
		return dateToString(stringToDate(src, srcfmt), desfmt);
	}

	public static String getTodayString() {
		Date cur = new Date();
		String today = dateToString(cur, ISO_EXPANDED_DATE_FORMAT);
		return today;
	}

	/**
	 * @param startDay 需要比较的时间 不能为空(null),需要正确的日期格式 ,如：2009-09-12
	 * @param endDay 被比较的时间 为空(null)则为当前时间
	 * @param stype 返回值类型 0为多少天，1为多少个月，2为多少年
	 * @return 举例： compareDate("2009-09-12", null, 0);//比较天 compareDate("2009-09-12", null, 1);//比较月 compareDate("2009-09-12", null, 2);//比较年
	 */
	public static int compareDate(String startDay, String endDay, int stype) {
		int n = 0;
		String[] u = { "天", "月", "年" };
		String formatStyle = stype == 1 ? "yyyy-MM" : "yyyy-MM-dd";

		endDay = endDay == null ? getCurrentDate("yyyy-MM-dd") : endDay;

		DateFormat df = new SimpleDateFormat(formatStyle);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(startDay));
			c2.setTime(df.parse(endDay));
		} catch (Exception e3) {
			System.out.println("wrong occured");
		}
		// List list = new ArrayList();
		while (!c1.after(c2)) { // 循环对比，直到相等，n 就是所要的结果
			// list.add(df.format(c1.getTime())); // 这里可以把间隔的日期存到数组中 打印出来
			n++;
			if (stype == 1) {
				c1.add(Calendar.MONTH, 1); // 比较月份，月份+1
			} else {
				c1.add(Calendar.DATE, 1); // 比较天数，日期+1
			}
		}
		n = n - 1;
		if (stype == 2) {
			n = (int) n / 365;
		}
		System.out.println(startDay + " -- " + endDay + " 相差多少" + u[stype] + ":" + n);
		return n;
	}

	public static String getCurrentDate(String format) {
		Calendar day = Calendar.getInstance();
		day.add(Calendar.DATE, 0);
		SimpleDateFormat sdf = new SimpleDateFormat(format);// "yyyy-MM-dd"
		String date = sdf.format(day.getTime());
		return date;
	}

	/**
	 * 取得当天日期,格式 2009-02-11
	 * 
	 * @return
	 */
	public static String getToday() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cl = new GregorianCalendar();
		return sdf.format(cl.getTime());
	}

	/**
	 * 取得当天日期时间,格式 2009-02-11 23:9:21
	 * 
	 * @return
	 */
	public static String getTodaytime() {
		Calendar cl = new GregorianCalendar();
		return getToday() + " " + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND) + " ";
	}

	/**
	 * 取得当前时间,格式 23:12:20
	 * 
	 * @return
	 */
	public static String getTime() {
		Calendar cl = new GregorianCalendar();
		return cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND) + " ";
	}

	/**
	 * 取得当前小时
	 * 
	 * @return
	 */
	public static int getHour() {
		Calendar cl = new GregorianCalendar();
		return cl.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 取得当前日期 格式为20090211
	 * 
	 * @return
	 */
	public static String getNoFormatToday() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cl = new GregorianCalendar();
		return sdf.format(cl.getTime());
	}

	/**
	 * 取得当前时间 格式为231611
	 * 
	 * @return
	 */
	public static String getNoFormatTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		Calendar cl = new GregorianCalendar();
		return sdf.format(cl.getTime());
	}

	/**
	 * 取得当前年份
	 * 
	 * @return
	 */
	public static String getYear() {
		return DateUtil.getNoFormatToday().substring(0, 4);
	}

	/**
	 * 取得当前月份
	 * 
	 * @return
	 */
	public static String getMonth() {
		return DateUtil.getNoFormatToday().substring(4, 6);
	}

	/**
	 * 取得当前日
	 * 
	 * @return
	 */
	public static String getDay() {
		return DateUtil.getNoFormatToday().substring(6, 8);
	}

	/**
	 * 返回昨天的日期 格式2009-02-10
	 * 
	 * @return
	 */
	public static String getYesterday() {
		String strYesterday = "";
		Calendar cale = null;
		cale = new GregorianCalendar();
		cale.add(Calendar.DATE, -1);
		strYesterday = DateUtil.getStrByCalendar(cale);
		return strYesterday;
	}

	public static String getStrByCalendar(Calendar cale) {
		return (new SimpleDateFormat("yyyy-MM-dd")).format(cale.getTime());
	}

	/**
	 * 日期字符串的格式转换,例如"2009-02-11"转换为2009年2月11日
	 * 
	 * @param sDate
	 * @return
	 */
	public static String getChnDateString(String sDate) {
		if (sDate == null) {
			return null;
		}
		sDate = sDate.trim();
		if (sDate.length() == 7) {
			sDate += "-01";
		}
		StringTokenizer st = new StringTokenizer(sDate, "-");
		int year = 2100;
		int month = 0;
		int day = 1;
		try {
			year = Integer.parseInt(st.nextToken());
			month = Integer.parseInt(st.nextToken()) - 1;
			day = Integer.parseInt(st.nextToken());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Calendar cl = new GregorianCalendar(year, month, day);
		return cl.get(Calendar.YEAR) + "年" + (cl.get(Calendar.MONTH) + 1) + "月" + cl.get(Calendar.DATE) + "日";
	}

	/**
	 * 取得某年某月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getMaxDayOfMonth(int year, int month) {
		Calendar cal = new GregorianCalendar(year, month - 1, 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}

	/**
	 * 取得某年某月的第一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getMinDayOfMonth(int year, int month) {
		Calendar cal = new GregorianCalendar(year, month - 1, 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}

	/**
	 * 取得当天的中文日期，像2006年11月28日 星期二
	 * 
	 * @return
	 */
	public static String getChineseToDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 E", Locale.CHINESE);
		Calendar cl = new GregorianCalendar();
		return sdf.format(cl.getTime());
	}

	/**
	 * 取得当天的中文日期，像2006年11月28日 星期二 下午05:06
	 * 
	 * @return
	 */
	public static String getChineseToDayTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 E a", Locale.CHINESE);
		Calendar cl = new GregorianCalendar();
		return sdf.format(cl.getTime());
	}

	/**
	 * 根据字符串，取得日期类
	 * 
	 * @param sDate
	 * @return
	 */
	public static Calendar getDate(String sDate) {
		if (sDate == null) {
			return null;
		}
		sDate = sDate.trim();
		if (sDate.length() == 7) {
			sDate += "-01";
		}
		StringTokenizer st = new StringTokenizer(sDate, "-");
		int year = 2100;
		int month = 0;
		int day = 1;
		try {
			year = Integer.parseInt(st.nextToken());
			month = Integer.parseInt(st.nextToken()) - 1;
			day = Integer.parseInt(st.nextToken());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new GregorianCalendar(year, month, day);
	}

	/**
	 * 根据日期类取得日期的字符串形式
	 * 
	 * @param sDate
	 * @return
	 */
	public static String getDateString(Calendar sDate) {
		if (sDate == null) {
			return "";
		}
		return (new SimpleDateFormat("yyyy-MM-dd")).format(sDate.getTime());
	}

	/**
	 * 根据日期类取年月的字符串形式
	 * 
	 * @param sDate
	 * @return
	 */
	public static String getYearMonth(Calendar sDate) {
		if (sDate == null) {
			return "";
		}
		return (new SimpleDateFormat("yyyy-MM")).format(sDate.getTime());
	}

	/**
	 * 比较两个日期类型的字符串，格式为（yyyy-mm-dd） 如果cale1大于cale2，返回1 如果cale1小于cale2，返回-1 如果相等，返回0 如果格式错误，返回-2
	 * 
	 * @param cale1
	 * @param cale2
	 * @return
	 */
	public static int compareCalendar(String cale1, String cale2) {
		Calendar calendar1 = getDate(cale1);
		Calendar calendar2 = getDate(cale2);
		if (calendar1 == null || calendar2 == null) {
			return -2;
		}
		return calendar1.compareTo(calendar2);
	}

}