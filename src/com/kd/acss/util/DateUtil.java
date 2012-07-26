package com.kd.acss.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.kd.acss.exception.BizException;

/**
 * <p> Title: [日期工具类]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * @version 1.0
 */
public final class DateUtil {

	/**
	 * 隐藏公用构造方法<p>
	 */
	private DateUtil() {
		// noop
	}

	/**
	 * <p>Description:[时间增加分钟]</p>
	 * Created on 2012-7-9
	 */
	public static String addMinutes(String time, String min) {
		try {
			Date d = DateUtils.addMinutes(DateUtils.parseDate(time, "HH:mm:ss"), Integer.parseInt(min));
			return DateFormatUtils.format(d, "HH:mm:ss");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <p>Description:[时间减少分钟]</p>
	 * Created on 2012-7-9
	 */
	public static String minusMinutes(String time, String min) {
		try {
			Date d = DateUtils.addMinutes(DateUtils.parseDate(time, "HH:mm:ss"), -Integer.parseInt(min));
			return DateFormatUtils.format(d, "HH:mm:ss");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <p>Description:[计算两个时间之间相差多少，n是按小时或分钟计算:Calendar.MINUTE,Calendar.HOUR]</p>
	 * Created on 2012-7-2
	 */
	public static int minusDate(String beginTime, String endTime, int n) {
		if (n == Calendar.SECOND) {
			n = 1000;
		}
		if (n == Calendar.MINUTE) {
			n = 1000 * 60;
		}
		if (n == Calendar.HOUR) {
			n = 1000 * 60 * 60;
		}
		long begin = 0;
		long end = 0;
		try {
			begin = DateUtils.parseDate(beginTime, "HH:mm:ss").getTime();
			end = DateUtils.parseDate(endTime, "HH:mm:ss").getTime();
		} catch (ParseException e) {
			throw new BizException("计算时间差出错:" + beginTime + "，" + endTime, e);
		}

		if (end > begin) {
			return (int) ((end - begin) / n + 1);
		} else {
			return (int) ((end - begin) / n - 1);
		}
	}

}
