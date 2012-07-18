package com.neusoft.acss.test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Test {

	public static void main(String[] args) {

	}

	/**
	 * <p>Description:[把EmployeeDetailBean的属性作为map对象整理，以备后续生成DetailExcel用。可以修改这个Map设置导出那些列]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static Map<String, Object[]> getPropertyMap() throws ClassNotFoundException {
		HashMap<String, Object[]> m = new LinkedHashMap<String, Object[]>();
		m.put("company", new Object[] { Class.forName("java.lang.String"), "公司", "generateCompany" });
		m.put("department", new Object[] { Class.forName("java.lang.String"), "部门", "generateDepartment" });
		m.put("name", new Object[] { Class.forName("java.lang.String"), "姓名", "generateName" });
		m.put("locale", new Object[] { Class.forName("java.lang.String"), "归属地", "generateLocale" });
		m.put("id", new Object[] { Class.forName("java.lang.Integer"), "登记号", "generateId" });
		m.put("date", new Object[] { Class.forName("java.lang.String"), "打卡日期", "generateDate" });
		m.put("week", new Object[] { Class.forName("com.neusoft.acss.enums.Week"), "星期", "generateWeek" });
		m.put("rest", new Object[] { Class.forName("java.lang.String"), "是否休息日", "generateRest" });
		m.put("tmorning", new Object[] { Class.forName("java.lang.String"), "上班时间", "generateTmorning" });
		m.put("tnooningA", new Object[] { Class.forName("java.lang.String"), "午休开始时间", "generateTnooningA" });
		m.put("tnooningB", new Object[] { Class.forName("java.lang.String"), "午休结束时间", "generateTnooningB" });
		m.put("tevening", new Object[] { Class.forName("java.lang.String"), "下班时间", "generateTevening" });
		m.put("workSt", new Object[] { Class.forName("com.neusoft.acss.enums.WorkSt"), "工作情况", "generateWorkSt" });
		m.put("tlate", new Object[] { Class.forName("java.lang.String"), "迟到时间", "generateTlate" });
		m.put("tearly", new Object[] { Class.forName("java.lang.String"), "早退时间", "generateTearly" });
		m.put("evection_locale",
				new Object[] { Class.forName("java.lang.String"), "本地出差地点", "generateEvection_locale" });
		m.put("evection_remote",
				new Object[] { Class.forName("java.lang.String"), "异地出差地点", "generateEvection_remote" });
		m.put("overtime", new Object[] { Class.forName("com.neusoft.acss.enums.Overtime"), "加班类型", "generateOvertime" });
		m.put("tovertime", new Object[] { Class.forName("java.lang.String"), "加班时间", "generateTovertime" });
		m.put("leave", new Object[] { Class.forName("com.neusoft.acss.enums.Leave"), "请假类型", "generateLeave" });
		m.put("exception", new Object[] { Class.forName("java.lang.String"), "异常情况", "generateException" });

		return m;
	}

	/**
	 * <p>Description:[把EmployeeTotalBean的属性作为map对象整理，以备后续生成TotalExcel用。可以修改这个Map设置导出那些列]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public static Map<String, Object[]> getPropertyMaps() throws ClassNotFoundException {
		Map<String, Object[]> m = new LinkedHashMap<String, Object[]>();
		m.put("company", new Object[] { Class.forName("java.lang.String"), "公司", "generateCompany" });
		m.put("department", new Object[] { Class.forName("java.lang.String"), "部门", "generateDepartment" });
		m.put("name", new Object[] { Class.forName("java.lang.String"), "姓名", "generateName" });
		m.put("locale", new Object[] { Class.forName("java.lang.String"), "归属地", "generateLocale" });
		m.put("id", new Object[] { Class.forName("java.lang.Integer"), "登记号", "generateId" });
		m.put("c_late", new Object[] { Class.forName("java.lang.Integer"), "迟到次数", "generateC_late" });
		m.put("c_early", new Object[] { Class.forName("java.lang.Integer"), "早退次数", "generateC_early" });
		// leave
		m.put("c_sick", new Object[] { Class.forName("java.lang.Integer"), "病假（天）", "generateC_sick" });
		m.put("c_thing", new Object[] { Class.forName("java.lang.Integer"), "事假（天）", "generateC_thing" });
		m.put("c_year", new Object[] { Class.forName("java.lang.Integer"), "年假（天）", "generateC_year" });
		m.put("c_other", new Object[] { Class.forName("java.lang.Integer"), "其他假期（天）", "generateC_other" });
		// evection
		m.put("c_e_locale", new Object[] { Class.forName("java.lang.Integer"), "本地出差（天）", "generateC_e_locale" });
		m.put("c_e_remote", new Object[] { Class.forName("java.lang.Integer"), "外地出差（天）", "generateC_e_remote" });
		// overtime
		m.put("c_o_workday", new Object[] { Class.forName("java.lang.Integer"), "工作日加班（次）", "generateC_o_workday" });
		m.put("c_o_weekend", new Object[] { Class.forName("java.lang.Integer"), "周末加班（次）", "generateC_o_weekend" });
		m.put("c_o_holiday", new Object[] { Class.forName("java.lang.Integer"), "法定假日加班（次）", "generateC_o_holiday" });
		m.put("c_o_remote", new Object[] { Class.forName("java.lang.Integer"), "外地加班（次）", "generateC_o_remote" });
		m.put("h_overtime", new Object[] { Class.forName("java.lang.Integer"), "加班时间（小时）", "generateH_overtime" });
		m.put("c_exception", new Object[] { Class.forName("java.lang.Integer"), "异常情况（次）", "generateC_exception" });
		return m;
	}
}
