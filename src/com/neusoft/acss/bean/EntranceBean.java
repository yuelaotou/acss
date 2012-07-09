package com.neusoft.acss.bean;

public class EntranceBean {

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
	 * 打卡时间
	 */
	private String time;

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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

}
