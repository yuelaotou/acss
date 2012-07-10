package com.neusoft.acss.bean;

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
	private String evection;

	/**
	 * 外地出差地点
	 */
	private String outPosition;

	/**
	 * 本地出差地点
	 */
	private String inPosition;

	/**
	 * 加班
	 */
	private String overtime;

	/**
	 * 病假
	 */
	private String sick_leave;

	/**
	 * 事假
	 */
	private String thing_leave;

	/**
	 * 年假
	 */
	private String year_leave;

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

	public String getEvection() {
		return evection;
	}

	public void setEvection(String evection) {
		this.evection = evection;
	}

	public String getOutPosition() {
		return outPosition;
	}

	public void setOutPosition(String outPosition) {
		this.outPosition = outPosition;
	}

	public String getInPosition() {
		return inPosition;
	}

	public void setInPosition(String inPosition) {
		this.inPosition = inPosition;
	}

	public String getOvertime() {
		return overtime;
	}

	public void setOvertime(String overtime) {
		this.overtime = overtime;
	}

	public String getSick_leave() {
		return sick_leave;
	}

	public void setSick_leave(String sick_leave) {
		this.sick_leave = sick_leave;
	}

	public String getThing_leave() {
		return thing_leave;
	}

	public void setThing_leave(String thing_leave) {
		this.thing_leave = thing_leave;
	}

	public String getYear_leave() {
		return year_leave;
	}

	public void setYear_leave(String year_leave) {
		this.year_leave = year_leave;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "EvectionBean [name=" + name + ", month=" + month + ", day=" + day + ", evection=" + evection
				+ ", outPosition=" + outPosition + ", inPosition=" + inPosition + ", overtime=" + overtime
				+ ", sick_leave=" + sick_leave + ", thing_leave=" + thing_leave + ", year_leave=" + year_leave + "]";
	}

	public boolean isEmpty() {
		return "".equals(evection + outPosition + inPosition + overtime + sick_leave + thing_leave + year_leave);
	}

}
