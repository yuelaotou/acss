package com.neusoft.acss.bean;

import com.neusoft.acss.enums.Week;

/**
 * <p> Title: [考勤信息实体类]</p>
 * <p> Description: [读取考勤信息文本后，把内容存储到AcssBean实体中，以后可能扩展，比如：归属地等等信息]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public class AcssBean {

	/**
	 * 部门
	 */
	private String department;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 登记号
	 */
	private int id;

	/**
	 * 打卡日期
	 */
	private String date;

	/**
	 * 星期
	 */
	private Week week;

	/**
	 * 打卡时间-上班
	 */
	private String tMorning;

	/**
	 * 打卡时间-午休A
	 */
	private String tNooningA;

	/**
	 * 打卡时间-午休B
	 */
	private String tNooningB;

	/**
	 * 打卡时间-下班
	 */
	private String tEvening;

	/**
	 * 机器号
	 */
	private int machine;

	/**
	 * 编号
	 */
	private String code;

	/**
	 * 比对方式
	 */
	private String type;

	/**
	 * 卡号
	 */
	private String cardnum;

	/**
	 * 是否假期
	 */
	private boolean isVacation = false;

	/**
	 * 是否请假
	 */
	private boolean isLeave = false;

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getTMorning() {
		return tMorning;
	}

	public void setTMorning(String tMorning) {
		this.tMorning = tMorning;
	}

	public String getTNooningA() {
		return tNooningA;
	}

	public void setTNooningA(String tNooningA) {
		this.tNooningA = tNooningA;
	}

	public String getTNooningB() {
		return tNooningB;
	}

	public void setTNooningB(String tNooningB) {
		this.tNooningB = tNooningB;
	}

	public String getTEvening() {
		return tEvening;
	}

	public void setTEvening(String tEvening) {
		this.tEvening = tEvening;
	}

	public int getMachine() {
		return machine;
	}

	public void setMachine(int machine) {
		this.machine = machine;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCardnum() {
		return cardnum;
	}

	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}

	public boolean isVacation() {
		return isVacation;
	}

	public void setVacation(boolean isVacation) {
		this.isVacation = isVacation;
	}

	public boolean isLeave() {
		return isLeave;
	}

	public void setLeave(boolean isLeave) {
		this.isLeave = isLeave;
	}

	@Override
	public String toString() {
		return "AcssBean [name=" + name + ", date=" + date + ", week=" + week + ", tMorning=" + tMorning
				+ ", tNooningA=" + tNooningA + ", tNooningB=" + tNooningB + ", tEvening=" + tEvening + ", isVacation="
				+ isVacation + "]";
	}

}
