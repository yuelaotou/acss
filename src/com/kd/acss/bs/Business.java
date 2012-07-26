package com.kd.acss.bs;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.kd.acss.bean.EmployeeBean;
import com.kd.acss.bean.EvectionBean;
import com.kd.acss.bean.Holiday;
import com.kd.acss.bean.Info;
import com.kd.acss.bean.RecordBean;
import com.kd.acss.bean.Weekend;
import com.kd.acss.bean.Workday;
import com.kd.acss.column.detail.IColumnDetail;
import com.kd.acss.column.detail.impl.D_B_Name;
import com.kd.acss.column.total.IColumnTotal;
import com.kd.acss.enums.Week;

/**
 * <p> Title: [业务逻辑类]</p>
 * <p> Description: [本类旨在对各种业务逻辑处理，和文件操作区分开]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * @version 1.0
 */
public class Business {

	private static String NAME = null;

	/**
	 * <p>Description:[读取考勤记录后，用本方法进行解析，按行存入到{@link RecordBean}中，以后更改解析规则来此修改，请记录信息]</p>
	 * Created on 2012-7-10
	 */
	public static RecordBean parseStrToAcssBean(String str, String tnoon_begin, String tnoon_middle, String tnoon_end) {

		RecordBean rb = new RecordBean();

		String searchList[] = new String[] { "[ ", "【", "】", "缺" };
		String replacementList[] = new String[] { "[", " ", " ", " " };
		str = StringUtils.replaceEach(str, searchList, replacementList);

		String s_info[] = StringUtils.split(str);
		int n = 0;
		if (!str.startsWith("[")) {
			rb.setName(s_info[0]);
			NAME = s_info[0];
			String date_week[] = StringUtils.substringsBetween(s_info[1], "[", "]");
			rb.setDate(date_week[0]);
			rb.setWeek(Week.getWeek(date_week[1]));
			n += 2;
		} else {
			rb.setName(NAME);
			String date_week[] = StringUtils.substringsBetween(s_info[0], "[", "]");
			rb.setDate(date_week[0]);
			rb.setWeek(Week.getWeek(date_week[1]));
			n++;
		}

		for (int i = n; i < s_info.length; i++) {
			String time = s_info[i];
			if (time.compareTo(tnoon_begin) <= 0) {// 上班
				// 判断是否为空，需要写入相对早些的时间，因为读取的时间是按照顺序来的。
				if (StringUtils.isEmpty(rb.getTmorning())) {
					rb.setTmorning(time);
				}
			} else if (time.compareTo(tnoon_begin) > 0 && time.compareTo(tnoon_middle) < 0) {// 午休A
				if (StringUtils.isEmpty(rb.getTnooningA())) {
					rb.setTnooningA(time);
				} else {
					rb.setTnooningB(time);
				}
			} else if (time.compareTo(tnoon_middle) >= 0 && time.compareTo(tnoon_end) < 0) {// 午休B
				// 不用判断为空，需要写入相对晚些的时间，因为读取的时间是按照顺序来的。
				// 若午休第一次打卡为空，则进行一定的处理。
				if (StringUtils.isEmpty(rb.getTnooningA()) && StringUtils.isNotEmpty(rb.getTnooningB())) {
					rb.setTnooningA(rb.getTnooningB());
				}
				rb.setTnooningB(time);
			} else if (time.compareTo(tnoon_end) >= 0) {// 下班
				// 不用判断为空，需要写入相对晚些的时间，因为读取的时间是按照顺序来的。
				rb.setTevening(time);
			}
		}

		return rb;
	}

	/**
	 * <p>Description:[通过员工基本信息，导入的考勤记录、出差登记表计算职工本月详细考勤记录]</p>
	 * <p>Description:[本方法自动扩充实现ColumnDetailImpl接口的所有类]</p>
	 * Created on 2012-7-10
	 */
	public static List<Map<String, String>> generateEmployeeDetailList(List<EmployeeBean> ebList,
			List<RecordBean> rbList, List<EvectionBean> evbList, List<Class<?>> lc) throws Exception {

		List<Map<String, String>> l = new ArrayList<Map<String, String>>();

		for (RecordBean rb : rbList) {
			Info i = new Info();
			i.setRecordBean(rb);

			// 找到相同姓名的EmployeeBean职工基本信息实体
			for (EmployeeBean eb : ebList) {
				if (rb.getName().equals(eb.getName())) {
					i.setEmployeeBean(eb);
					break;
				}
			}

			// 找到相同姓名和日期的EvectionBean出差登记表实体
			for (EvectionBean evb : evbList) {
				if (rb.getName().equals(evb.getName())
						&& rb.getDate().replace("-", "").equals(evb.getMonth().concat(evb.getDay()))) {
					i.setEvectionBean(evb);
					break;
				}
			}

			// 计算职工每一天的打卡情况，用Class全名作为key
			Map<String, String> m = generateEmployeeDetail(i, lc);

			l.add(m);
		}
		return l;
	}

