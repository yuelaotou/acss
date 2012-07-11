package com.neusoft.acss.bean;

import java.util.LinkedHashMap;
import java.util.Map;

import com.neusoft.acss.enums.Leave;
import com.neusoft.acss.enums.Overtime;
import com.neusoft.acss.enums.Week;
import com.neusoft.acss.enums.WorkSt;

/**
 * <p> Title: [员工打卡详细信息实体]</p>
 * <p> Description: [员工每月详细打卡信息实体，存储Excel每行记录]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public class EmployeeDetailBean extends EmployeeDomain {

	private static final long serialVersionUID = 1L;

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
	 * 是就写休息，否则为空
	 */
	private String isRest;

	/**
	 * 上班时间
	 */
	private String tMorning;

	/**
	 * 午休开始时间
	 */
	private String tNooningA;

	/**
	 * 午休结束时间
	 */
	private String tNooningB;

	/**
	 * 下班时间
	 */
	private String tEvening;

	/**
	 * 工作情况
	 */
	private WorkSt workSt;

	/**
	 * 迟到时间
	 */
	private String tLate;

	/**
	 * 早退时间
	 */
	private String tEarly;

	/**
	 * 本地出差地点
	 */
	private String evection_locale;

	/**
	 * 异地出差地点
	 */
	private String evection_remote;

	/**
	 * 加班类型
	 */
	private Overtime overtime;

	/**
	 * 加班时间
	 */
	private String tOvertime;

	/**
	 * 请假类型
	 */
	private Leave leave;

	/**
	 * 异常情况
	 */
	private String exception;

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

	public String getIsRest() {
		return isRest;
	}

	public void setIsRest(String isRest) {
		this.isRest = isRest;
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

	public WorkSt getWorkSt() {
		return workSt;
	}

	public void setWorkSt(WorkSt workSt) {
		this.workSt = workSt;
	}

	public String getTLate() {
		return tLate;
	}

	public void setTLate(String tLate) {
		this.tLate = tLate;
	}

	public String getTEarly() {
		return tEarly;
	}

	public void setTEarly(String tEarly) {
		this.tEarly = tEarly;
	}

	public String getEvection_locale() {
		return evection_locale;
	}

	public void setEvection_locale(String evection_locale) {
		this.evection_locale = evection_locale;
	}

	public String getEvection_remote() {
		return evection_remote;
	}

	public void setEvection_remote(String evection_remote) {
		this.evection_remote = evection_remote;
	}

	public Overtime getOvertime() {
		return overtime;
	}

	public void setOvertime(Overtime overtime) {
		this.overtime = overtime;
	}

	public String getTOvertime() {
		return tOvertime;
	}

	public void setTOvertime(String tOvertime) {
		this.tOvertime = tOvertime;
	}

	public Leave getLeave() {
		return leave;
	}

	public void setLeave(Leave leave) {
		this.leave = leave;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	@Override
	public String toString() {
		return "EmployeeDetailBean [" + (date != null ? "date=" + date + ", " : "")
				+ (week != null ? "week=" + week + ", " : "") + (isRest != null ? "isRest=" + isRest + ", " : "")
				+ (tMorning != null ? "tMorning=" + tMorning + ", " : "")
				+ (tNooningA != null ? "tNooningA=" + tNooningA + ", " : "")
				+ (tNooningB != null ? "tNooningB=" + tNooningB + ", " : "")
				+ (tEvening != null ? "tEvening=" + tEvening + ", " : "")
				+ (workSt != null ? "workSt=" + workSt + ", " : "") + (tLate != null ? "tLate=" + tLate + ", " : "")
				+ (tEarly != null ? "tEarly=" + tEarly + ", " : "")
				+ (evection_locale != null ? "evection_locale=" + evection_locale + ", " : "")
				+ (evection_remote != null ? "evection_remote=" + evection_remote + ", " : "")
				+ (overtime != null ? "overtime=" + overtime + ", " : "")
				+ (tOvertime != null ? "tOvertime=" + tOvertime + ", " : "")
				+ (leave != null ? "leave=" + leave + ", " : "") + (exception != null ? "exception=" + exception : "")
				+ "]";
	}

	/**
	 * <p>Discription:[把EmployeeDetailBean的属性作为map对象整理，以备后续生成DetailExcel用。可以修改这个Map设置导出那些列]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 * @throws:
	 */
	public static Map<String, String> getDetailMap() {
		Map<String, String> detailMap = new LinkedHashMap<String, String>();
		detailMap.put("company", "公司");
		detailMap.put("department", "部门");
		detailMap.put("name", "姓名");
		detailMap.put("locale", "归属地");
		detailMap.put("id", "登记号");
		detailMap.put("date", "打卡日期");
		detailMap.put("week", "星期");
		detailMap.put("isRest", "是否休息日");
		detailMap.put("tMorning", "上班时间");
		detailMap.put("tNooningA", "午休开始时间");
		detailMap.put("tNooningB", "午休结束时间");
		detailMap.put("tEvening", "下班时间");
		detailMap.put("workSt", "工作情况");
		detailMap.put("tLate", "迟到时间");
		detailMap.put("tEarly", "早退时间");
		detailMap.put("evection_locale", "本地出差地点");
		detailMap.put("evection_remote", "异地出差地点");
		detailMap.put("overtime", "加班类型");
		detailMap.put("tOvertime", "加班时间");
		detailMap.put("leave", "请假类型");
		detailMap.put("exception", "异常情况");
		return detailMap;
	}
}
