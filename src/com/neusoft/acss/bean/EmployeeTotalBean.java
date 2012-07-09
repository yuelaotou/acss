package com.neusoft.acss.bean;

public class EmployeeTotalBean {

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
	 * 迟到次数
	 */
	private int c_late;

	/**
	 * 早退次数
	 */
	private int c_early;

	/**
	 * 请假天数
	 */
	private int c_leave;

	/**
	 * 加班天数
	 */
	private int c_overday;

	/**
	 * 加班时间，以小时计算
	 */
	private int overtime;

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

	public int getC_late() {
		return c_late;
	}

	public void setC_late(int c_late) {
		this.c_late = c_late;
	}

	public int getC_early() {
		return c_early;
	}

	public void setC_early(int c_early) {
		this.c_early = c_early;
	}

	public int getC_leave() {
		return c_leave;
	}

	public void setC_leave(int c_leave) {
		this.c_leave = c_leave;
	}

	public int getC_overday() {
		return c_overday;
	}

	public void setC_overday(int c_overday) {
		this.c_overday = c_overday;
	}

	public int getOvertime() {
		return overtime;
	}

	public void setOvertime(int overtime) {
		this.overtime = overtime;
	}

}
