package com.neusoft.acss.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.neusoft.acss.Acss;
import com.neusoft.acss.bean.RecordBean;
import com.neusoft.acss.bean.Vacation;
import com.neusoft.acss.bean.WorkDay;
import com.neusoft.acss.bs.Business;
import com.neusoft.acss.consts.Consts;
import com.neusoft.acss.exception.BizException;

/**
 * <p> Title: [文本操作类]</p>
 * <p> Description: [读取、导入文本。主要操作：每月考勤文件、法定假日、串休等文本]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public final class TxtUtil {

	/**
	 * 隐藏公用构造方法<p>
	 */
	private TxtUtil() {
	}

	/**
	 * <p>Description:[读取考勤结果txt文件，存入到List&lt;{@link RecordBean}&gt;中]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public static List<RecordBean> readAcssBeanFromFile(File file, String tnoon_begin, String tnoon_middle,
			String tnoon_end) {
		List<RecordBean> list = new ArrayList<RecordBean>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
			String str = "";
			RecordBean rb = null;
			while ((str = br.readLine()) != null) {
				if (!StringUtils.isEmpty(str)) {
					rb = Business.parseStrToAcssBean(str.trim(), tnoon_begin, tnoon_middle, tnoon_end);
					list.add(rb);
				}
			}
			br.close();
		} catch (UnsupportedEncodingException e) {
			throw new BizException("考勤结果txt编码格式错误，目前编码格式要求为：GBK", e);
		} catch (FileNotFoundException e) {
			throw new BizException("不会发生的异常，吗的", e);
		} catch (IOException e) {
			throw new BizException("读取文件：" + file.getName() + " 内容异常", e);
		}

		return list;
	}

	/**
	 * <p>Description:[从文件中读取字符串到集合中，然后返回Vacation的集合]</p>
	 * <p>
	 * #元旦：2012年1月1日至3日放假调休，共3天。2011年12月31日(星期六)上班。</br>
	 * #春节：1月22日至28日放假调休，共7天。1月21日(星期六)、1月29日(星期日)上班。</br>
	 * #清明节：4月2日至4日放假调休，共3天。3月31日(星期六)、4月1日(星期日)上班。</br>
	 * #劳动节：4月29日至5月1日放假调休，共3天。4月28日(星期六)上班。</br>
	 * #端午节：6月22日至24日放假公休，共3天。</br>
	 * #中秋节、国庆节：9月30日至10月7日放假调休，共8天。9月29日(星期六)上班。</br>
	 * </p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public static List<Vacation> getVacations() {
		List<Vacation> list = new ArrayList<Vacation>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(Consts.PATH_VACATIONS));
			String str = "";
			Vacation vacation = null;
			while ((str = br.readLine()) != null) {
				vacation = new Vacation();
				vacation.setDate(DateUtils.parseDate(str, "yyyy-MM-dd"));
				list.add(vacation);
			}
			br.close();
		} catch (FileNotFoundException e) {
			throw new BizException("找不到文件：" + Consts.PATH_VACATIONS, e);
		} catch (IOException e) {
			throw new BizException("读取文件出错：" + Consts.PATH_VACATIONS, e);
		} catch (ParseException e) {
			throw new BizException("文件：" + Consts.PATH_VACATIONS + " 中日期格式错误，无法转换", e);
		}
		return list;
	}

	/**
	 * <p>Description:[导入法定假期到Consts.PATH_VACATIONS中]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public static void saveVacations(File file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			FileOutputStream out = new FileOutputStream(Consts.PATH_VACATIONS);
			String str = "";
			while ((str = br.readLine()) != null) {
				out.write(str.concat("\r\n").getBytes());
			}
			out.flush();
			out.close();
			br.close();
		} catch (FileNotFoundException e) {
			throw new BizException("找不到文件：" + Consts.PATH_VACATIONS + "，无法写入！", e);
		} catch (IOException e) {
			throw new BizException("写入文件出错：" + Consts.PATH_VACATIONS, e);
		}
	}

	/**
	 * <p>Description:[从文件中读取字符串到集合中，然后返回WorkDay的集合]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public static List<WorkDay> getWorkDays() {
		List<WorkDay> list = new ArrayList<WorkDay>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(Consts.PATH_WORKDAYS));
			String str = "";
			while ((str = br.readLine()) != null) {
				WorkDay workDay = new WorkDay();
				workDay.setDate(DateUtils.parseDate(str, "yyyy-MM-dd"));
				list.add(workDay);
			}
			br.close();
		} catch (FileNotFoundException e) {
			throw new BizException("找不到文件：" + Consts.PATH_WORKDAYS, e);
		} catch (IOException e) {
			throw new BizException("读取文件出错：" + Consts.PATH_WORKDAYS, e);
		} catch (ParseException e) {
			throw new BizException("文件：" + Consts.PATH_WORKDAYS + " 中日期格式错误，无法转换", e);
		}
		return list;
	}

	/**
	 * <p>Description:[导入导入周末信息到Consts.PATH_WORKDAYS中]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public static void saveWorkDays(File file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			FileOutputStream out = new FileOutputStream(Consts.PATH_WORKDAYS);
			String str = "";
			while ((str = br.readLine()) != null) {
				out.write(str.concat("\r\n").getBytes());
			}
			out.flush();
			out.close();
			br.close();
		} catch (FileNotFoundException e) {
			throw new BizException("找不到文件：" + Consts.PATH_WORKDAYS + "，无法写入！", e);
		} catch (IOException e) {
			throw new BizException("写入文件出错：" + Consts.PATH_WORKDAYS, e);
		}
	}

	public static void main(String args[]) {
		Acss a = new Acss();
		a.setSize(500, 300);
		a.setVisible(true);
	}

}
