package com.own.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类名: DateUtils<br>
 * 类描述: 日期时间工具类<br>
 * 作成者: 展望<br>
 * 作成日期 2018-4-27 下午05:44:58<br>
 * Letter Date or Time Component Presentation Examples: <br>
 * G Era designator Text AD<br>
 * y Year Year 1996; 96<br>
 * M Month in year Month July; Jul; 07<br>
 * w Week in year Number 27<br>
 * W Week in month Number 2<br>
 * D Day in year Number 189<br>
 * d Day in month Number 10<br>
 * F Day of week in month Number 2<br>
 * E Day in week Text Tuesday; Tue<br>
 * a Am/pm marker Text PM<br>
 * H Hour in day (0-23) Number 0<br>
 * k Hour in day (1-24) Number 24<br>
 * K Hour in am/pm (0-11) Number 0<br>
 * h Hour in am/pm (1-12) Number 12<br>
 * m Minute in hour Number 30<br>
 * s Second in minute Number 55<br>
 * S Millisecond Number 978<br>
 * z Time zone General time zone Pacific Standard Time; PST; GMT-08:00<br>
 * Z Time zone RFC 822 time zone -0800<br>
 */
public class DateUtils {

	/**
	 * Date Format without time: yyyy-MM-dd
	 */
	public static final String FORMAT_DATE_01 = "yyyy-MM-dd";

	/**
	 * Date Format without time: yyyyMMdd
	 */
	public static final String FORMAT_DATE_02 = "yyyyMMdd";

	/**
	 * Date Format with time: yyyy/MM/dd HH:mm:ss
	 */
	public static final String FORMAT_TIME_01 = "yyyy/MM/dd HH:mm:ss";

	/**
	 * Date Format with time: yyyy-MM-dd HH:mm:ss
	 */
	public static final String FORMAT_TIME_02 = "yyyy-MM-dd HH:mm:ss";
	
	public static final String FORMAT_TIME_12 = "yyyy年MM月dd日 HH:mm:ss";

	/**
	 * Date Format with time: yyyy-MM-dd HH:mm
	 */
	public static final String FORMAT_TIME_03 = "yyyy-MM-dd HH:mm";

	/**
	 * Date Format with time: yyyyMMddHHmmss
	 */
	public static final String FORMAT_TIME_04 = "yyyyMMddHHmmss";

	/**
	 * Date Format with time: yyyyMMddHH
	 */
	public static final String FORMAT_TIME_05 = "yyyyMMddHH";

	/**
	 * Date Format with time: yyyyMMddHHmm
	 */
	public static final String FORMAT_TIME_06 = "yyyyMMddHHmm";

	/**
	 * Date Format with time: yy/MM/dd HH:mm:ss
	 */
	public static final String FORMAT_TIME_07 = "yy/MM/dd HH:mm:ss";
	
	/**
	 * Date Format with time: yyyyMMddHHmmssSSS
	 */
	public static final String FORMAT_TIME_08 = "yyyyMMddHHmmssSSS";
	/**
	 * Date Format with time: yyyy年MM月dd日
	 */
	public static final String FORMAT_TIME_09 = "yyyy年MM月dd日";
	/**
	 * 格林威治时间 格式 E MMM dd HH:mm:ss z yyyy
	 */
	public static final String FORMAT_TIME_10 = "E MMM dd HH:mm:ss z yyyy";

	/**
	 * Date Format with time: HH:mm
	 */
	public static final String FORMAT_TIME_11 = "HH:mm";
	
	public static final String FORMAT_TIME_13 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	/**
	 * Date Format of weekday: E
	 */
	public static final String FORMAT_WEERK_DAY = "E";
	/**
	 * Date Format of :yyyyMM
	 */
	public static final String FORMAT_YEAR_MONTH = "yyyyMM";
	
	public static final int CONSTANT1000 = 1000;
	public static final int CONSTANT24 = 24;
	public static final int CONSTANT60 = 60;
	public static final int CONSTANT8 = 8;
	public static final int CONSTANT10 = 10;
	public static final int CONSTANT12 = 12;

