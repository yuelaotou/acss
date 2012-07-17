package com.neusoft.acss.bs;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.acss.bean.EmployeeDetailBean;
import com.neusoft.acss.enums.Leave;
import com.neusoft.acss.enums.Overtime;

public class EmployeeTotalBS {

	private static EmployeeTotalBS instance = null;

	private EmployeeTotalBS() {

	}

	public static EmployeeTotalBS getInstance() {
		if (instance == null) {
			instance = new EmployeeTotalBS();
		}
		return instance;
	}

	/**
	 * <p>Discription:[把EmployeeTotalBean的属性作为map对象整理，以备后续生成TotalExcel用。可以修改这个Map设置导出那些列]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public static Map<String, Object[]> getPropertyMap() throws ClassNotFoundException {
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

	/**
	 * <p>Discription:[公司]</p>
	 * Created on 2012-7-17
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateCompany(List<EmployeeDetailBean> edblist) {
		EmployeeDetailBean edb = edblist.get(0);
		return edb.getString("company");
	}

	/**
	 * <p>Discription:[部门]</p>
	 * Created on 2012-7-17
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateDepartment(List<EmployeeDetailBean> edblist) {
		EmployeeDetailBean edb = edblist.get(0);
		return edb.getString("department");
	}

	/**
	 * <p>Discription:[姓名]</p>
	 * Created on 2012-7-17
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateName(List<EmployeeDetailBean> edblist) {
		EmployeeDetailBean edb = edblist.get(0);
		return edb.getString("name");
	}

	/**
	 * <p>Discription:[归属地处理]</p>
	 * Created on 2012-7-17
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateLocale(List<EmployeeDetailBean> edblist) {
		EmployeeDetailBean edb = edblist.get(0);
		return edb.getString("locale");
	}

	/**
	 * <p>Discription:[获得登记号,目前为空,以后再处理.]</p>
	 * Created on 2012-7-17
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public Integer generateId(List<EmployeeDetailBean> edblist) {
		EmployeeDetailBean edb = edblist.get(0);
		return edb.isNull("id") ? new Integer(0) : new Integer(edb.getString("id"));
	}

	/**
	 * <p>Discription:[迟到次数]</p>
	 * Created on 2012-7-17
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public Integer generateC_late(List<EmployeeDetailBean> edblist) {
		int n = 0;
		for (EmployeeDetailBean edb : edblist) {
			if (!edb.isNull("tlate")) {
				n++;
			}
		}
		return n;
	}

	/**
	 * <p>Discription:[早退次数]</p>
	 * Created on 2012-7-17
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public Integer generateC_early(List<EmployeeDetailBean> edblist) {
		int n = 0;
		for (EmployeeDetailBean edb : edblist) {
			if (!edb.isNull("tearly")) {
				n++;
			}
		}
		return n;
	}

	/**
	 * <p>Discription:[病假（天）]</p>
	 * Created on 2012-7-17
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public Integer generateC_sick(List<EmployeeDetailBean> edblist) {
		int n = 0;
		for (EmployeeDetailBean edb : edblist) {
			if (!edb.isNull("leave") && edb.getValue("leave").equals(Leave.SICK)) {
				n++;
			}
		}
		return n;
	}

	/**
	 * <p>Discription:[事假（天）]</p>
	 * Created on 2012-7-17
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public Integer generateC_thing(List<EmployeeDetailBean> edblist) {
		int n = 0;
		for (EmployeeDetailBean edb : edblist) {
			if (!edb.isNull("leave") && edb.getValue("leave").equals(Leave.THING)) {
				n++;
			}
		}
		return n;
	}

	/**
	 * <p>Discription:[年假（天）]</p>
	 * Created on 2012-7-17
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public Integer generateC_year(List<EmployeeDetailBean> edblist) {
		int n = 0;
		for (EmployeeDetailBean edb : edblist) {
			if (!edb.isNull("leave") && edb.getValue("leave").equals(Leave.YEAR)) {
				n++;
			}
		}
		return n;
	}

	/**
	 * <p>Discription:[其他假期（天）]</p>
	 * Created on 2012-7-17
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public Integer generateC_other(List<EmployeeDetailBean> edblist) {
		int n = 0;
		for (EmployeeDetailBean edb : edblist) {
			if (!edb.isNull("leave") && edb.getValue("leave").equals(Leave.OTHER)) {
				n++;
			}
		}
		return n;
	}

	/**
	 * <p>Discription:[本地出差（天）]</p>
	 * Created on 2012-7-17
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public Integer generateC_e_locale(List<EmployeeDetailBean> edblist) {
		int n = 0;
		for (EmployeeDetailBean edb : edblist) {
			if (!edb.isNull("evection_locale")) {
				n++;
			}
		}
		return n;
	}

	/**
	 * <p>Discription:[外地出差（天）]</p>
	 * Created on 2012-7-17
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public Integer generateC_e_remote(List<EmployeeDetailBean> edblist) {
		int n = 0;
		for (EmployeeDetailBean edb : edblist) {
			if (!edb.isNull("evection_remote")) {
				n++;
			}
		}
		return n;
	}

	/**
	 * <p>Discription:[工作日加班（次）]</p>
	 * Created on 2012-7-17
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public Integer generateC_o_workday(List<EmployeeDetailBean> edblist) {
		int n = 0;
		for (EmployeeDetailBean edb : edblist) {
			if (!edb.isNull("overtime") && edb.getValue("overtime").equals(Overtime.WORKDAY)) {
				n++;
			}
		}
		return n;
	}

	/**
	 * <p>Discription:[周末加班（次）]</p>
	 * Created on 2012-7-17
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public Integer generateC_o_weekend(List<EmployeeDetailBean> edblist) {
		int n = 0;
		for (EmployeeDetailBean edb : edblist) {
			if (!edb.isNull("overtime") && edb.getValue("overtime").equals(Overtime.WEEKEND)) {
				n++;
			}
		}
		return n;
	}

	/**
	 * <p>Discription:[法定假日加班（次）]</p>
	 * Created on 2012-7-17
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public Integer generateC_o_holiday(List<EmployeeDetailBean> edblist) {
		int n = 0;
		for (EmployeeDetailBean edb : edblist) {
			if (!edb.isNull("overtime") && edb.getValue("overtime").equals(Overtime.HOLIDAY)) {
				n++;
			}
		}
		return n;
	}

	/**
	 * <p>Discription:[外地加班（次）]</p>
	 * Created on 2012-7-17
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public Integer generateC_o_remote(List<EmployeeDetailBean> edblist) {
		int n = 0;
		for (EmployeeDetailBean edb : edblist) {
			if (!edb.isNull("overtime") && edb.getValue("overtime").equals(Overtime.REMOTE)) {
				n++;
			}
		}
		return n;
	}

	/**
	 * <p>Discription:[加班时间（小时）]</p>
	 * Created on 2012-7-17
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public Integer generateH_overtime(List<EmployeeDetailBean> edblist) {
		return 12;
	}

	/**
	 * <p>Discription:[异常情况（次）]</p>
	 * Created on 2012-7-17
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public Integer generateC_exception(List<EmployeeDetailBean> edblist) {
		int n = 0;
		for (EmployeeDetailBean edb : edblist) {
			if (!edb.isNull("exception")) {
				n++;
			}
		}
		return n;
	}
}
