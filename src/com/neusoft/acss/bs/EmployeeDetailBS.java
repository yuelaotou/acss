package com.neusoft.acss.bs;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.acss.bean.EmployeeDetailBean;
import com.neusoft.acss.bean.EvectionBean;
import com.neusoft.acss.enums.Leave;
import com.neusoft.acss.enums.Overtime;
import com.neusoft.acss.enums.Week;
import com.neusoft.acss.enums.WorkSt;

public class EmployeeDetailBS {

	private static EmployeeDetailBS instance = null;

	private EmployeeDetailBS() {

	}

	public static EmployeeDetailBS getInstance() {
		if (instance == null) {
			instance = new EmployeeDetailBS();
		}
		return instance;
	}

	/**
	 * <p>Discription:[把EmployeeDetailBean的属性作为map对象整理，以备后续生成DetailExcel用。可以修改这个Map设置导出那些列]</p>
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
	 * <p>Discription:[公司]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateCompany(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		return edb.getString("company");
	}

	/**
	 * <p>Discription:[部门]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateDepartment(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		return edb.getString("department");
	}

	/**
	 * <p>Discription:[姓名]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateName(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		return edb.getString("name");
	}

	/**
	 * <p>Discription:[归属地处理]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateLocale(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		return edb.getString("locale", "归属地");
	}

	/**
	 * <p>Discription:[获得登记号,目前为空,以后再处理.]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public Integer generateId(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		return edb.isNull("id") ? new Integer(0) : new Integer(edb.getString("id"));
	}

	/**
	 * <p>Discription:[获得打卡日期]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateDate(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		return edb.getString("date");
	}

	/**
	 * <p>Discription:[返回当天是星期几]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public Week generateWeek(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		return (Week) edb.getValue("week");
	}

	/**
	 * <p>Discription:[是否休息日]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateRest(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		return edb.getString("rest");
	}

	/**
	 * <p>Discription:[获得上班时间]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateTmorning(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		return edb.getString("tmorning");
	}

	/**
	 * <p>Discription:[获得午休开始时间]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateTnooningA(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		return edb.getString("tnooningA");
	}

	/**
	 * <p>Discription:[获得午休结束时间]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateTnooningB(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		return edb.getString("tnooningB");
	}

	/**
	 * <p>Discription:[获得下班打卡时间]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateTevening(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		return edb.getString("tevening");
	}

	/**
	 * <p>Discription:[处理工作情况，Workst]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public WorkSt generateWorkSt(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		if (evb == null) {
			return null;
		}
		if (evb.getEvection_locale() != null || evb.getEvection_remote() != null) {
			return WorkSt.EVECTION;
		} else if (evb.getLeave_sick() != null || evb.getLeave_thing() != null || evb.getLeave_year() != null) {
			return WorkSt.LEAVE;
		}
		return null;
	}

	/**
	 * <p>Discription:[处理迟到时间，根据业务需要本方法可能会变更]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateTlate(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		if (edb.isNull("tmorning") || edb.isNull("tnooningA") || edb.isNull("tnooningB") || edb.isNull("tevening")) {
			return "";
		} else {
			int t = Business.minusDate(tmorning, edb.getString("tmorning"), 1000 * 60);
			if (t <= 0) {
				return "";
			} else {
				return t + "";
			}
		}
	}

	/**
	 * <p>Discription:[处理早退时间，根据业务需要本方法可能会变更]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateTearly(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		if (edb.isNull("tmorning") || edb.isNull("tnooningA") || edb.isNull("tnooningB") || edb.isNull("tevening")) {
			return "";
		} else {
			int t = Business.minusDate(edb.getString("tevening"), tevening, 1000 * 60);
			if (t <= 0) {
				return "";
			} else {
				return t + "";
			}
		}
	}

	/**
	 * <p>Discription:[得到本地加班地点，根据业务需要本方法可能会变更]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateEvection_locale(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		if (evb != null) {
			return evb.getEvection_locale();
		}
		return null;
	}

	/**
	 * <p>Discription:[得到外地加班地点，根据业务需要本方法可能会变更]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateEvection_remote(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		if (evb != null) {
			return evb.getEvection_remote();
		}
		return null;
	}

	/**
	 * <p>Discription:[处理加班类型，根据业务需要本方法可能会变更]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public Overtime generateOvertime(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		// 以后需要加入判断是周末还是法定假日
		if (evb != null) {
			if (!StringUtils.isEmpty(evb.getOvertime())) {
				if (edb.isNull("rest")) {
					return Overtime.WORKDAY;
				} else {
					return Overtime.HOLIDAY;
				}
			}
		}
		return null;
	}

	/**
	 * <p>Discription:[处理加班时间，根据业务需要本方法可能会变更]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateTovertime(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		if (evb != null) {
			if (!StringUtils.isEmpty(evb.getOvertime())) {
				return evb.getOvertime();
			}
		}
		return "";
	}

	/**
	 * <p>Discription:[处理请假类型，根据业务需要本方法可能会变更]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public Leave generateLeave(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		if (evb == null) {
			return null;
		}
		if (!StringUtils.isEmpty(evb.getLeave_sick())) {
			return Leave.SICK;
		}
		if (!StringUtils.isEmpty(evb.getLeave_thing())) {
			return Leave.THING;
		}
		if (!StringUtils.isEmpty(evb.getLeave_year())) {
			return Leave.YEAR;
		}
		return null;
	}

	/**
	 * <p>Discription:[处理异常信息，根据业务需要本方法可能会变更]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateException(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {

		if (edb.isNull("rest")) {// 说明是正常上班日
			if (edb.isNull("tmorning") || edb.isNull("tnooningA") || edb.isNull("tnooningB") || edb.isNull("tevening")) {
				return "异常";
			}
			// } else {
			// if (edb.isNull("tmorning") || edb.isNull("tnooningA") || edb.isNull("tnooningB") ||
			// edb.isNull("tevening")) {
			// return "异常";
			// }
		}
		return null;
	}
}
