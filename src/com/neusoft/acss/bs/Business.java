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
	public static List<EmployeeTotalBean> convertDetail2Total(List<EmployeeDetailBean> edblist)
			throws ClassNotFoundException {
		List<EmployeeDetailBean> list = new ArrayList<EmployeeDetailBean>();
		List<EmployeeTotalBean> etblist = new ArrayList<EmployeeTotalBean>();
		Map<String, Object[]> m = EmployeeTotalBS.getPropertyMap();
		EmployeeTotalBean etb = null;

		String name = "";
		for (int i = 0; i < edblist.size(); i++) {
			EmployeeDetailBean edb = edblist.get(i);

			// 首次进来，添加edb到list中。
			if (i == 0) {
				list.add(edb);
				name = edb.getString("name");
				continue;
			}

			// 若是最后一次循环，需要提前添加edb到list中
			if (i == edblist.size() - 1) {
				list.add(edb);
			} else if (name.equals(edb.getString("name"))) {
				// 若本次的edb和上次的是同一个人，则只添加edb到list中，继续下次循环
				list.add(edb);
				continue;
			}

			// 其余情况走到这里，只有两种情况：1,换人了; 2,最后一条记录。
			// 不管哪种情况说明是可以统计当前list当中的edb情况了，统计之后添加etb到etblist中。所以本情况走完之后应该做4件事：
			// 1,重新new ArrayList<EmployeeDetailBean>();
			// 2,把etb添加到etblist中;
			// 3,重新给name赋值为：edb.getString("name");
			// 4,把当前的edb添加到list中，因为情况1（换人了）还未统计本个edb，不要落了。而情况2添不添加都不执行了。所以还是要添加。

			etb = new EmployeeTotalBean(m);
			Iterator<Entry<String, Object[]>> it = m.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Object[]> entry = it.next();
				Object key = entry.getKey();
				Object[] values = entry.getValue();

				Object args[] = new Object[] { list };
				Class<?> clz[] = new Class<?>[] { Class.forName("java.util.ArrayList") };
				try {
					Object value = MethodUtils.invokeMethod(EmployeeTotalBS.getInstance(), values[2].toString(), args,
							clz);
					etb.setValue(key.toString(), value);
				} catch (Exception e) {
					e.printStackTrace();
					throw new BizException("EmployeeTotalBS#getPropertiesMap配置错误，属性是：" + key + "，方法是："
							+ values[2].toString(), e);
				}
			}
			etblist.add(etb);

			name = edb.getString("name");
			list = new ArrayList<EmployeeDetailBean>();
			list.add(edb);
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