	/**
	 * 类名: WeekDay<br>
	 * 类描述: 星期 - 枚举<br>
	 * 作成者: 郭昕<br>
	 * 作成日期 2012-7-21 下午05:39:45<br>
	 */
	public enum WeekDay {
		/**
		 *星期天 
		 */
		SUN(0, "星期天"),
		/**
		 *星期一 
		 */
		MON(1, "星期一"), 
		/**
		 * 星期二
		 */
		TUE(2, "星期二"), 
		/**
		 * 星期三
		 */
		WED(3, "星期三"), 
		/**
		 * 星期四
		 */
		THU(4, "星期四"), 
		/**
		 * 星期五
		 */
		FRI(5, "星期五"), 
		/**
		 * 星期六
		 */
		SAT(6, "星期六");

		/**
		 * 数字
		 */
		private int index;
		/**
		 * 描述
		 */
		private String value;
        /**
         * 
         * @param index 数字
         * @param value 描述
         */
		private WeekDay(final int index, final String value) {
			this.index = index;
			this.value = value;
		}

		/**
		 * 获取星期枚举的序号
		 * 
		 * @return int
		 */
		public int getIndex() {
			return this.index;
		}

		/**
		 * 获取星期枚举的描述（中文）
		 * 
		 * @return String
		 */
		public String getValue() {
			return this.value;
		}

		/**
		 * 重写toString方法
		 */
		public String toString() {
			return this.value;
		}
	}

