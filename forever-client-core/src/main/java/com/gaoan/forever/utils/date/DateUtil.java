package com.gaoan.forever.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gaoan.forever.constant.MessageInfoConstant;

@SuppressWarnings("unused")
public final class DateUtil {
	private static Log log = LogFactory.getLog(DateUtil.class);
	private static final String TIME_PATTERN = "HH:mm";
	// 一天的毫秒数 60*60*1000*24

	private final static long DAY_MILLIS = 86400000;

	// 一小时的毫秒数 60*60*1000
	private final static long HOUR_MILLIS = 3600000;

	// 一分钟的毫秒数 60*1000
	private final static long MINUTE_MILLIS = 60000;

	public static String YYMMDD = "yyMMdd";

	/**
	 * Checkstyle rule: utility classes should not have public constructor
	 */
	private DateUtil() {
	}

	public static String getOrderNo(Date date) {
		String str = format(date, YYMMDD);

		return str + String.valueOf(Math.random()).substring(2, 8);
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param formatString
	 * @return
	 */
	public static String format(Date date, String formatString) {
		SimpleDateFormat df = new SimpleDateFormat(formatString);
		return df.format(date);
	}

	/**
	 * This method generates a String representation of a date/time in the
	 * format you specify on input
	 *
	 * @param aMask
	 *            the date pattern the String is in
	 * @param strDate
	 *            a String representation of a date
	 * @return a converted Date object
	 * @throws ParseException
	 *             when String doesn't match the expected format
	 * @see SimpleDateFormat
	 */
	public static Date convertStringToDate(String aMask, String strDate) throws ParseException {
		SimpleDateFormat df;
		Date date;
		df = new SimpleDateFormat(aMask);

		if (log.isDebugEnabled()) {
			log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
		}

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			// log.error("ParseException: " + pe);
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * This method returns the current date time in the format: MM/dd/yyyy HH:MM
	 * a
	 *
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(TIME_PATTERN, theTime);
	}

	/**
	 * This method generates a String representation of a date's date/time in
	 * the format you specify on input
	 *
	 * @param aMask
	 *            the date pattern the String is in
	 * @param aDate
	 *            a date object
	 * @return a formatted String representation of the date
	 * @see SimpleDateFormat
	 */
	public static String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			log.warn("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * yyyy-mm-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormat1(Date date) {
		String result = "";
		if (date != null) {
			try {
				result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * yyyy/mm/dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormat2(Date date) {
		String result = "";
		if (date != null) {
			try {
				result = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date);
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * yyyy年mm月dd日 HH时mm分ss秒
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormat3(Date date) {
		String result = "";
		if (date != null) {
			try {
				result = new SimpleDateFormat("yyyy年MM月dd日  HH时mm分ss秒").format(date);
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * yyyy-mm-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatY1(Date date) {
		String result = "";
		if (date != null) {
			try {
				result = new SimpleDateFormat("yyyy-MM-dd").format(date);
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * yyyy/mm/dd
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatY2(Date date) {
		String result = "";
		if (date != null) {
			try {
				result = new SimpleDateFormat("yyyy/MM/dd").format(date);
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * yyyy年mm月dd日
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatY3(Date date) {
		String result = "";
		if (date != null) {
			try {
				result = new SimpleDateFormat("yyyy年MM月dd日").format(date);
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * mm月dd日
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatY4(Date date) {
		String result = "";
		if (date != null) {
			try {
				result = new SimpleDateFormat("MM月dd日").format(date);
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * yyyy
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatY5(Date date) {
		String result = "";
		if (date != null) {
			try {
				result = new SimpleDateFormat("yyyy").format(date);
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * yyyy年MM月
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatY6(Date date) {
		String result = "";
		if (date != null) {
			try {
				result = new SimpleDateFormat("yyyy年MM月").format(date);
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * mm:dd
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatY7(Date date) {
		String result = "";
		if (date != null) {
			try {
				result = new SimpleDateFormat("MM:dd").format(date);
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatH1(Date date) {
		String result = "";
		if (date != null) {
			try {
				result = new SimpleDateFormat("HH:mm:ss").format(date);
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * HH:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatH2(Date date) {
		String result = "";
		if (date != null) {
			try {
				result = new SimpleDateFormat("HH:mm").format(date);
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * @param date
	 * @return
	 */
	public static String dateFormat(Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				result = new SimpleDateFormat(format).format(date);
			} catch (Exception e) {
			}
		}
		return result;
	}

	public static Date parseDate(String str, String simple) {
		Date date = null;
		if (StringUtils.isNotEmpty(str)) {
			try {
				date = new SimpleDateFormat(simple).parse(str);
			} catch (Exception e) {
			}
		}
		return date;
	}

	/**
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @param str
	 * @return
	 */
	public static Date parseDate1(String str) {
		Date date = null;
		if (StringUtils.isNotEmpty(str)) {
			try {
				date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
			} catch (Exception e) {
			}
		}
		return date;
	}

	/**
	 * yyyy/mm/dd HH:mm:ss
	 * 
	 * @param str
	 * @return
	 */
	public static Date parseDate2(String str) {
		Date date = null;
		if (StringUtils.isNotEmpty(str)) {
			try {
				date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(str);
			} catch (Exception e) {
			}
		}
		return date;
	}

	/**
	 * yyyy年mm月dd日 HH时mm分ss秒
	 * 
	 * @param str
	 * @return
	 */
	public static Date parseDate3(String str) {
		Date date = null;
		if (StringUtils.isNotEmpty(str)) {
			try {
				date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").parse(str);
			} catch (Exception e) {
			}
		}
		return date;
	}

	/**
	 * yyyy-mm-dd
	 * 
	 * @param str
	 * @return
	 */
	public static Date parseDateY1(String str) {
		Date date = null;
		if (StringUtils.isNotEmpty(str)) {
			try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
			} catch (Exception e) {
			}
		}
		return date;
	}

	/**
	 * yyyy/mm/dd
	 * 
	 * @param str
	 * @return
	 */
	public static Date parseDateY2(String str) {
		Date date = null;
		if (StringUtils.isNotEmpty(str)) {
			try {
				date = new SimpleDateFormat("yyyy/MM/dd").parse(str);
			} catch (Exception e) {
			}
		}
		return date;
	}

	/**
	 * yyyy年mm月dd日
	 * 
	 * @param str
	 * @return
	 */
	public static Date parseDateY3(String str) {
		Date date = null;
		if (StringUtils.isNotEmpty(str)) {
			try {
				date = new SimpleDateFormat("yyyy年MM月dd日").parse(str);
			} catch (Exception e) {
			}
		}
		return date;
	}

	/**
	 * HH:mm:ss
	 * 
	 * @param str
	 * @return
	 */
	public static Date parseDateH1(String str) {
		Date date = null;
		if (StringUtils.isNotEmpty(str)) {
			try {
				date = new SimpleDateFormat("HH:mm:ss").parse(str);
			} catch (Exception e) {
			}
		}
		return date;
	}

	/**
	 * HH:mm
	 * 
	 * @param str
	 * @return
	 */
	public static Date parseDateH2(String str) {
		Date date = null;
		if (StringUtils.isNotEmpty(str)) {
			try {
				date = new SimpleDateFormat("HH:mm").parse(str);
			} catch (Exception e) {
			}
		}
		return date;
	}

	public static Date parseBeginDate(String str) {
		Date date = null;
		if (StringUtils.isNotEmpty(str)) {
			try {
				if (str.indexOf(":") < 0) {
					str += " 00:00:00";
				}
				date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
			} catch (Exception e) {
			}
		}
		return date;
	}

	public static Date parseEndDate(String str) {
		Date date = null;
		if (StringUtils.isNotEmpty(str)) {
			try {
				if (str.indexOf(":") < 0) {
					str += " 23:59:59";
				}
				date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
			} catch (Exception e) {
			}
		}
		return date;
	}

	public static int gadYear(Date startDate, Date endDate) {
		Integer result = 0;
		Calendar startC = Calendar.getInstance();
		Calendar endC = Calendar.getInstance();
		startC.setTime(startDate);
		endC.setTime(endDate);
		result = startC.get(Calendar.YEAR) - endC.get(Calendar.YEAR);
		return result == 0 ? 1 : Math.abs(result);
	}

	public static int gadMonday(Date startDate, Date endDate) {
		Integer result = 0;
		Calendar startC = Calendar.getInstance();
		Calendar endC = Calendar.getInstance();

		startC.setTime(startDate);
		int year = startC.get(Calendar.YEAR);
		int month = startC.get(Calendar.MONTH);

		endC.setTime(endDate);
		int year1 = endC.get(Calendar.YEAR);
		int month1 = endC.get(Calendar.MONTH);

		if (year == year1) {
			result = month1 - month;// 两个日期相差几个月，即月份差
		} else {
			result = 12 * (year1 - year) + month1 - month;// 两个日期相差几个月，即月份差
		}
		return result == 0 ? 1 : Math.abs(result);
	}

	/**
	 * 将时间减去天
	 * 
	 * @author 作者 :刘万里
	 * @version 创建时间：2014年7月28日 下午12:38:31
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date calendarDay(Date date, Integer amount) {
		Calendar dateC = Calendar.getInstance();
		dateC.setTime(date);
		dateC.add(Calendar.DAY_OF_MONTH, amount);
		return dateC.getTime();
	}

	/**
	 * 将时间减去月
	 * 
	 * @author 作者 :刘万里
	 * @version 创建时间：2014年7月28日 下午12:42:34
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date calendarMonth(Date date, Integer amount) {
		Calendar dateC = Calendar.getInstance();
		dateC.setTime(date);
		dateC.add(Calendar.MONTH, amount);
		return dateC.getTime();
	}

	/**
	 * 将时间减去年
	 * 
	 * @author 作者 :刘万里
	 * @version 创建时间：2014年7月28日 下午12:42:48
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date calendarYear(Date date, Integer amount) {
		Calendar dateC = Calendar.getInstance();
		dateC.setTime(date);
		dateC.add(Calendar.YEAR, amount);
		return dateC.getTime();
	}

	/**
	 * 将时间减去时
	 * 
	 * @author 作者 :刘万里
	 * @version 创建时间：2014年7月29日 上午11:26:13
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date calendarHour(Date date, Integer amount) {
		Calendar dateC = Calendar.getInstance();
		dateC.setTime(date);
		dateC.add(Calendar.HOUR, amount);
		return dateC.getTime();
	}

	/**
	 * 将时间减去分
	 * 
	 * @author 作者 :刘万里
	 * @version 创建时间：2014年7月29日 上午11:26:20
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date calendarMinute(Date date, Integer amount) {
		Calendar dateC = Calendar.getInstance();
		dateC.setTime(date);
		dateC.add(Calendar.MINUTE, amount);
		return dateC.getTime();
	}

	/**
	 * 将时间减去秒
	 * 
	 * @author 作者 :刘万里
	 * @version 创建时间：2014年7月29日 上午11:26:27
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date calendarSecond(Date date, Integer amount) {
		Calendar dateC = Calendar.getInstance();
		dateC.setTime(date);
		dateC.add(Calendar.SECOND, amount);
		return dateC.getTime();
	}

	public static Date calendarMilliSecond(Date date, Integer amount) {
		Calendar dateC = Calendar.getInstance();
		dateC.setTime(date);
		dateC.add(Calendar.MILLISECOND, amount);
		return dateC.getTime();
	}

	/**
	 * 当前时间加毫秒数 例 ： 20141019180403477
	 * 
	 * @author wushuaihua
	 * @version 创建时间：2014年10月19日 下午6:06:27
	 * @return
	 */
	public static String getCurrentTimeMillise() {
		Calendar cal = new GregorianCalendar();
		// 当前年
		int year = cal.get(Calendar.YEAR);
		// 当前月
		int month = (cal.get(Calendar.MONTH)) + 1;
		// 当前月的第几天：即当前日
		int day_of_month = cal.get(Calendar.DAY_OF_MONTH);
		// 当前时：HOUR_OF_DAY-24小时制；HOUR-12小时制
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		// 当前分
		int minute = cal.get(Calendar.MINUTE);
		// 当前秒
		int second = cal.get(Calendar.SECOND);
		// 当前毫秒
		int millise = cal.get(Calendar.MILLISECOND);

		StringBuffer sb = new StringBuffer(String.valueOf(year));
		sb.append(fullZero(month)).append(fullZero(day_of_month)).append(fullZero(hour)).append(fullZero(minute))
				.append(fullZero(second)).append(fullMillizeZero(millise));
		return sb.toString();
	}

	/**
	 * 月、日、时、分、秒填充
	 * 
	 * @author wushuaihua
	 * @version 创建时间：2014年10月19日 下午6:07:25
	 * @param num
	 * @return
	 */
	private static String fullZero(int num) {
		return num < 10 ? "0" + num : String.valueOf(num);
	}

	/**
	 * 毫秒填充
	 * 
	 * @author wushuaihua
	 * @version 创建时间：2014年10月19日 下午6:09:54
	 * @param num
	 * @return
	 */
	private static String fullMillizeZero(int num) {
		String str = String.valueOf(num);
		if (num < 10)
			str = "00" + str;
		else if (num < 100)
			str = "0" + str;
		return str;
	}

	/**
	 * 获取当周的星期一
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonday(Date date) {
		date = DateUtils.truncate(date, Calendar.DATE);
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 1) {
			dayOfWeek = 7;
		} else {
			dayOfWeek -= 1;
		}
		return DateUtils.addDays(date, 1 - dayOfWeek);
	}

	/**
	 * 获取当周的星期天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getSunday(Date date) {
		date = DateUtils.truncate(date, Calendar.DATE);
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 1) {
			dayOfWeek = 7;
		} else {
			dayOfWeek -= 1;
		}
		return DateUtils.addDays(date, 7 - dayOfWeek);
	}

	/**
	 * 得到当前月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthFirstDay(Date date) {
		date = DateUtils.truncate(date, Calendar.MONTH);
		return date;
	}

	/**
	 * 得到当前月的最后天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthLastDay(Date date) {
		date = DateUtils.truncate(date, Calendar.MONDAY);
		date = DateUtils.addMonths(date, 1);
		date = DateUtils.addDays(date, -1);
		return date;
	}

	/**
	 * 校验所传时间戳是否在当前时间的limit时间段内
	 * 
	 * @param time
	 * @param limit
	 * @return
	 */
	public static boolean isInDate(long time, int limit) {
		boolean result = false;

		Calendar calendar = Calendar.getInstance(); // 处理开始时间
		calendar.add(Calendar.MINUTE, limit * -1);
		Date startDate = calendar.getTime();

		calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, limit);
		Date endDate = calendar.getTime();

		calendar.setTimeInMillis(time);
		Date checkDate = calendar.getTime();

		log.info("校验时间: " + dateFormat1(checkDate) + ", 正确时间段: " + dateFormat1(startDate) + "-" + dateFormat1(endDate));

		if (checkDate.after(startDate) && checkDate.before(endDate)) {
			result = true;
		}

		return result;
	}

}
