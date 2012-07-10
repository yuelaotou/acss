package com.neusoft.acss.bean;

import java.io.Serializable;

/**
 * <p> Title: [职工基本实体类]</p>
 * <p> Description: [职工基本实体类，供{@link EmployeeDetailBean},{@link EmployeeTotalBean} 继承]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public class EmployeeDomain implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 公司
	 */
	private String company;

	/**
	 * 部门
	 */
	private String department;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 归属地
	 */
	private String locale;

	/**
	 * 登记号
	 */
	private int id;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
