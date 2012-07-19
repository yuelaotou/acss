package com.neusoft.acss.enums;

/**
 * <p> Title: [星期]</p>
 * <p> Description: [描述星期的Enum类]</p>
 * <p> Description: [SUN("日"), MON("一"), TUE("二"), WED("三"), THU("四"), FRI("五"), SAT("六")]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public enum Week {
	SUN("日"), MON("一"), TUE("二"), WED("三"), THU("四"), FRI("五"), SAT("六");

	private final String week;

	private Week(String week) {
		this.week = week;
	}

	@Override
	public String toString() {
		return this.week;
	}

	/**
	 * <p>Description:[根据传入的字符串转换成Week的Enum对象]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public static Week getWeek(String week) {
		if (week.equals("1") || week.equals("一")) {
			return Week.MON;
		}
		if (week.equals("2") || week.equals("二")) {
			return Week.TUE;
		}
		if (week.equals("3") || week.equals("三")) {
			return Week.WED;
		}
		if (week.equals("4") || week.equals("四")) {
			return Week.THU;
		}
		if (week.equals("5") || week.equals("五")) {
			return Week.FRI;
		}
		if (week.equals("6") || week.equals("六")) {
			return Week.SAT;
		}
		if (week.equals("7") || week.equals("日")) {
			return Week.SUN;
		}
		return null;
	}
}
