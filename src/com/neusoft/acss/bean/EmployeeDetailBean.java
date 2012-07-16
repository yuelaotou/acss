package com.neusoft.acss.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.acss.bs.Business;
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
public class EmployeeDetailBean {

	private Object object = null;

	private BeanMap beanMap = null;

	public EmployeeDetailBean(Map<String, Object[]> propertyMap) {
		this.object = generateBean(propertyMap);
		this.beanMap = BeanMap.create(this.object);
	}

	/** 
	  * 给EmployeeDetailBean属性赋值 
	  * @param property 属性名 
	  * @param value 值 
	  */
	public void setValue(String property, Object value) {
		beanMap.put(property, value);
	}

	/** 
	  * 通过属性名得到属性值 
	  * @param property 属性名 
	  * @return 值 
	  */
	public Object getValue(String property) {
		return beanMap.get(property);
	}

	/** 
	  * 得到该实体bean对象 
	  * @return 
	  */
	public Object getObject() {
		return this.object;
	}

	/** 
	 * @param propertyMap 
	 * @return 
	 */
	private Object generateBean(Map<String, Object[]> m) {
		BeanGenerator generator = new BeanGenerator();
		Iterator<Entry<String, Object[]>> it = m.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object[]> entry = it.next();
			Object key = entry.getValue();
			Object[] values = entry.getValue();
			generator.addProperty(key.toString(), (Class<?>) values[0]);
		}
		return generator.create();
	}

	/**
	 * <p>Discription:[把EmployeeDetailBean的属性作为map对象整理，以备后续生成DetailExcel用。可以修改这个Map设置导出那些列]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static Map<String, Object[]> getPropertyMap() throws ClassNotFoundException {
		HashMap<String, Object[]> m = new HashMap<String, Object[]>();
		m.put("company", new Object[] { Class.forName("java.lang.String"), "公司", "generateCompany" });
		m.put("department", new Object[] { Class.forName("java.lang.String"), "部门", "generateDepartment" });
		m.put("name", new Object[] { Class.forName("java.lang.String"), "姓名", "generateName" });
		m.put("locale", new Object[] { Class.forName("java.lang.String"), "归属地", "generateLocale" });
		m.put("id", new Object[] { Class.forName("java.lang.Integer"), "登记号", "generateId" });
		m.put("week", new Object[] { Class.forName("com.neusoft.acss.enums.Week"), "星期", "generateWeek" });
		m.put("isRest", new Object[] { Class.forName("java.lang.Boolean"), "是否休息日", "generateIsRest" });
		m.put("tMorning", new Object[] { Class.forName("java.lang.String"), "上班时间", "generateTMorning" });
		m.put("tNooningA", new Object[] { Class.forName("java.lang.String"), "午休开始时间", "generateTNooningA" });
		m.put("tNooningB", new Object[] { Class.forName("java.lang.String"), "午休结束时间", "generateTNooningB" });
		m.put("tEvening", new Object[] { Class.forName("java.lang.String"), "下班时间", "generateTEvening" });
		m.put("workSt", new Object[] { Class.forName("com.neusoft.acss.enums.WorkSt"), "工作情况", "generateWorkSt" });
		m.put("tLate", new Object[] { Class.forName("java.lang.String"), "迟到时间", "generateTLate" });
		m.put("tEarly", new Object[] { Class.forName("java.lang.String"), "下班时间", "generateTEarly" });
		m.put("evection_locale",
				new Object[] { Class.forName("java.lang.String"), "本地出差地点", "generateEvection_locale" });
		m.put("evection_remote",
				new Object[] { Class.forName("java.lang.String"), "异地出差地点", "generateEvection_remote" });
		m.put("overtime", new Object[] { Class.forName("com.neusoft.acss.enums.Overtime"), "加班类型", "generateOvertime" });
		m.put("tOvertime", new Object[] { Class.forName("java.lang.String"), "加班时间", "generateTOvertime" });
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
		return edb.getValue("company") == null ? "" : edb.getValue("company").toString();
	}

	/**
	 * <p>Discription:[部门]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateDepartment(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		return edb.getValue("department") == null ? "" : edb.getValue("department").toString();
	}

	/**
	 * <p>Discription:[姓名]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateName(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		return edb.getValue("name") == null ? "" : edb.getValue("name").toString();
	}

	/**
	 * <p>Discription:[归属地处理]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateLocale(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		return edb.getValue("locale") == null ? "归属地" : edb.getValue("locale").toString();
	}

	/**
	 * <p>Discription:[获得登记号,目前为空,以后再处理.]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public Integer generateId(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		return edb.getValue("id") == null ? new Integer(0) : new Integer(edb.getValue("name").toString());
	}

	/**
	 * <p>Discription:[返回当天是星期几]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public Week generateWeek(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		return (Week) edb.getValue("Week");
	}

	/**
	 * <p>Discription:[是否休息日]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public boolean generateIsRest(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		return edb.getValue("isRest") == null ? true : false;
	}

	/**
	 * <p>Discription:[获得上班时间]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateTMorning(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		return edb.getValue("tMorning") == null ? "" : edb.getValue("tMorning").toString();
	}

	/**
	 * <p>Discription:[获得午休开始时间]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateTNooningA(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		return edb.getValue("tNooningA") == null ? "" : edb.getValue("tNooningA").toString();
	}

	/**
	 * <p>Discription:[获得午休结束时间]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateTNooningB(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		return edb.getValue("tNooningB") == null ? "" : edb.getValue("tNooningB").toString();
	}

	/**
	 * <p>Discription:[获得下班打卡时间]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateTEvening(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		return edb.getValue("tEvening") == null ? "" : edb.getValue("tEvening").toString();
	}

	/**
	 * <p>Discription:[处理工作情况，Workst]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public WorkSt generateWorkSt(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		if (evb == null)
			return null;
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
	public String generateTLate(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		if (edb.getValue("tMorning") == null || edb.getValue("tNooningA") == null || edb.getValue("tNooningB") == null
				|| edb.getValue("tEvening") == null) {
			return "";
		} else {
			int t = Business.minusDate(tmorning, edb.getValue("tMorning").toString(), 1000 * 60);
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
	public String generateTEarly(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		if (edb.getValue("tMorning") == null || edb.getValue("tNooningA") == null || edb.getValue("tNooningB") == null
				|| edb.getValue("tEvening") == null) {
			return "";
		} else {
			int t = Business.minusDate(edb.getValue("tEvening").toString(), tevening, 1000 * 60);
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
		return evb.getEvection_locale();
	}

	/**
	 * <p>Discription:[得到外地加班地点，根据业务需要本方法可能会变更]</p>
	 * Created on 2012-7-11
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateEvection_remote(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
			String tnoon_begin, String tnoon_end) {
		return evb.getEvection_remote();
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
				if (Boolean.valueOf(edb.getValue("isRest").toString())) {
					return Overtime.HOLIDAY;
				} else {
					return Overtime.WORKDAY;
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
	public String generateTOvertime(EmployeeDetailBean edb, EvectionBean evb, String tmorning, String tevening,
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

		if (Boolean.valueOf(edb.getValue("isRest").toString())) {
			if (edb.getValue("tMorning") != null && edb.getValue("tNooningA") != null
					&& edb.getValue("tNooningB") != null && edb.getValue("tEvening") != null) {
				return "异常";
			}
		} else {
			if (edb.getValue("tMorning") == null || edb.getValue("tNooningA") == null
					|| edb.getValue("tNooningB") == null || edb.getValue("tEvening") == null) {
				return "异常";
			}
		}
		return null;
	}
}
