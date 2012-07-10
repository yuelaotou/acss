package com.neusoft.acss.bs;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.neusoft.acss.bean.AcssBean;
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
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static AcssBean parseStrToAcssBean(String str, String tnoon_begin, String tnoon_middle, String tnoon_end) {
		AcssBean acssBean = new AcssBean();

		String searchList[] = new String[] { "[ ", "【", "】", "缺" };
		String replacementList[] = new String[] { "[", " ", " ", " " };
		str = StringUtils.replaceEach(str, searchList, replacementList);

		String s_info[] = StringUtils.split(str);
		int n = 0;
		if (!str.startsWith("[")) {
			acssBean.setName(s_info[0]);
			NAME = s_info[0];
			String date_week[] = StringUtils.substringsBetween(s_info[1], "[", "]");
			acssBean.setDate(date_week[0]);
			acssBean.setWeek(getWeek(date_week[1]));
			n += 2;
		} else {
			acssBean.setName(NAME);
			String date_week[] = StringUtils.substringsBetween(s_info[0], "[", "]");
			acssBean.setDate(date_week[0]);
			acssBean.setWeek(getWeek(date_week[1]));
			n++;
		}

		for (int i = n; i < s_info.length; i++) {
			String time = s_info[i];
			if (time.compareTo(tnoon_begin) < 0) {// 上班
				// 判断是否为空，需要写入相对早些的时间，因为读取的时间是按照顺序来的。
				if (StringUtils.isEmpty(acssBean.getTMorning())) {
					acssBean.setTMorning(time);
				}
			} else if (time.compareTo(tnoon_begin) >= 0 && time.compareTo(tnoon_middle) < 0) {// 午休A
				// 判断是否为空，需要写入相对早些的时间，因为读取的时间是按照顺序来的。
				if (StringUtils.isEmpty(acssBean.getTNooningA())) {
					acssBean.setTNooningA(time);
				}
			} else if (time.compareTo(tnoon_middle) >= 0 && time.compareTo(tnoon_end) <= 0) {// 午休B
				// 不用判断为空，需要写入相对晚些的时间，因为读取的时间是按照顺序来的。
				if (StringUtils.isEmpty(acssBean.getTNooningB())) {
					acssBean.setTNooningB(time);
				} else {
					// 若午休第一次打卡为空，则进行一定的处理。
					if (StringUtils.isEmpty(acssBean.getTNooningA())) {
						acssBean.setTNooningA(acssBean.getTNooningB());
						acssBean.setTNooningB(time);
					}
				}
			} else if (time.compareTo(tnoon_end) > 0) {// 下班
				// 不用判断为空，需要写入相对晚些的时间，因为读取的时间是按照顺序来的。
				acssBean.setTEvening(time);
			}
		}

		return acssBean;
	}

	/**
	 * <p>Discription:[通过导入的考勤记录、出差登记表和相关设置计算职工本月详细考勤记录]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static List<EmployeeDetailBean> generateEmployeeDetailList(List<AcssBean> acssBeanList,
			List<EvectionBean> evectionBeanList) {
		EmployeeDetailBean employeeDetailBean = null;
		List<EmployeeDetailBean> employeeDetailBeanList = new ArrayList<EmployeeDetailBean>();

		String mdate = "";
		for (AcssBean acssBean : acssBeanList) {
		}
		return employeeDetailBeanList;
	}

	/**
	 * <p>Discription:[通过employeeDetailBeanList计算出employeeTotalBeanList]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static List<EmployeeTotalBean> convertDetail2Total(List<EmployeeDetailBean> edblist) {
		List<EmployeeTotalBean> etblist = new ArrayList<EmployeeTotalBean>();
		EmployeeTotalBean etb = null;
        Map<String, String> map = new HashMap<String, String>();
		for (EmployeeDetailBean edb : edblist) {

            if (map.containsKey(edb.getName())) {
                map.put(edb.getName(), map.get(edb.getName())+edb.getEvection_locale());
            } else {
                map.put(edb.getName(), edb.getEvection_locale());
            }
		}
        for (Entry<String, String> entry : map.entrySet()) {
        	//entry.getKey(), entry.getValue()
        	EmployeeTotalBean j = new EmployeeTotalBean();
        	etblist.add(j);
        }
		return etblist;
	}

	/**
	 * <p>Discription:[根据传入的字符串转换成Week的Enum对象]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
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
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static void checkVacation(List<AcssBean> acssBeanList, List<Vacation> vacationList, List<WorkDay> workDayList) {
		for (AcssBean acssBean : acssBeanList) {
			Calendar c = Calendar.getInstance();
			try {
				c.setTime(DateUtils.parseDate(acssBean.getDate(), "yyyy-MM-dd"));
			} catch (ParseException e) {
				throw new BizException("考勤文件中日期格式错误，请确认格式为：yyyy-MM-dd，" + acssBean.getDate(), e);
			}

			// 判断是否为周六周日
			if (c.get(Calendar.DAY_OF_WEEK) == 7 || c.get(Calendar.DAY_OF_WEEK) == 1) {
				acssBean.setVacation(true);
				// 如果在串休列表中，直接return flase，不再计算Vacation了。
				for (WorkDay wd : workDayList) {
					if (wd.getDate().compareTo(c.getTime()) == 0) {
						acssBean.setVacation(false);
						break;
					}
				}
			} else {
				// 如果在国家规定的休假列表中，return true。
				for (Vacation vac : vacationList) {
					if (vac.getDate().compareTo(c.getTime()) == 0) {
						acssBean.setVacation(true);
					}
				}
			}
		}
	}

	/**
	 * <p>Discription:[计算两个时间之间相差多少小时]</p>
	 * Created on 2012-7-2
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @throws ParseException 
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static int minusDate(String beginTime, String endTime) {
		long begin = 0;
		long end = 0;
		try {
			begin = DateUtils.parseDate(beginTime, "HH:mm:ss").getTime();
			end = DateUtils.parseDate(endTime, "HH:mm:ss").getTime();
		} catch (ParseException e) {
			throw new BizException("计算日期相差时间出错:" + beginTime + "，" + endTime, e);
		}
		return (int) ((end - begin) / (1000 * 60 * 60)) + 1;
	}

}
