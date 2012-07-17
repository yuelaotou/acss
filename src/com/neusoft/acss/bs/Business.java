package com.neusoft.acss.bs;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.neusoft.acss.Acss;
import com.neusoft.acss.bean.EmployeeDetailBean;
import com.neusoft.acss.bean.EmployeeTotalBean;
import com.neusoft.acss.bean.EvectionBean;
import com.neusoft.acss.bean.Vacation;
import com.neusoft.acss.bean.WorkDay;
import com.neusoft.acss.enums.Week;
import com.neusoft.acss.exception.BizException;

/**
 * <p> Title: [业务逻辑类]</p>
 * <p> Description: [本类旨在对各种业务逻辑处理，和文件操作区分开]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public class Business {

	private static String NAME = null;

	/**
	 * <p>Discription:[读取考勤记录后，用本方法进行解析，按行存入到{@link AcssBean}中，以后更改解析规则来此修改，请记录信息]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @throws ClassNotFoundException 
	 */
	public static EmployeeDetailBean parseStrToAcssBean(String str, String tnoon_begin, String tnoon_middle,
			String tnoon_end, Map<String, Object[]> m) throws ClassNotFoundException {

		EmployeeDetailBean edb = new EmployeeDetailBean(m);

		String searchList[] = new String[] { "[ ", "【", "】", "缺" };
		String replacementList[] = new String[] { "[", " ", " ", " " };
		str = StringUtils.replaceEach(str, searchList, replacementList);

		String s_info[] = StringUtils.split(str);
		int n = 0;
		if (!str.startsWith("[")) {
			edb.setValue("name", s_info[0]);
			NAME = s_info[0];
			String date_week[] = StringUtils.substringsBetween(s_info[1], "[", "]");
			edb.setValue("date", date_week[0]);
			edb.setValue("week", getWeek(date_week[1]));
			n += 2;
		} else {
			edb.setValue("name", NAME);
			String date_week[] = StringUtils.substringsBetween(s_info[0], "[", "]");
			edb.setValue("date", date_week[0]);
			edb.setValue("week", getWeek(date_week[1]));
			n++;
		}

		for (int i = n; i < s_info.length; i++) {
			String time = s_info[i];
			if (time.compareTo(tnoon_begin) <= 0) {// 上班
				// 判断是否为空，需要写入相对早些的时间，因为读取的时间是按照顺序来的。
				if (edb.getValue("tmorning") == null) {
					edb.setValue("tmorning", time);
				}
			} else if (time.compareTo(tnoon_begin) > 0 && time.compareTo(tnoon_middle) < 0) {// 午休A
				// 判断是否为空，需要写入相对早些的时间，因为读取的时间是按照顺序来的。
				if (edb.getValue("tnooningA") == null) {
					edb.setValue("tnooningA", time);
				}
			} else if (time.compareTo(tnoon_middle) >= 0 && time.compareTo(tnoon_end) < 0) {// 午休B
				// 不用判断为空，需要写入相对晚些的时间，因为读取的时间是按照顺序来的。
				if (edb.getValue("tnooningB") == null) {
					edb.setValue("tnooningB", time);
				} else {
					// 若午休第一次打卡为空，则进行一定的处理。
					if (edb.getValue("tnooningA") == null) {
						edb.setValue("tnooningA", edb.getValue("tnooningB").toString());
						edb.setValue("tnooningB", time);
					}
				}
			} else if (time.compareTo(tnoon_end) >= 0) {// 下班
				// 不用判断为空，需要写入相对晚些的时间，因为读取的时间是按照顺序来的。
				edb.setValue("tevening", time);
			}
		}

		return edb;
	}

	/**
	 * <p>Discription:[通过导入的考勤记录、出差登记表和相关设置计算职工本月详细考勤记录]</p>
	 * <p>Discription:[若以后需求有变更，修改这里。比如：如何计算加班时间等等算法]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @throws ClassNotFoundException 
	 */
	public static List<EmployeeDetailBean> generateEmployeeDetailList(List<EmployeeDetailBean> list,
			List<EvectionBean> evectionBeanList, String tmorning, String tevening, String tnoon_begin, String tnoon_end)
			throws ClassNotFoundException {
		Map<String, Object[]> m = EmployeeDetailBS.getPropertyMap();

		for (EmployeeDetailBean edb : list) {
			EvectionBean evb = null;

			for (EvectionBean evb_1 : evectionBeanList) {
				evb = null;
				if (edb.getValue("name").equals(evb_1.getName())
						&& edb.getValue("date").toString().replace("-", "")
								.equals(evb_1.getMonth().concat(evb_1.getDay()))) {
					evb = evb_1;
					break;
				}
			}

			Iterator<Entry<String, Object[]>> it = m.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Object[]> entry = it.next();
				Object key = entry.getKey();
				Object[] values = entry.getValue();
				Object args[] = new Object[] { edb, evb, tmorning, tevening, tnoon_begin, tnoon_end };
				Class<?> clz[] = new Class<?>[] { Class.forName("com.neusoft.acss.bean.EmployeeDetailBean"),
						Class.forName("com.neusoft.acss.bean.EvectionBean"), Class.forName("java.lang.String"),
						Class.forName("java.lang.String"), Class.forName("java.lang.String"),
						Class.forName("java.lang.String") };
				try {
					Object value = MethodUtils.invokeMethod(EmployeeDetailBS.getInstance(), values[2].toString(), args,
							clz);
					edb.setValue(key.toString(), value);
				} catch (Exception e) {
					e.printStackTrace();
					throw new BizException("EmployeeDetailBS#getPropertiesMap配置错误，属性是：" + key + "，方法是："
							+ values[2].toString(), e);
				}
			}
			System.out.println(edb);
		}
		return list;
	}

	/**
	 * <p>Discription:[通过employeeDetailBeanList计算出employeeTotalBeanList]</p>
	 * <p>Discription:[若以后需求有变更，修改这里。比如：如何计算加班时间等等算法]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public static List<EmployeeTotalBean> convertDetail2Total(List<EmployeeDetailBean> edblist) {
		List<EmployeeTotalBean> etblist = new ArrayList<EmployeeTotalBean>();
		EmployeeTotalBean etb = null;

		int s_c_late = 0;
		int s_c_early = 0;
		int s_c_sick = 0;
		int s_c_thing = 0;
		int s_c_year = 0;
		int s_c_other = 0;
		int s_c_evection_locale = 0;
		int s_c_evection_remote = 0;
		int s_c_overtime_workday = 0;
		int s_c_overtime_weekend = 0;
		int s_c_overtime_holiday = 0;
		int s_c_overtime_remote = 0;
		int s_h_overtime = 0;
		int s_c_exception = 0;
		String name = "";
		for (EmployeeDetailBean edb : edblist) {
			if (!name.equals(edb.getValue("name")) && !"".equals(name)) {

				// 说明已经完成一个人信息的统计
				etb = new EmployeeTotalBean();
				etb.setName(name);
				etb.setC_late(s_c_late);
				etb.setC_early(s_c_early);

				etb.setC_sick(s_c_sick);
				etb.setC_thing(s_c_thing);
				etb.setC_year(s_c_year);
				etb.setC_other(s_c_other);

				etb.setC_evection_locale(s_c_evection_locale);
				etb.setC_evection_remote(s_c_evection_remote);

				etb.setC_overtime_workday(s_c_overtime_workday);
				etb.setC_overtime_weekend(s_c_overtime_weekend);
				etb.setC_overtime_holiday(s_c_overtime_holiday);
				etb.setC_overtime_remote(s_c_overtime_remote);
				etb.setH_overtime(s_h_overtime);
				etb.setC_exception(s_c_exception);
				etblist.add(etb);

				s_c_late = 0;
				s_c_early = 0;
				s_c_sick = 0;
				s_c_thing = 0;
				s_c_year = 0;
				s_c_other = 0;
				s_c_evection_locale = 0;
				s_c_evection_remote = 0;
				s_c_overtime_workday = 0;
				s_c_overtime_weekend = 0;
				s_c_overtime_holiday = 0;
				s_c_overtime_remote = 0;
				s_h_overtime = 0;
				s_c_exception = 0;
			}

			/*
			 * 只要迟到时间不空，就算今天迟到一次。不是按照时间累积成一天才算一天。 若以后需求有变更，修改这里。
			 */
			if (edb.getValue("tLate") != null) {
				s_c_late++;
			}

			/*
			 * 只要早退时间不空，就算今天早退一次。不是按照时间累积成一天才算一天。 若以后需求有变更，修改这里。
			 */
			// if (!StringUtils.isEmpty(edb.getTEarly())) {
			// s_c_early++;
			// }

			/*
			 * 只要请假类型不空，就算今天请假一次。再根据请假类型分开计算请假的天数。 若以后需求有变更，修改这里。
			 */
			// if (edb.getLeave() != null) {
			// if (edb.getLeave().equals(Leave.SICK)) {
			// s_c_sick++;
			// }
			// if (edb.getLeave().equals(Leave.THING)) {
			// s_c_thing++;
			// }
			// if (edb.getLeave().equals(Leave.YEAR)) {
			// s_c_year++;
			// }
			// if (edb.getLeave().equals(Leave.OTHER)) {
			// s_c_other++;
			// }
			// }

			/*
			 * 只要本地出差字段不空，就算今天本地出差一次。 若以后需求有变更，修改这里。
			 */
			// if (!StringUtils.isEmpty(edb.getEvection_locale())) {
			// s_c_evection_locale++;
			// }

			/*
			 * 只要外地出差字段不空，就算今天外地出差一次。 若以后需求有变更，修改这里。
			 */
			// if (!StringUtils.isEmpty(edb.getEvection_remote())) {
			// s_c_evection_remote++;
			// }
			/*
			 * 只要加班类型不空，就加班。若以后需求有变更，修改这里。
			 */
			// if (edb.getOvertime() != null) {
			// if (edb.getOvertime().equals(Overtime.WORKDAY)) {
			// s_c_overtime_workday++;
			// }
			// if (edb.getOvertime().equals(Overtime.WEEKEND)) {
			// s_c_overtime_weekend++;
			// }
			// if (edb.getOvertime().equals(Overtime.HOLIDAY)) {
			// s_c_overtime_holiday++;
			// }
			// if (edb.getOvertime().equals(Overtime.REMOTE)) {
			// s_c_overtime_remote++;
			// }
			// }

			/*
			 * 只要加班字段不空，把加班时间累积起来。 若以后需求有变更，修改这里。
			 */
			// if (!StringUtils.isEmpty(edb.getTOvertime())) {
			// s_h_overtime++;
			// s_h_overtime += Integer.parseInt(edb.getTOvertime());
			// }

			/*
			 * 只要异常字段不空，有一次算一次统计出来。 若以后需求有变更，修改这里。
			 */
			// if (!StringUtils.isEmpty(edb.getException())) {
			// s_c_exception++;
			// }

			// name = edb.getName();
		}

		return etblist;
	}

	/**
	 * <p>Discription:[根据传入的字符串转换成Week的Enum对象]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public static Week getWeek(String week) {
		if (week.equals("1") || week.equals("一")) {
			return Week.MON;
		}
		if (week.equals("2") || week.equals("二")) {
			return Week.TUE;
		}
		if (week.equals("3") || week.equals("三")) {
			return Week.WED;
		}
		if (week.equals("4") || week.equals("四")) {
			return Week.THU;
		}
		if (week.equals("5") || week.equals("五")) {
			return Week.FRI;
		}
		if (week.equals("6") || week.equals("六")) {
			return Week.SAT;
		}
		if (week.equals("7") || week.equals("日")) {
			return Week.SUN;
		}
		return null;
	}

	/**
	 * <p>Discription:[根据法定假日和串休记录，再结合正常周六周日休息，判断AcssBean是正常上班还是休息]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public static void checkVacation(List<EmployeeDetailBean> edbList, List<Vacation> vacationList,
			List<WorkDay> workDayList) {

		for (EmployeeDetailBean edb : edbList) {
			Calendar c = Calendar.getInstance();
			try {
				c.setTime(DateUtils.parseDate(edb.getValue("date").toString(), "yyyy-MM-dd"));
			} catch (ParseException e) {
				throw new BizException("考勤文件中日期格式错误，请确认格式为：yyyy-MM-dd，" + edb.getValue("date"), e);
			}

			// 判断是否为周六周日
			if (c.get(Calendar.DAY_OF_WEEK) == 7 || c.get(Calendar.DAY_OF_WEEK) == 1) {
				edb.setValue("rest", "休息日");
				// 如果在串休列表中，直接return flase，不再计算Vacation了。
				for (WorkDay wd : workDayList) {
					if (wd.getDate().compareTo(c.getTime()) == 0) {
						edb.setValue("rest", null);
						break;
					}
				}
			} else {
				// 如果在国家规定的休假列表中，return true。
				for (Vacation vac : vacationList) {
					if (vac.getDate().compareTo(c.getTime()) == 0) {
						edb.setValue("rest", "休息日");
					}
				}
			}
		}
	}

	/**
	 * <p>Discription:[计算两个时间之间相差多少时间，n为小时或分钟:Calendar.MINUTE,Calendar.HOUR]</p>
	 * Created on 2012-7-2
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @throws ParseException 
	 */
	public static int minusDate(String beginTime, String endTime, int n) {
		if (n == Calendar.SECOND) {
			n = 1000;
		}
		if (n == Calendar.MINUTE) {
			n = 1000 * 60;
		}
		if (n == Calendar.HOUR) {
			n = 1000 * 60 * 60;
		}
		long begin = 0;
		long end = 0;
		try {
			begin = DateUtils.parseDate(beginTime, "HH:mm:ss").getTime();
			end = DateUtils.parseDate(endTime, "HH:mm:ss").getTime();
		} catch (ParseException e) {
			throw new BizException("计算时间差出错:" + beginTime + "，" + endTime, e);
		}
		int t = (int) ((end - begin) / n) + 1;
		return t;
	}

	public static void main(String args[]) {
		Acss a = new Acss();
		a.setSize(500, 300);
		a.setVisible(true);
	}
}
