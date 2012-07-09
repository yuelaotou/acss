package com.neusoft.acss.bean;

import com.neusoft.acss.enums.WorkStatus;

public class EmployeeDetailBean {

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
	 * 上班时间
	 */
	private String beginTime;

	/**
	 * 下班时间
	 */
	private String endTime;

	/**
	 * 状态
	 */
	private WorkStatus status;

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

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public WorkStatus getStatus() {
		return status;
	}

	public void setStatus(WorkStatus status) {
		this.status = status;
	}

}