	/**
	 * <p>Description:[通过EmployeeBean,RecordBean,EvectionBean计算职工本月详细考勤记录]</p>
	 * <p>Description:[本方法自动扩充实现ColumnDetailImpl接口的所有类]</p>
	 * Created on 2012-7-10
	 */
	public static Map<String, String> generateEmployeeDetail(Info i, List<Class<?>> lc) throws Exception {

		Map<String, String> m = new LinkedHashMap<String, String>();
		for (Class<?> clz : lc) {
			IColumnDetail c = (IColumnDetail) clz.newInstance();
			m.put(clz.getName(), c.generateColumn(i));
		}
		return m;
	}

	/**
	 * <p>Description:[职工详细考勤记录转换成统计信息，储存在Info中]</p>
	 * <p>Description:[本方法按职工分类，每个职工每月的信息统计]</p>
	 * Created on 2012-7-10
	 */
	public static void convertDetail2Total(Info info, List<Class<?>> lc) throws Exception {
		List<Map<String, String>> lm = info.getDetailList();
		List<Map<String, String>> sublist = new ArrayList<Map<String, String>>();
		List<Map<String, String>> totalList = new ArrayList<Map<String, String>>();
		final String column = D_B_Name.class.getName();
		String name = "";
		for (int i = 0; i < lm.size(); i++) {
			Map<String, String> m_detail = lm.get(i);

			// 首次进来，主要是把同一个人的信息归到一起。
			if (i == 0) {
				sublist.add(m_detail);
				name = m_detail.get(column);
				continue;
			}

			// 若是最后一次循环，需要提前添加m_detail到list中
			if (i == lm.size() - 1) {
				sublist.add(m_detail);
			} else if (name.equals(m_detail.get(column))) {
				// 若本次和上次是同一个人，则只添加m_detail到list中，继续下次循环
				sublist.add(m_detail);
				continue;
			}

			// 其余情况走到这里，只有两种情况：1,换人了; 2,最后一条记录。
			// 不管哪种情况说明是可以统计当前list当中的m_detail情况了，统计之后添加Total的map到returnlist中。所以在此应该做4件事：

			// 1,已经按人头有了sublist，所以现在按照sublist计算这个人本月的统计信息，每人一条记录;
			Map<String, String> totalMap = calculateDetail2Total(sublist, lc);
			totalList.add(totalMap);

			// 2,重新给name赋值为：m.get("name");
			name = m_detail.get(column);

			// 3,因为情况1（换人了），所以重新sublist = new ArrayList<Map<String, String>>();
			sublist = new ArrayList<Map<String, String>>();

			// 4,把当前的m_detail添加到list中，因为情况1（换人了）还未统计本个m_detail，不要落了。而情况2添不添加都不执行了。所以还是要添加。
			sublist.add(m_detail);
		}
		info.setTotalList(totalList);
	}

	/**
	 * <p>Description:[通过每个人每个月详细的记录计算统计信息]</p>
	 * <p>Description:[本方法自动扩充实现ColumnTotalImpl接口的所有类]</p>
	 * Created on 2012-7-10
	 */
	public static Map<String, String> calculateDetail2Total(List<Map<String, String>> sublist, List<Class<?>> lc)
			throws Exception {

		Info i = new Info();
		i.setSubList(sublist);

		Map<String, String> m = new LinkedHashMap<String, String>();
		for (Class<?> clz : lc) {
			IColumnTotal c = (IColumnTotal) clz.newInstance();
			m.put(clz.getName(), c.generateColumn(i));
		}
		return m;

	}

	/**
	 * <p>Description:[根据法定假日和串休记录，再结合正常周六周日休息，判断RecordBean是正常上班还是休息]</p>
	 * Created on 2012-7-10
	 */
	public static void checkVacation(Info info) {
		List<RecordBean> rbList = info.getRecordBeanList();
		List<Workday> workdayList = info.getWorkDayList();
		List<Weekend> weekendList = info.getWeekendList();
		List<Holiday> holidayList = info.getHolidayList();
		for (RecordBean rb : rbList) {
			String date = rb.getDate();

			// 工作日列表
			for (Workday wd : workdayList) {
				if (wd.getDate().equals(date)) {
					rb.setRest(null);
					break;
				}
			}

			// 周末列表
			for (Weekend we : weekendList) {
				if (we.getDate().equals(date)) {
					rb.setRest("休息日");
					break;
				}
			}

			// 法定假日列表
			for (Holiday hd : holidayList) {
				if (hd.getDate().equals(date)) {
					rb.setRest("法定假日");
					break;
				}
			}
		}
	}

}
