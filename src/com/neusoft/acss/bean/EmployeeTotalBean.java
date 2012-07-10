package com.neusoft.acss.bean;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p> Title: [员工打卡统计总表实体]</p>
 * <p> Description: [员工每月统计总表实体，存储Excel每行记录]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public class EmployeeTotalBean extends EmployeeDomain {

	private static final long serialVersionUID = 1L;

	/**
	 * 迟到次数
	 */
	private int c_late;

	/**
	 * 早退次数
	 */
	private int c_early;

	/**
	 * 病假（天）
	 */
	private int c_sick;

	/**
	 * 事假（天）
	 */
	private int c_thing;

	/**
	 * 年假（天）
	 */
	private int c_year;

	/**
	 * 其他假期（天）
	 */
	private int c_other;

	/**
	 * 本地出差（天）
	 */
	private int c_evection_locale;

	/**
	 * 外地出差（天）
	 */
	private int c_evection_remote;

	/**
	 * 本地加班（天）
	 */
	private int c_overtime_locale;

	/**
	 * 外地加班（天）
	 */
	private int c_overtime_remote;

	/**
	 * 加班时间（小时）
	 */
	private int h_overtime;

	/**
	 * 异常情况
	 */
	private String exception;

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

	public int getC_sick() {
		return c_sick;
	}

	public void setC_sick(int c_sick) {
		this.c_sick = c_sick;
	}

	public int getC_thing() {
		return c_thing;
	}

	public void setC_thing(int c_thing) {
		this.c_thing = c_thing;
	}

	public int getC_year() {
		return c_year;
	}

	public void setC_year(int c_year) {
		this.c_year = c_year;
	}

	public int getC_other() {
		return c_other;
	}

	public void setC_other(int c_other) {
		this.c_other = c_other;
	}

	public int getC_evection_locale() {
		return c_evection_locale;
	}

	public void setC_evection_locale(int c_evection_locale) {
		this.c_evection_locale = c_evection_locale;
	}

	public int getC_evection_remote() {
		return c_evection_remote;
	}

	public void setC_evection_remote(int c_evection_remote) {
		this.c_evection_remote = c_evection_remote;
	}

	public int getC_overtime_locale() {
		return c_overtime_locale;
	}

	public void setC_overtime_locale(int c_overtime_locale) {
		this.c_overtime_locale = c_overtime_locale;
	}

	public int getC_overtime_remote() {
		return c_overtime_remote;
	}

	public void setC_overtime_remote(int c_overtime_remote) {
		this.c_overtime_remote = c_overtime_remote;
	}

	public int getH_overtime() {
		return h_overtime;
	}

	public void setH_overtime(int h_overtime) {
		this.h_overtime = h_overtime;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	@Override
	public String toString() {
		return "EmployeeTotalBean [c_late=" + c_late + ", c_early=" + c_early + ", c_sick=" + c_sick + ", c_thing="
				+ c_thing + ", c_year=" + c_year + ", c_other=" + c_other + ", c_evection_locale=" + c_evection_locale
				+ ", c_evection_remote=" + c_evection_remote + ", c_overtime_locale=" + c_overtime_locale
				+ ", c_overtime_remote=" + c_overtime_remote + ", h_overtime=" + h_overtime + ", "
				+ (exception != null ? "exception=" + exception : "") + "]";
	}

	/**
	 * <p>Discription:[把EmployeeTotalBean的属性作为map对象整理，以备后续生成TotalExcel用。可以修改这个Map设置导出那些列]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static Map<String, String> getTotalMap() {
		Map<String, String> totalMap = new LinkedHashMap<String, String>();
		totalMap.put("company", "公司");
		totalMap.put("department", "部门");
		totalMap.put("name", "姓名");
		totalMap.put("locale", "归属地");
		totalMap.put("id", "登记号");
		totalMap.put("c_late", "迟到次数");
		totalMap.put("c_early", "早退次数");
		totalMap.put("c_sick", "病假（天）");
		totalMap.put("c_thing", "事假（天）");
		totalMap.put("c_year", "年假（天）");
		totalMap.put("c_other", "其他假期（天）");
		totalMap.put("c_evection_locale", "本地出差（天）");
		totalMap.put("c_evection_remote", "外地出差（天）");
		totalMap.put("c_overtime_locale", "本地加班（天）");
		totalMap.put("c_overtime_remote", "外地加班（天）");
		totalMap.put("h_overtime", "加班时间（小时）");
		totalMap.put("exception", "异常情况");
		return totalMap;
	}
}