	/**
	 * Slf4j logger
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DateUtils.class);

	/**
	 * 将Date对象按指定的时间格式解析成String
	 * 
	 * @param date
	 *            Date
	 * @param pattern
	 *            String, 时间格式, 如yyyy/MM/dd HH:mm:ss
	 * @return String
	 */
	public static String format(Date date, String pattern) {
		if (date == null || StringUtils.isBlank(pattern)) {
			return null;
		}

		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 将Date对象按指定的时间格式解析成String，若Date对象为空，则返回当前时间
	 * 
	 * @param date
	 *            Date
	 * @param pattern
	 *            String, 时间格式, 如yyyy/MM/dd HH:mm:ss
	 * @return String
	 */
	public static String formatAdvance(Date date, final String pattern) {
		if (date == null) {
			date = new Date();
		}

		return format(date, pattern);
	}

	/**
	 * 将String按指定的时间格式转化成Date对象
	 * 
	 * @param str
	 *            String
	 * @param pattern
	 *            String, 时间格式, 如yyyy/MM/dd HH:mm:ss
	 * @return Date
	 */
	public static Date parse(final String str, final String pattern) {
		if (StringUtils.isBlank(str) || StringUtils.isBlank(pattern)) {
			return null;
		}

		Date date = null;
		try {
			date = new SimpleDateFormat(pattern).parse(str);
		} catch (final ParseException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return date;
	}
	/**
	 * 
	 * 描 述:将String按指定的时间格式转化成Date对象<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * @param str 字符串时间
	 * @param pattern 时间格式, 如yyyy/MM/dd HH:mm:ss
	 * @param locale 时区
	 * @return
	 */
	public static Date parse(final String str, final String pattern , Locale locale) {
		if (StringUtils.isBlank(str) || StringUtils.isBlank(pattern)|| null ==locale) {
			return null;
		}

		Date date = null;
		try {
			date = new SimpleDateFormat(pattern,locale).parse(str);
		} catch (final ParseException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return date;
	}

	/**
	 * 将String按指定的时间格式转化成Date对象，若String为空，则返回当前时间
	 * 
	 * @param str
	 *            String
	 * @param pattern
	 *            String, 时间格式, 如yyyy/MM/dd HH:mm:ss
	 * @return Date
	 */
	public static Date parseAdvance(final String str, final String pattern) {
		if (StringUtils.isBlank(str)) {
			return new Date();
		}

		return parse(str, pattern);
	}

	/**
	 * 获取两个时间点的间隔时长（秒），不区分先后顺序，即不会返回负值
	 * 
	 * @param before
	 *            Date
	 * @param after
	 *            Date
	 * @return long 时间间隔（秒）
	 */
	public static long compareSec(Date before, Date after) {
		if (before == null || after == null) {
			return 0L;
		}
		long dif = after.getTime() - before.getTime();
		dif = Math.abs(dif);
		return dif / CONSTANT1000;
	}

	/**
	 * 获取两个时间点的间隔时长（秒），不区分先后顺序，即不会返回负值
	 * 
	 * @param before
	 *            String
	 * @param after
	 *            String
	 * @param pattern
	 *            String, 时间格式, 如yyyy/MM/dd HH:mm:ss
	 * @return long 时间间隔（秒）
	 */
	public static long compareSec(String before, String after, String pattern) {
		if (StringUtils.isBlank(before) || StringUtils.isBlank(after)
				|| StringUtils.isBlank(pattern)) {
			return 0L;
		}

		Date beforeDate = parse(before, pattern);
		Date afterDate = parse(after, pattern);
		return compareSec(beforeDate, afterDate);
	}

	/**
	 * 获取两个时间点的间隔时长（分钟），不区分先后顺序，即不会返回负值
	 * 
	 * @param before
	 *            Date
	 * @param after
	 *            Date
	 * @return long 时间间隔（分钟）
	 */
	public static long compareMin(Date before, Date after) {
		return compareSec(before, after) / CONSTANT60;
	}

	/**
	 * 获取两个时间点的间隔时长（分钟），不区分先后顺序，即不会返回负值
	 * 
	 * @param before
	 *            String
	 * @param after
	 *            String
	 * @param pattern
	 *            String, 时间格式, 如yyyy/MM/dd HH:mm:ss
	 * @return long 时间间隔（分钟）
	 */
	public static long compareMin(String before, String after, String pattern) {
		return compareSec(before, after, pattern) / CONSTANT60;
	}

	/**
	 * 获取两个时间点的间隔时长（小时），不区分先后顺序，即不会返回负值
	 * 
	 * @param before
	 *            Date
	 * @param after
	 *            Date
	 * @return long 时间间隔（小时）
	 */
	public static long compareHour(Date before, Date after) {
		return compareMin(before, after) / CONSTANT60;
	}

	/**
	 * 获取两个时间点的间隔时长（小时），不区分先后顺序，即不会返回负值
	 * 
	 * @param before
	 *            String
	 * @param after
	 *            String
	 * @param pattern
	 *            String, 时间格式, 如yyyy/MM/dd HH:mm:ss
	 * @return long 时间间隔（小时）
	 */
	public static long compareHour(String before, String after, String pattern) {
		return compareMin(before, after, pattern) / CONSTANT60;
	}

	/**
	 * 获取两个时间点的间隔时长（天），不区分先后顺序，即不会返回负值
	 * 
	 * @param before
	 *            Date
	 * @param after
	 *            Date
	 * @return long 时间间隔（天）
	 */
	public static long compareDay(Date before, Date after) {
		return compareHour(before, after) / CONSTANT24;
	}

	/**
	 * 获取两个时间点的间隔时长（天），不区分先后顺序，即不会返回负值
	 * 
	 * @param before
	 *            String
	 * @param after
	 *            String
	 * @param pattern
	 *            String, 时间格式, 如yyyy/MM/dd HH:mm:ss
	 * @return long 时间间隔（天）
	 */
	public static long compareDay(String before, String after, String pattern) {
		return compareHour(before, after, pattern) / CONSTANT24;
	}

	/**
	 * 获取入参日期是星期几
	 * 
	 * @param date
	 *            Date
	 * @return WeekDay 星期枚举
	 */
	public static WeekDay convertWeek(Date date) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int weekDay=calendar.get(Calendar.DAY_OF_WEEK);
		weekDay--;
		for (WeekDay weekday : WeekDay.values()) {
			if (weekday.getIndex() == weekDay) {
				return weekday;
			}
		}
		return null;
	}

	/**
	 * 获取指定时间间隔分钟后的时间
	 * 
	 * @param date
	 *            Date
	 * @param min
	 *            int
	 * @return Date
	 */
	public static Date addMinutes(Date date, int min) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, min);
		return calendar.getTime();
	}
	
