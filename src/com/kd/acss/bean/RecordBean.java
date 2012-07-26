package com.kd.acss.bean;

import com.kd.acss.enums.Week;

/**
 * <p> Title: [员工打卡详细信息实体]</p>
 * <p> Description: [员工打卡详细信息实体，把txt中所有内容存储到RecordBean实体中]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * @version 1.0
 */
public class RecordBean {

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 打卡日期
	 */
	private String date;

	/**
	 * 星期
	 */
	private Week week;

	/**
	 * 是否休息日
	 */
	private String rest;

	/**
	 * 上班时间
	 */
	private String tmorning;

	/**
	 * 午休开始时间
	 */
	private String tnooningA;

	/**
	 * 午休结束时间
	 */
	private String tnooningB;

	/**
	 * 下班时间
	 */
	private String tevening;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Week getWeek() {
		return week;
	}

	public void setWeek(Week week) {
		this.week = week;
	}

	public String getRest() {
		return rest;
	}

	public void setRest(String rest) {
		this.rest = rest;
	}

	public String getTmorning() {
		return tmorning;
	}

	public void setTmorning(String tmorning) {
		this.tmorning = tmorning;
	}

	public String getTnooningA() {
		return tnooningA;
	}

	public void setTnooningA(String tnooningA) {
		this.tnooningA = tnooningA;
	}

	public String getTnooningB() {
		return tnooningB;
	}

	public void setTnooningB(String tnooningB) {
		this.tnooningB = tnooningB;
	}

	public String getTevening() {
		return tevening;
	}

	public void setTevening(String tevening) {
		this.tevening = tevening;
	}

	@Override
	public String toString() {
		return "RecordBean [name=" + name + ", date=" + date + ", week=" + week + ", tmorning=" + tmorning
				+ ", tnooningA=" + tnooningA + ", tnooningB=" + tnooningB + ", tevening=" + tevening + "]";
	}

	public boolean isEmpty() {
		return "".equals(tmorning + tnooningA + tnooningB + tevening);
	}

}
