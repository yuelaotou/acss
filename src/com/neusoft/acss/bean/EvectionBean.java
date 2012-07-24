package com.neusoft.acss.bean;

import org.apache.commons.lang3.StringUtils;

/**
 * <p> Title: [出差登记表实体类]</p>
 * <p> Description: [出差登记表实体，把Excel中所有内容存储到EvectionBean实体中]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public class EvectionBean {

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 月份
	 */
	private String month;

	/**
	 * 日期
	 */
	private String day;

	/**
	 * 出差时间
	 */
	private String tevection;

	/**
	 * 外地出差地点
	 */
	private String evection_remote;

	/**
	 * 本地出差地点
	 */
	private String evection_locale;

	/**
	 * 加班
	 */
	private String overtime;

	/**
	 * 病假
	 */
	private String leave_sick;

	/**
	 * 事假
	 */
	private String leave_thing;

	/**
	 * 年假
	 */
	private String leave_year;

	/**
	 * 签字，并不会用到，只是预留。
	 */
	private String sign;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getTevection() {
		return tevection;
	}

	public void setTevection(String tevection) {
		this.tevection = tevection;
	}

	public String getEvection_remote() {
		return evection_remote;
	}

	public void setEvection_remote(String evection_remote) {
		this.evection_remote = evection_remote;
	}

	public String getEvection_locale() {
		return evection_locale;
	}

	public void setEvection_locale(String evection_locale) {
		this.evection_locale = evection_locale;
	}

	public String getOvertime() {
		return overtime;
	}

	public void setOvertime(String overtime) {
		this.overtime = overtime;
	}

	public String getLeave_sick() {
		return leave_sick;
	}

	public void setLeave_sick(String leave_sick) {
		this.leave_sick = leave_sick;
	}

	public String getLeave_thing() {
		return leave_thing;
	}

	public void setLeave_thing(String leave_thing) {
		this.leave_thing = leave_thing;
	}

	public String getLeave_year() {
		return leave_year;
	}

	public void setLeave_year(String leave_year) {
		this.leave_year = leave_year;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "EvectionBean [name=" + name + ", month=" + month + ", day=" + day + ", tevection=" + tevection
				+ ", evection_locale=" + evection_locale + ", evection_remote=" + evection_remote + ", overtime="
				+ overtime + ", leave_sick=" + leave_sick + ", leave_thing=" + leave_thing + ", leave_year="
				+ leave_year + "]";
	}

	public boolean isEmpty() {
		return "".equals(tevection + evection_locale + evection_remote + overtime + leave_sick + leave_thing
				+ leave_year);
	}

	/**
	 * <p>Discription:[出差时间、病假、事假、年假是否有和type相同的。type一般为全天、上午、下午]</p>
	 * Created on 2012-7-24
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public boolean hasType(String type) {
		if (tevection != null && tevection.equals(type)) {
			return true;
		}
		if (leave_sick != null && leave_sick.equals(type)) {
			return true;
		}
		if (leave_thing != null && leave_thing.equals(type)) {
			return true;
		}
		if (leave_year != null && leave_year.equals(type)) {
			return true;
		}
		return false;
	}

	/**
	 * <p>Discription:[出差时间、病假、事假、年假是否有时间在给定time前的。比如给定时间为08:30:00，则判定的是《是否有人在早上上班时就出差或请假了》]</p>
	 * <p>Discription:[本方法只能判断是时间段的方法。若是写着全天，上午或下午是不能正确判断的。因此调用本方法前需要判断hasType]</p>
	 * Created on 2012-7-24
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public boolean startWith(String time) {
		if (tevection != null) {
			String beforetime = StringUtils.substringBefore(tevection.replace("-", "~"), "~");
			// 包装成HH:mm:ss格式
			beforetime = StringUtils.rightPad(StringUtils.leftPad(beforetime, 5, "0"), 8, ":00");
			if (!beforetime.equals("00000:00") && beforetime.compareTo(time) <= 0) {
				return true;
			}
		}
		if (leave_sick != null) {
			String beforetime = StringUtils.substringBefore(leave_sick.replace("-", "~"), "~");
			// 包装成HH:mm:ss格式
			beforetime = StringUtils.rightPad(StringUtils.leftPad(beforetime, 5, "0"), 8, ":00");
			if (!beforetime.equals("00000:00") && beforetime.compareTo(time) <= 0) {
				return true;
			}
		}
		if (leave_thing != null) {
			String beforetime = StringUtils.substringBefore(leave_thing.replace("-", "~"), "~");
			// 包装成HH:mm:ss格式
			beforetime = StringUtils.rightPad(StringUtils.leftPad(beforetime, 5, "0"), 8, ":00");
			if (!beforetime.equals("00000:00") && beforetime.compareTo(time) <= 0) {
				return true;
			}
		}
		if (leave_year != null) {
			String beforetime = StringUtils.substringBefore(leave_year.replace("-", "~"), "~");
			// 包装成HH:mm:ss格式
			beforetime = StringUtils.rightPad(StringUtils.leftPad(beforetime, 5, "0"), 8, ":00");
			if (!beforetime.equals("00000:00") && beforetime.compareTo(time) <= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <p>Discription:[出差时间、病假、事假、年假是否有时间在给定time后的。比如给定时间为17:30:00，则判定的是《是否有人出差或请假到晚上下班》]</p>
	 * <p>Discription:[本方法只能判断是时间段的方法。若是写着全天，上午或下午是不能正确判断的。因此调用本方法前需要判断hasType]</p>
	 * Created on 2012-7-24
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public boolean endWith(String time) {
		if (tevection != null) {
			String beforetime = StringUtils.substringBefore(tevection.replace("-", "~"), "~");
			// 包装成HH:mm:ss格式
			beforetime = StringUtils.rightPad(StringUtils.leftPad(beforetime, 5, "0"), 8, ":00");
			if (!beforetime.equals("00000:00") && beforetime.compareTo(time) >= 0) {
				return true;
			}
		}
		if (leave_sick != null) {
			String beforetime = StringUtils.substringBefore(leave_sick.replace("-", "~"), "~");
			// 包装成HH:mm:ss格式
			beforetime = StringUtils.rightPad(StringUtils.leftPad(beforetime, 5, "0"), 8, ":00");
			if (!beforetime.equals("00000:00") && beforetime.compareTo(time) >= 0) {
				return true;
			}
		}
		if (leave_thing != null) {
			String beforetime = StringUtils.substringBefore(leave_thing.replace("-", "~"), "~");
			// 包装成HH:mm:ss格式
			beforetime = StringUtils.rightPad(StringUtils.leftPad(beforetime, 5, "0"), 8, ":00");
			if (!beforetime.equals("00000:00") && beforetime.compareTo(time) >= 0) {
				return true;
			}
		}
		if (leave_year != null) {
			String beforetime = StringUtils.substringBefore(leave_year.replace("-", "~"), "~");
			// 包装成HH:mm:ss格式
			beforetime = StringUtils.rightPad(StringUtils.leftPad(beforetime, 5, "0"), 8, ":00");
			if (!beforetime.equals("00000:00") && beforetime.compareTo(time) >= 0) {
				return true;
			}
		}
		return false;
	}

}