	public static Date addDay(Date date, int day) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}

	/**
	 * 获取当前时间间隔分钟后的时间
	 * 
	 * @param min
	 *            int
	 * @return Date
	 */
	public static Date addMinutes(int min) {
		return addMinutes(new Date(), min);
	}

	/**
	 * 获取当前时间对应的月份
	 * 
	 * @return int
	 */
	public static int getMonth(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int month = now.get(Calendar.MONTH);
		return month;
	}

	/**
	 * 判断两个时间是否处于同年同周
	 * 
	 * @param first
	 *            Date
	 * @param second
	 *            Date
	 * @return boolean
	 */
	public static boolean inSameWeek(Date first, Date second) {
		if (first == null || second == null) {
			return false;
		}
		Calendar c1 = Calendar.getInstance();
		c1.setTime(first);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(second);
		return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
				&& c1.get(Calendar.WEEK_OF_YEAR) == c2
						.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 判断两个时间是否处于同年同月同日
	 * 
	 * @param first
	 *            Date
	 * @param second
	 *            Date
	 * @return boolean
	 */
	public static boolean isSameDay(Date first, Date second) {
		return ((format(first, FORMAT_DATE_02).substring(0, CONSTANT8)).equals(format(
				second, FORMAT_DATE_02).substring(0, CONSTANT8)));
	}

	/**
	 * 判断两个时间是否处于同年同月同日同时
	 * 
	 * @param first
	 *            Date
	 * @param second
	 *            Date
	 * @return boolean
	 */
	public static boolean isSameHour(Date first, Date second) {
		return ((format(first, FORMAT_TIME_05).substring(0, CONSTANT10)).equals(format(
				second, FORMAT_TIME_05).substring(0, CONSTANT10)));
	}

	/**
	 * 判断两个时间是否处于同年同月同日同时同分
	 * 
	 * @param first
	 *            Date
	 * @param second
	 *            Date
	 * @return boolean
	 */
	public static boolean isSameMin(Date first, Date second) {
		return ((format(first, FORMAT_TIME_06).substring(0, CONSTANT12)).equals(format(
				second, FORMAT_TIME_06).substring(0, CONSTANT12)));
	}

	/**
	 * 获取当日零点时间
	 * 
	 * @return long
	 */
	public static long getInitTimeToday() {
		return getInitTime(new Date());
	}
	
	/**
	 * 描 述：将当前时间转换成指定的格式字符串<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param pattern
	 * @return String
	 */
	public static String getCurrentTime(String pattern) {
		return format(new Date(), pattern);
	}
	
	/**
	 * 获取指定日前的零点时间
	 * 
	 * @param date
	 *            Date
	 * @return long
	 */
	public static long getInitTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}
	/**
	 * 
	 * 描 述：获取指定时间的日期<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * @param date 日期
	 * @param hour_of_day 设置的小时
	 * @param minute 设置的分钟
	 * @param second 设置的秒
	 * @return
	 */
	public static long getSetTime(Date date , int hour_of_day ,int minute , int second ) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, hour_of_day);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}
	/**
	 * 
	 * 描 述：根据天换上秒<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * @param day 天数
	 * @return 天换算的秒
	 */
	public static int getSecondByDay(final int day) {
		return CONSTANT60 * CONSTANT60 * CONSTANT24 * day;
	}
	/**
	 * 
	 * 描 述：计算出离beginDate日期datas天的日期,若datas小于0表示当前日期之前datas天，若datas大于0表当前日期之后datas天<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * @param beginDate
	 * @param datas
	 * @return
	 */
	public static Date getDate(Date beginDate, int datas) { 
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beginDate);
		calendar.add(Calendar.DATE, datas);
		Date preDate = calendar.getTime();
		return preDate;
	  }
	public static Date getDateBySecond(Date beginDate, int second) { 
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beginDate);
		calendar.add(Calendar.SECOND, second);
		Date preDate = calendar.getTime();
		return preDate;
	  }
	/**
	 * 
	 * 描 述：计算出离beginDate日期datas天的日期,若datas小于0表示当前日期之前datas天<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * @param beginDate
	 * @param datas
	 * @return
	 */
	public static Date getBeforeDate(Date beginDate, int datas) { 
	    Calendar calendar=Calendar.getInstance(); 
	    calendar.setTime(beginDate); 
	    calendar.add(GregorianCalendar.DATE, datas);  
	    return calendar.getTime(); 
	  } 
	/**
	 * 
	 * 描 述：计算出离beginDate日期month天的日期,若month小于0表示当前日期之前month月<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * @param beginDate 当前日期
	 * @param month 上几个月
	 * @return
	 */
	public static Date getBeforeMonth(Date beginDate, int month) { 
	    Calendar calendar=Calendar.getInstance(); 
	    calendar.setTime(beginDate); 
	    calendar.add(GregorianCalendar.MONTH, month);
	    return calendar.getTime(); 
	}
	/**
	 * 描 述：获取一天的开始时间<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * @param calendar
	 * @return
	 */
	public static  Date getStartOfDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTime();
	}
	/**
	 * 描 述：获取一天的结束时间<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * @param calendar
	 * @return
	 */
	public static  Date getEndOfDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        return calendar.getTime();
	}
	
	/**
	 * 描 述：获得指定日期的后一天<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * @param specifiedDay 传过来的日期字符串
	 * @return 返回参数日期的下一天
	 * @throws Exception
	 */
	public static String getSpecifiedDayAfter(String specifiedDay) {
		
		Calendar c = Calendar.getInstance(); 
		
		try {
			Date date = new SimpleDateFormat(DateUtils.FORMAT_DATE_01).parse(specifiedDay); 
			c.setTime(date); 
			
			int day=c.get(Calendar.DATE); 
			c.set(Calendar.DATE,day+1); 
		} catch (final Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		return new SimpleDateFormat(DateUtils.FORMAT_DATE_01).format(c.getTime());
	} 
	
	/**
	 * 
	 * 描 述：当前日期是否是当月第一周<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * @return true:是当月第一周,false:不是当月第一周
	 */
	public static Boolean isFirstWeekInMonth(){
		Calendar cale = Calendar.getInstance();
		int wek =cale.get(Calendar.DAY_OF_WEEK_IN_MONTH);
		return wek ==1 ? true : false;
	}
	
	/**
	 * 描 述：获取当前时间的第一天<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * @param calendar
	 * @return
	 */
	public static  Date getFirstDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        return c.getTime();
	}
	
	/**
	 * 描 述：获取当前时间的最后一天<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * @param calendar
	 * @return
	 */
	public static  Date getLastDayOfMonth(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return ca.getTime();
	}
	
	private static final long DAY_TIME=24*60*60*1000;
	
	public static Boolean isDifferenceDay(Date date1,Date date2,int day){
		long difference = date1.getTime()-date2.getTime();
		return Math.abs(difference/DAY_TIME)>=day;
	}
	
	public static Date getUTCTimeString() {
		//取得本地时间：
		final Calendar cal = Calendar.getInstance();
		//取得时间偏移量： 
		final int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
		//取得夏令时差： 
		final int dstOffset = cal.get(Calendar.DST_OFFSET);
		//从本地时间里扣除这些差量，即可以取得UTC时间：
		cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		Date dt = cal.getTime();
		return dt;
	}
	
	
	
	    
}
