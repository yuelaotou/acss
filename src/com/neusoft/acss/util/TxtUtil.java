package com.neusoft.acss.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.neusoft.acss.Acss;
import com.neusoft.acss.bean.AcssBean;
import com.neusoft.acss.bean.Vacation;
import com.neusoft.acss.bean.WorkDay;
import com.neusoft.acss.consts.Consts;
import com.neusoft.acss.enums.Week;

public class TxtUtil {
	private static String NAME = null;

	/**
	 * 
	 * <p>Discription:[读取考勤结果txt文件，存入到List&lt;{@link AcssBean}&gt;中]</p>
	 * Created on 2012-7-9
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 * @throws:
	 */
	public static List<AcssBean> readAcssBeanFromFile(File file, String tnoon_begin, String tnoon_middle,
			String tnoon_end) throws IOException {
		List<AcssBean> acssBeanList = new ArrayList<AcssBean>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GB2312"));
		String str = "";
		AcssBean acssBean = null;
		while ((str = br.readLine()) != null) {
			if (!StringUtils.isEmpty(str)) {
				acssBean = parseStrToAcssBean(str.trim(), tnoon_begin, tnoon_middle, tnoon_end);
				acssBeanList.add(acssBean);
			}
		}

		return acssBeanList;
	}

	/**
	 * 
	 * <p>Discription:[按照规则解析字符串到{@link AcssBean}中]</p>
	 * Created on 2012-7-9
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 * @throws:
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
				}else{
					//若午休第一次打卡为空，则进行一定的处理。
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
	 * 
	 * <p>Discription:[根据传入的字符串转换成Week的Enum对象]</p>
	 * Created on 2012-7-9
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 * @throws:
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
	 * 
	 * <p>
	 * Discription:[从文件中读取字符串到集合中，然后返回Vacation的集合]
	 * </p>
	 * <p>
	 * #元旦：2012年1月1日至3日放假调休，共3天。2011年12月31日(星期六)上班。</br>
	 * #春节：1月22日至28日放假调休，共7天。1月21日(星期六)、1月29日(星期日)上班。</br>
	 * #清明节：4月2日至4日放假调休，共3天。3月31日(星期六)、4月1日(星期日)上班。</br>
	 * #劳动节：4月29日至5月1日放假调休，共3天。4月28日(星期六)上班。</br> #端午节：6月22日至24日放假公休，共3天。</br>
	 * #中秋节、国庆节：9月30日至10月7日放假调休，共8天。9月29日(星期六)上班。</br>
	 * </p>
	 * Created on 2012-7-1
	 * 
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @throws ParseException
	 * @throws IOException
	 * @throws URISyntaxException 
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static List<Vacation> getVacations() throws ParseException, IOException {
		List<Vacation> list = new ArrayList<Vacation>();

		BufferedReader br = new BufferedReader(new FileReader(Consts.PATH_VACATIONS));
		String str = "";
		Vacation vacation = null;
		while ((str = br.readLine()) != null) {
			vacation = new Vacation();
			vacation.setDate(DateUtils.parseDate(str, "yyyy-MM-dd"));
			list.add(vacation);
		}
		return list;
	}

	/**
	 * 
	 * <p>Discription:[导入法定假期到src/vacations.txt中]</p>
	 * Created on 2012-7-2
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @throws URISyntaxException 
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static void saveVacations(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));

		FileOutputStream out = new FileOutputStream(Consts.PATH_VACATIONS);
		String str = "";
		while ((str = br.readLine()) != null) {
			out.write(str.concat("\r\n").getBytes());
		}
		out.flush();
		out.close();
	}

	/**
	 * 
	 * <p>
	 * Discription:[从文件中读取字符串到集合中，然后返回WorkDay的集合]
	 * </p>
	 * Created on 2012-7-1
	 * 
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @throws ParseException
	 * @throws IOException
	 * @throws URISyntaxException 
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static List<WorkDay> getWorkDays() throws ParseException, IOException {
		List<WorkDay> list = new ArrayList<WorkDay>();

		BufferedReader br = new BufferedReader(new FileReader(Consts.PATH_WORKDAYS));
		String str = "";
		while ((str = br.readLine()) != null) {
			WorkDay workDay = new WorkDay();
			workDay.setDate(DateUtils.parseDate(str, "yyyy-MM-dd"));
			list.add(workDay);
		}
		return list;
	}

	/**
	 * 
	 * <p>Discription:[导入导入周末信息到Consts.PATH_WORKDAYS中]</p>
	 * Created on 2012-7-2
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @throws URISyntaxException 
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static void saveWorkDays(File file) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(file));
		FileOutputStream out = new FileOutputStream(Consts.PATH_WORKDAYS);
		String str = "";
		while ((str = br.readLine()) != null) {
			out.write(str.concat("\r\n").getBytes());
		}
		out.flush();
		out.close();
	}

	public static void main(String args[]) {
		Acss a = new Acss();
		a.setSize(500, 300);
		a.setVisible(true);
	}

}
