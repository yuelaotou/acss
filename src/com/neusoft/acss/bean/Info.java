package com.neusoft.acss.bean;

import java.util.List;
import java.util.Map;

/**
 * <p> Title: [存储各种对象的超类]</p>
 * <p> Description: [本类包含各种实体对象和封装实体对象的List对象，用于在各个方法之间传输数据用。</p>
 * <p> Description: [以后扩展也只需要在这里增加属性，在具体方法中调用]</p>
 * <p> Created on 2012-7-19</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public class Info {

	/*
	 * 法定假日实体类
	 * sss
	 */
	private Vacation vacation = null;

	/*
	 * 串休日实体类
	 */
	private WorkDay workDay = null;

	/*
	 * 员工基本信息实体类
	 */
	private EmployeeBean employeeBean = null;

	/*
	 * 员工打卡详细信息实体
	 */
	private RecordBean recordBean = null;

	/*
	 * 出差登记表实体类
	 */
	private EvectionBean evectionBean = null;

	/*
	 * 法定假日实体集合
	 */
	private List<Vacation> vacationList = null;

	/*
	 * 串休日实体集合
	 */
	private List<WorkDay> workDayList = null;

	/*
	 * 员工基本信息实体集合
	 */
	private List<EmployeeBean> employeeBeanList = null;

	/*
	 * 员工打卡详细信息实体集合
	 */
	private List<RecordBean> recordBeanList = null;

	/*
	 * 出差登记表实体集合
	 */
	private List<EvectionBean> evectionBeanList = null;

	/*
	 * 详细信息集合
	 */
	private List<Map<String, String>> detailList = null;

	/*
	 * 每人每月详细信息集合
	 */
	private List<Map<String, String>> subList = null;

	/*
	 * 统计信息集合
	 */
	private List<Map<String, String>> totalList = null;

	/*
	 * 操作提示消息
	 */
	private String message = null;

	public Vacation getVacation() {
		return vacation;
	}

	public void setVacation(Vacation vacation) {
		this.vacation = vacation;
	}

	public WorkDay getWorkDay() {
		return workDay;
	}

	public void setWorkDay(WorkDay workDay) {
		this.workDay = workDay;
	}

	public EmployeeBean getEmployeeBean() {
		return employeeBean;
	}

	public void setEmployeeBean(EmployeeBean employeeBean) {
		this.employeeBean = employeeBean;
	}

	public RecordBean getRecordBean() {
		return recordBean;
	}

	public void setRecordBean(RecordBean recordBean) {
		this.recordBean = recordBean;
	}

	public EvectionBean getEvectionBean() {
		return evectionBean;
	}

	public void setEvectionBean(EvectionBean evectionBean) {
		this.evectionBean = evectionBean;
	}

	public List<Vacation> getVacationList() {
		return vacationList;
	}

	public void setVacationList(List<Vacation> vacationList) {
		this.vacationList = vacationList;
	}

	public List<WorkDay> getWorkDayList() {
		return workDayList;
	}

	public void setWorkDayList(List<WorkDay> workDayList) {
		this.workDayList = workDayList;
	}

	public List<EmployeeBean> getEmployeeBeanList() {
		return employeeBeanList;
	}

	public void setEmployeeBeanList(List<EmployeeBean> employeeBeanList) {
		this.employeeBeanList = employeeBeanList;
	}

	public List<RecordBean> getRecordBeanList() {
		return recordBeanList;
	}

	public void setRecordBeanList(List<RecordBean> recordBeanList) {
		this.recordBeanList = recordBeanList;
	}

	public List<EvectionBean> getEvectionBeanList() {
		return evectionBeanList;
	}

	public void setEvectionBeanList(List<EvectionBean> evectionBeanList) {
		this.evectionBeanList = evectionBeanList;
	}

	public List<Map<String, String>> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<Map<String, String>> detailList) {
		this.detailList = detailList;
	}

	public List<Map<String, String>> getSubList() {
		return subList;
	}

	public void setSubList(List<Map<String, String>> subList) {
		this.subList = subList;
	}

	public List<Map<String, String>> getTotalList() {
		return totalList;
	}

	public void setTotalList(List<Map<String, String>> totalList) {
		this.totalList = totalList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
