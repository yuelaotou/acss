package com.neusoft.acss.bs;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.neusoft.acss.Acss;
import com.neusoft.acss.bean.EmployeeBean;
import com.neusoft.acss.bean.EvectionBean;
import com.neusoft.acss.bean.RecordBean;
import com.neusoft.acss.bean.Vacation;
import com.neusoft.acss.bean.WorkDay;
import com.neusoft.acss.column.detail.impl.ColumnDetailImpl;
import com.neusoft.acss.column.total.impl.ColumnTotalImpl;
import com.neusoft.acss.enums.Week;
import com.neusoft.acss.exception.BizException;
import com.neusoft.acss.util.ClassUtil;

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
	 * <p>Description:[读取考勤记录后，用本方法进行解析，按行存入到{@link AcssBean}中，以后更改解析规则来此修改，请记录信息]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
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
			rb.setWeek(getWeek(date_week[1]));
			n += 2;
		} else {
			rb.setName(NAME);
			String date_week[] = StringUtils.substringsBetween(s_info[0], "[", "]");
			rb.setDate(date_week[0]);
			rb.setWeek(getWeek(date_week[1]));
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
				// 判断是否为空，需要写入相对早些的时间，因为读取的时间是按照顺序来的。
				if (StringUtils.isEmpty(rb.getTnooningA())) {
					rb.setTnooningA(time);
				}
			} else if (time.compareTo(tnoon_middle) >= 0 && time.compareTo(tnoon_end) < 0) {// 午休B
				// 不用判断为空，需要写入相对晚些的时间，因为读取的时间是按照顺序来的。
				if (StringUtils.isEmpty(rb.getTnooningB())) {
					rb.setTnooningB(time);
				} else {
					// 若午休第一次打卡为空，则进行一定的处理。
					if (StringUtils.isEmpty(rb.getTnooningA())) {
						rb.setTnooningA(rb.getTnooningB());
						rb.setTnooningB(time);
					}
				}
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
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public static List<Map<String, String>> generateEmployeeDetailList(List<EmployeeBean> ebList,
			List<RecordBean> rbList, List<EvectionBean> evbList) throws Exception {

		List<Map<String, String>> l = new ArrayList<Map<String, String>>();
		List<Class<?>> lc = ClassUtil.getAllImplClassesByInterface(ColumnDetailImpl.class, "super");

		for (RecordBean rb : rbList) {

			// 找到相同姓名的EmployeeBean职工基本信息实体
			EmployeeBean eb = null;
			// for (EmployeeBean eb_1 : ebList) {
			// eb = null;
			// if (rb.getName().equals(eb_1.getName())) {
			// eb = eb_1;
			// break;
			// }
			// }

			// 过会要删掉的，只是测试类
			eb = new EmployeeBean();

			// 找到相同姓名和日期的EvectionBean出差登记表实体
			EvectionBean evb = null;
			for (EvectionBean evb_1 : evbList) {
				if (rb.getName().equals(evb_1.getName())
						&& rb.getDate().replace("-", "").equals(evb_1.getMonth().concat(evb_1.getDay()))) {
					evb = evb_1;
					break;
				}
			}

			// 计算职工每一天的打卡情况，用Class全名作为key
			Map<String, String> m = generateEmployeeDetail(eb, rb, evb, lc);

			Iterator<Entry<String, String>> it = m.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> entry = it.next();
				System.out.println(entry.getKey() + " == " + entry.getValue());
			}
			l.add(m);
		}
		return l;
	}

	/**
	 * <p>Description:[通过EmployeeBean,RecordBean,EvectionBean计算职工本月详细考勤记录]</p>
	 * <p>Description:[本方法自动扩充实现ColumnDetailImpl接口的所有类]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public static Map<String, String> generateEmployeeDetail(EmployeeBean eb, RecordBean rb, EvectionBean evb,
			List<Class<?>> lc) throws Exception {

		Map<String, String> m = new LinkedHashMap<String, String>();
		for (Class<?> clz : lc) {
			ColumnDetailImpl c = (ColumnDetailImpl) clz.newInstance();
			m.put(clz.getName(), c.generateColumn(eb, rb, evb));
		}
		return m;
	}

	/**
	 * <p>Description:[职工详细考勤记录转换成统计信息]</p>
	 * <p>Description:[本方法按职工分类，每个职工每月的信息统计]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public static List<Map<String, String>> convertDetail2Total(List<Map<String, String>> lm) throws Exception {

		List<Map<String, String>> sublist = new ArrayList<Map<String, String>>();
		List<Map<String, String>> returnlist = new ArrayList<Map<String, String>>();
		List<Class<?>> lc = ClassUtil.getAllImplClassesByInterface(ColumnTotalImpl.class, "super");

		String name = "";
		for (int i = 0; i < lm.size(); i++) {
			Map<String, String> m_detail = lm.get(i);

			// 首次进来，主要是把同一个人的信息归到一起。
			if (i == 0) {
				sublist.add(m_detail);
				name = m_detail.get("com.neusoft.acss.column.detail.NameColumn");
				continue;
			}

			// 若是最后一次循环，需要提前添加m_detail到list中
			if (i == lm.size() - 1) {
				sublist.add(m_detail);
			} else if (name.equals(m_detail.get("com.neusoft.acss.column.detail.NameColumn"))) {
				// 若本次和上次是同一个人，则只添加m_detail到list中，继续下次循环
				sublist.add(m_detail);
				continue;
			}

			// 其余情况走到这里，只有两种情况：1,换人了; 2,最后一条记录。
			// 不管哪种情况说明是可以统计当前list当中的m_detail情况了，统计之后添加Total的map到returnlist中。所以在此应该做4件事：

			// 1,已经按人头有了sublist，所以现在按照sublist计算这个人本月的统计信息，每人一条记录;
			Map<String, String> mm = calculateDetail2Total(sublist, lc);
			returnlist.add(mm);

			// 2,重新给name赋值为：m.get("name");
			name = m_detail.get("com.neusoft.acss.column.detail.NameColumn");

			// 3,因为情况1（换人了），所以重新sublist = new ArrayList<Map<String, String>>();
			sublist = new ArrayList<Map<String, String>>();

			// 4,把当前的m_detail添加到list中，因为情况1（换人了）还未统计本个m_detail，不要落了。而情况2添不添加都不执行了。所以还是要添加。
			sublist.add(m_detail);
		}
		return returnlist;
	}

	/**
	 * <p>Description:[通过每个人每个月详细的记录计算统计信息]</p>
	 * <p>Description:[本方法自动扩充实现ColumnTotalImpl接口的所有类]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public static Map<String, String> calculateDetail2Total(List<Map<String, String>> lm, List<Class<?>> lc)
			throws Exception {

		Map<String, String> m = new LinkedHashMap<String, String>();
		for (Class<?> clz : lc) {
			ColumnTotalImpl c = (ColumnTotalImpl) clz.newInstance();
			m.put(clz.getName(), c.generateColumn(lm));
		}
		return m;

	}

	/**
	 * <p>Description:[根据传入的字符串转换成Week的Enum对象]</p>
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
	 * <p>Description:[根据法定假日和串休记录，再结合正常周六周日休息，判断AcssBean是正常上班还是休息]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public static void checkVacation(List<RecordBean> rbList, List<Vacation> vacationList, List<WorkDay> workDayList) {

		for (RecordBean rb : rbList) {
			Calendar c = Calendar.getInstance();
			try {
				c.setTime(DateUtils.parseDate(rb.getDate(), "yyyy-MM-dd"));
			} catch (ParseException e) {
				throw new BizException("考勤文件中日期格式错误，请确认格式为：yyyy-MM-dd，" + rb.getDate(), e);
			}

			// 判断是否为周六周日
			if (c.get(Calendar.DAY_OF_WEEK) == 7 || c.get(Calendar.DAY_OF_WEEK) == 1) {
				rb.setRest("休息日");
				// 如果在串休列表中，直接return flase，不再计算Vacation了。
				for (WorkDay wd : workDayList) {
					if (wd.getDate().compareTo(c.getTime()) == 0) {
						rb.setRest(null);
						break;
					}
				}
			} else {
				// 如果在国家规定的休假列表中，return true。
				for (Vacation vac : vacationList) {
					if (vac.getDate().compareTo(c.getTime()) == 0) {
						rb.setRest("休息日");
					}
				}
			}
		}
	}

	/**
	 * <p>Description:[计算两个时间之间相差多少时间，n为小时或分钟:Calendar.MINUTE,Calendar.HOUR]</p>
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
