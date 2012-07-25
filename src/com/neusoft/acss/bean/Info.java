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
	 * 上班日实体类
	 */
	private Workday workday = null;

	/*
	 * 周末实体类
	 */
	private Weekend weekend = null;

	/*
	 * 法定假日实体类
	 */
	private Holiday holiday = null;

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
	 * 周末实体集合
	 */
	private List<Weekend> weekendList = null;

	/*
	 * 上班日实体集合
	 */
	private List<Workday> workdayList = null;

	/*
	 * 法定假日实体集合
	 */
	private List<Holiday> holidayList = null;

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

	public Weekend getWeekend() {
		return weekend;
	}

	public void setWeekend(Weekend weekend) {
		this.weekend = weekend;
	}

	public Workday getWorkDay() {
		return workday;
	}

	public void setWorkDay(Workday workday) {
		this.workday = workday;
	}

	public Holiday getHoliday() {
		return holiday;
	}

	public void setHoliday(Holiday holiday) {
		this.holiday = holiday;
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

	public List<Workday> getWorkDayList() {
		return workdayList;
	}

	public void setWorkDayList(List<Workday> workdayList) {
		this.workdayList = workdayList;
	}

	public List<Weekend> getWeekendList() {
		return weekendList;
	}

	public void setWeekendList(List<Weekend> weekendList) {
		this.weekendList = weekendList;
	}

	public List<Holiday> getHolidayList() {
		return holidayList;
	}

	public void setHolidayList(List<Holiday> holidayList) {
		this.holidayList = holidayList;
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
