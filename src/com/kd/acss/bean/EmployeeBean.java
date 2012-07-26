package com.kd.acss.bean;

/**
 * <p> Title: [员工基本信息实体类]</p>
 * <p> Description: [存储员工基本信息，包括公司，部门，归属地等等。和EvectionBean、RecordBean根据name关联]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * @version 1.0
 */
public class EmployeeBean {

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 公司
	 */
	private String company;

	/**
	 * 部门
	 */
	private String department;

	/**
	 * 归属地
	 */
	private String locale;

	/**
	 * 登记号
	 */
	private String id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "EmployeeBean [name=" + name + ", company=" + company + ", department=" + department + ", locale="
				+ locale + ", id=" + id + "]";
	}

	public boolean isEmpty() {
		return "".equals(name);
	}

}
