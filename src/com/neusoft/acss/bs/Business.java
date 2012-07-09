package com.neusoft.acss.bs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.neusoft.acss.Entrance;
import com.neusoft.acss.bean.EmployeeDetailBean;
import com.neusoft.acss.bean.EmployeeTotalBean;
import com.neusoft.acss.bean.EntranceBean;
import com.neusoft.acss.bean.Vacation;
import com.neusoft.acss.bean.WorkDate;
import com.neusoft.acss.consts.FileConst;
import com.neusoft.acss.enums.WorkStatus;

public class Business {

	/**
	 * 
	 * <p>
	 * Discription:[解析EXCEL2003版本，每行作为一个JavaBean存储在中ArrayList中]
	 * </p>
	 * Created on 2012-6-29
	 * 
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @throws IOException
	 * @throws ParseException
	 * @throws URISyntaxException 
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static List<EntranceBean> ParseExcel2003(File file) throws IOException, ParseException, URISyntaxException {
		InputStream is = new FileInputStream(file);
		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(is));
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = null;
		EntranceBean eb = null;

		List<Vacation> vacationList = getVacations();
		List<WorkDate> workDateList = getWorkDates();

		List<EntranceBean> list = new ArrayList<EntranceBean>();
		for (int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			eb = new EntranceBean();

			eb.setDepartment(row.getCell(0).toString().trim());
			eb.setName(row.getCell(1).toString().trim());
			eb.setId(Integer.parseInt(row.getCell(2).toString().trim()));

			String date = StringUtils.substringBefore(row.getCell(3).toString(), " ");
			String time = StringUtils.substringAfter(row.getCell(3).toString(), " ");
			eb.setDate(date);
			eb.setTime(StringUtils.leftPad(time, 8, "0"));
			eb.setMachine(Integer.parseInt(row.getCell(4).toString().trim()));
			eb.setCode(row.getCell(5).toString().trim());
			eb.setType(row.getCell(6).toString().trim());
			eb.setCardnum(row.getCell(7).toString().trim());
			eb.setVacation(checkVacation(date, vacationList, workDateList));

			list.add(eb);
		}
		is.close();
		return list;
	}

	/**
	 * 
	 * <p>
	 * Discription:[解析EXCEL2007版本，每行作为一个JavaBean存储在中ArrayList中]
	 * </p>
	 * Created on 2012-6-29
	 * 
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @throws IOException
	 * @throws ParseException
	 * @throws URISyntaxException 
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static List<EntranceBean> ParseExcel2007(File file) throws ParseException, IOException, URISyntaxException {
		InputStream is = new FileInputStream(file);
		XSSFWorkbook xwb = new XSSFWorkbook(is);
		XSSFSheet sheet = xwb.getSheetAt(0);
		XSSFRow row = null;
		EntranceBean eb = null;

		List<Vacation> vacationList = getVacations();
		List<WorkDate> workDateList = getWorkDates();

		List<EntranceBean> list = new ArrayList<EntranceBean>();
		for (int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			eb = new EntranceBean();
			eb.setDepartment(row.getCell(0).toString().trim());
			eb.setName(row.getCell(1).toString().trim());
			eb.setId(Integer.parseInt(row.getCell(2).toString().trim()));

			String date = StringUtils.substringBefore(row.getCell(3).toString(), " ");
			String time = StringUtils.substringAfter(row.getCell(3).toString(), " ");
			eb.setDate(date);
			eb.setTime(StringUtils.leftPad(time, 8, "0"));
			eb.setMachine(Integer.parseInt(row.getCell(4).toString().trim()));
			eb.setCode(row.getCell(5).toString().trim());
			eb.setType(row.getCell(6).toString().trim());
			eb.setCardnum(row.getCell(7).toString().trim());
			eb.setVacation(checkVacation(date, vacationList, workDateList));

			list.add(eb);
		}
		is.close();
		return list;
	}

	private static boolean checkVacation(String date, List<Vacation> vacationList, List<WorkDate> workDateList)
			throws ParseException {

		Calendar c = Calendar.getInstance();
		c.setTime(DateUtils.parseDate(date, "yyyy-MM-dd"));

		// 判断是否为周六周日
		if (c.get(Calendar.DAY_OF_WEEK) == 7 || c.get(Calendar.DAY_OF_WEEK) == 1) {
			// 如果在串休列表中，直接return flase，不再计算Vacation了。
			for (WorkDate wd : workDateList) {
				if (wd.getDate().compareTo(c.getTime()) == 0) {
					return false;
				}
			}
			return true;
		} else {
			// 如果在国家规定的休假列表中，return true。
			for (Vacation vac : vacationList) {
				if (vac.getDate().compareTo(c.getTime()) == 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * Discription:[根据传入的employeeDetailBeanList生成DetailExcel，文件保存位置为传入的file值]
	 * </p>
	 * Created on 2012-7-1
	 * 
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @throws IOException
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static void GenerateDetailExcel(String file, List<EmployeeDetailBean> employeeDetailBeanList)
			throws IOException {
		Workbook workbook = new SXSSFWorkbook(500);
		Sheet sheet = workbook.createSheet();
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("部门");
		row.createCell(1).setCellValue("姓名");
		row.createCell(2).setCellValue("登记号");
		row.createCell(3).setCellValue("打卡日期");
		row.createCell(4).setCellValue("上班时间");
		row.createCell(5).setCellValue("下班时间");
		row.createCell(6).setCellValue("打卡情况");
		for (int rownum = 0; rownum < employeeDetailBeanList.size(); rownum++) {
			EmployeeDetailBean employeeDetailBean = employeeDetailBeanList.get(rownum);
			row = sheet.createRow(rownum + 1);
			row.createCell(0).setCellValue(employeeDetailBean.getDepartment());
			row.createCell(1).setCellValue(employeeDetailBean.getName());
			row.createCell(2).setCellValue(employeeDetailBean.getId());
			row.createCell(3).setCellValue(employeeDetailBean.getDate());
			row.createCell(4).setCellValue(employeeDetailBean.getBeginTime());
			row.createCell(5).setCellValue(employeeDetailBean.getEndTime());
			row.createCell(6).setCellValue(
					employeeDetailBean.getStatus() == null ? "" : employeeDetailBean.getStatus().toString());
		}

		FileOutputStream out = new FileOutputStream(file);
		workbook.write(out);
		out.close();
	}

	/**
	 * 
	 * <p>Discription:[通过employeeDetailBeanList计算出employeeTotalBeanList]</p>
	 * Created on 2012-7-2
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @throws IOException, ParseException 
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static List<EmployeeTotalBean> ConvertDetail2Total(List<EmployeeDetailBean> employeeDetailBeanList)
			throws IOException, IOException, ParseException {
		List<EmployeeTotalBean> employeeTotalBeanList = new ArrayList<EmployeeTotalBean>();
		EmployeeTotalBean employeeTotalBean = null;
		int id = 0;
		int c_late = 0;
		int c_early = 0;
		int c_leave = 0;
		int c_overday = 0;
		int overtime = 0;
		for (EmployeeDetailBean employeeDetailBean : employeeDetailBeanList) {
			if (id != employeeDetailBean.getId()) {
				if (id != 0) {
					employeeTotalBean.setC_late(c_late);
					employeeTotalBean.setC_early(c_early);
					employeeTotalBean.setC_leave(c_leave);
					employeeTotalBean.setC_overday(c_overday);
					employeeTotalBean.setOvertime(overtime);
					employeeTotalBeanList.add(employeeTotalBean);
				}
				employeeTotalBean = new EmployeeTotalBean();
				employeeTotalBean.setDepartment(employeeDetailBean.getDepartment());
				employeeTotalBean.setName(employeeDetailBean.getName());
				employeeTotalBean.setId(employeeDetailBean.getId());
				// 换人了
				c_late = 0;
				c_early = 0;
				c_leave = 0;
				c_overday = 0;
				overtime = 0;
			}
			if (employeeDetailBean.getStatus().compareTo(WorkStatus.CHIDAO) == 0) {
				c_late++;
			}
			if (employeeDetailBean.getStatus().compareTo(WorkStatus.ZAOTUI) == 0) {
				c_early++;
			}
			if (employeeDetailBean.getStatus().compareTo(WorkStatus.QINGJIA) == 0) {
				c_leave++;
			}
			if (employeeDetailBean.getStatus().compareTo(WorkStatus.JIABAN) == 0) {
				c_overday++;

				// 判断，若上班或下班时间未打卡，但是因为是加班，就算成是属性设置中的上班时间和下班时间
				if (employeeDetailBean.getBeginTime().equals("")) {
					employeeDetailBean.setBeginTime(Business.readValue("work.begin.time"));
				}
				if (employeeDetailBean.getEndTime().equals("")) {
					employeeDetailBean.setEndTime(Business.readValue("work.end.time"));
				}

				// 计算两个时间的小时差，也就是加班多少时间
				overtime += minusDate(employeeDetailBean.getBeginTime(), employeeDetailBean.getEndTime());
			}
			id = employeeDetailBean.getId();
		}
		return employeeTotalBeanList;

	}

	/**
	 * 
	 * <p>Discription:[计算两个时间之间相差多少小时]</p>
	 * Created on 2012-7-2
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @throws ParseException 
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static int minusDate(String beginTime, String endTime) throws ParseException {
		long begin = DateUtils.parseDate(beginTime, "HH:mm:ss").getTime();
		long end = DateUtils.parseDate(endTime, "HH:mm:ss").getTime();
		return (int) ((end - begin) / (1000 * 60 * 60)) + 1;
	}

	/**
	 * 
	 * <p>
	 * Discription:[根据传入的employeeTotalBeanList导出统计总表，文件保存位置为传入的file值]
	 * </p>
	 * Created on 2012-7-1
	 * 
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @throws IOException
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static void GenerateTotalExcel(String file, List<EmployeeTotalBean> employeeTotalBeanList)
			throws IOException {
		Workbook workbook = new SXSSFWorkbook(500);
		Sheet sheet = workbook.createSheet();
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("部门");
		row.createCell(1).setCellValue("姓名");
		row.createCell(2).setCellValue("登记号");
		row.createCell(3).setCellValue("迟到次数");
		row.createCell(4).setCellValue("早退次数");
		row.createCell(5).setCellValue("请假天数");
		row.createCell(6).setCellValue("加班天数");
		row.createCell(7).setCellValue("加班时间");
		for (int rownum = 0; rownum < employeeTotalBeanList.size(); rownum++) {
			EmployeeTotalBean employeeTotalBean = employeeTotalBeanList.get(rownum);
			row = sheet.createRow(rownum + 1);
			row.createCell(0).setCellValue(employeeTotalBean.getDepartment());
			row.createCell(1).setCellValue(employeeTotalBean.getName());
			row.createCell(2).setCellValue(employeeTotalBean.getId());
			row.createCell(3).setCellValue(employeeTotalBean.getC_late());
			row.createCell(4).setCellValue(employeeTotalBean.getC_early());
			row.createCell(5).setCellValue(employeeTotalBean.getC_leave());
			row.createCell(6).setCellValue(employeeTotalBean.getC_overday());
			row.createCell(7).setCellValue(employeeTotalBean.getOvertime());
		}

		FileOutputStream out = new FileOutputStream(file);
		workbook.write(out);
		out.close();
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
	public static List<Vacation> getVacations() throws ParseException, IOException, URISyntaxException {
		List<Vacation> list = new ArrayList<Vacation>();

		BufferedReader bufferedReader = new BufferedReader(new FileReader(FileConst.PATH_VACATIONS));
		String str = "";
		Vacation vacation = null;
		while ((str = bufferedReader.readLine()) != null) {
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
	public static void saveVacations(File file) throws IOException, URISyntaxException {
		BufferedReader br = new BufferedReader(new FileReader(file));

		FileOutputStream out = new FileOutputStream(FileConst.PATH_VACATIONS);
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
	 * Discription:[从文件中读取字符串到集合中，然后返回WorkDate的集合]
	 * </p>
	 * Created on 2012-7-1
	 * 
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @throws ParseException
	 * @throws IOException
	 * @throws URISyntaxException 
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static List<WorkDate> getWorkDates() throws ParseException, IOException, URISyntaxException {
		List<WorkDate> list = new ArrayList<WorkDate>();

		BufferedReader bufferedReader = new BufferedReader(new FileReader(FileConst.PATH_WORKDATES));
		String str = "";
		while ((str = bufferedReader.readLine()) != null) {
			WorkDate workDate = new WorkDate();
			workDate.setDate(DateUtils.parseDate(str, "yyyy-MM-dd"));
			list.add(workDate);
		}
		return list;
	}

	/**
	 * 
	 * <p>Discription:[导入导入周末信息到FileConst.PATH_WORKDATES中]</p>
	 * Created on 2012-7-2
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @throws URISyntaxException 
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static void saveWorkDates(File file) throws IOException, URISyntaxException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

		FileOutputStream out = new FileOutputStream(FileConst.PATH_WORKDATES);

		String str = "";
		while ((str = bufferedReader.readLine()) != null) {
			out.write(str.concat("\r\n").getBytes());
		}
		out.flush();
		out.close();
	}

	/**
	 * 
	 * <p>Discription:[通过导入的考勤记录和相关设置计算职工本月详细考勤记录]</p>
	 * Created on 2012-6-30
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static List<EmployeeDetailBean> generateEmployeeDetailList(List<EntranceBean> entranceBeanList) {
		EmployeeDetailBean employeeDetailBean = null;
		List<EmployeeDetailBean> employeeDetailBeanList = new ArrayList<EmployeeDetailBean>();

		String mdate = "";
		for (EntranceBean entranceBean : entranceBeanList) {

			// 新开始一天的打卡记录
			if (!mdate.equals(entranceBean.getId() + entranceBean.getDate())) {
				if (!mdate.equals("")) {
					employeeDetailBeanList.add(employeeDetailBean);
				}

				employeeDetailBean = new EmployeeDetailBean();
				employeeDetailBean.setDepartment(entranceBean.getDepartment());
				employeeDetailBean.setName(entranceBean.getName());
				employeeDetailBean.setId(entranceBean.getId());
				employeeDetailBean.setDate(entranceBean.getDate());
				employeeDetailBean.setBeginTime("");
				employeeDetailBean.setEndTime("");
				if (entranceBean.isVacation()) {
					// 如果是休息，则认为今天打卡是加班。否则先不处理，等以后再统一处理。
					employeeDetailBean.setStatus(WorkStatus.JIABAN);
				}

				// 确保是一个人同一天
				mdate = entranceBean.getId() + entranceBean.getDate();
			}

			String entranceBeanTime = entranceBean.getTime();
			if (entranceBeanTime.compareTo("12:00:00") < 0) {// 上午
				employeeDetailBean.setBeginTime(entranceBeanTime);
			} else if (entranceBeanTime.compareTo("12:00:00") >= 0 && entranceBeanTime.compareTo("13:00:00") <= 0) {// 中午
				if (employeeDetailBean.getBeginTime().equals("")) {
					employeeDetailBean.setBeginTime(entranceBeanTime);
				} else {
					employeeDetailBean.setEndTime(entranceBeanTime);
				}
			} else if (entranceBeanTime.compareTo("13:00:00") > 0) {// 下午
				employeeDetailBean.setEndTime(entranceBeanTime);
			}
		}
		employeeDetailBeanList = getEmployeeDetailWorkStatus(employeeDetailBeanList);
		return employeeDetailBeanList;
	}

	/**
	 * 
	 * <p>Discription:[通过EmployeeDetailBean的上班时间，下班时间计算EmployeeDetailBean的打卡情况]</p>
	 * Created on 2012-7-2
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static List<EmployeeDetailBean> getEmployeeDetailWorkStatus(List<EmployeeDetailBean> employeeDetailBeanList) {

		// 读取所有设置的信息。
		Map<String, String> info = readProperties();
		String beginTime = info.get("work.begin.time");
		String endTime = info.get("work.end.time");

		for (EmployeeDetailBean employeeDetailBean : employeeDetailBeanList) {
			if (employeeDetailBean.getStatus() != null
					&& employeeDetailBean.getStatus().compareTo(WorkStatus.JIABAN) == 0) {
				continue;
			}

			if ("".equals(employeeDetailBean.getBeginTime())) {
				employeeDetailBean.setStatus(WorkStatus.CHIDAO);
			} else if ("".equals(employeeDetailBean.getEndTime())) {
				employeeDetailBean.setStatus(WorkStatus.ZAOTUI);
			} else if (employeeDetailBean.getBeginTime().compareTo(beginTime) > 0) {
				employeeDetailBean.setStatus(WorkStatus.CHIDAO);
			} else if (employeeDetailBean.getEndTime().compareTo(endTime) < 0) {
				employeeDetailBean.setStatus(WorkStatus.ZAOTUI);
			} else {
				employeeDetailBean.setStatus(WorkStatus.ZHENGCHANG);
			}
		}
		return employeeDetailBeanList;
	}

	/**
	 * 
	 * <p>Discription:[根据key读取value值]</p>
	 * Created on 2012-6-30
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static String readValue(String key) {
		Properties properties = new Properties();
		try {
			InputStream is = new FileInputStream(new File(FileConst.PATH_PROPERTIES));
			properties.load(is);
			String value = properties.getProperty(key);
			is.close();
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * <p>Discription:[读取所有键值对，放入Map<String, String>中]</p>
	 * Created on 2012-6-30
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static Map<String, String> readProperties() {
		Properties properties = new Properties();
		try {
			InputStream is = new FileInputStream(new File(FileConst.PATH_PROPERTIES));
			properties.load(is);
			Map<String, String> map = new HashMap<String, String>();
			@SuppressWarnings("unchecked")
			Enumeration<String> enumvalue = (Enumeration<String>) properties.propertyNames();
			while (enumvalue.hasMoreElements()) {
				String key = enumvalue.nextElement();
				String value = properties.getProperty(key);
				map.put(key, value);
			}
			is.close();
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * <p>Discription:[把上下班等属性写入到配置文件中]</p>
	 * Created on 2012-7-3
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static void writeProperties(String key, String value, String comments) throws IOException {
		Properties properties = new Properties();
		InputStream is = new FileInputStream(new File(FileConst.PATH_PROPERTIES));
		properties.load(is);
		properties.setProperty(key, value);
		OutputStream os = new FileOutputStream(new File(FileConst.PATH_PROPERTIES));
		properties.store(os, comments);
		os.flush();
		os.close();
		is.close();
	}

	public static void main(String args[]) {
		Entrance entrance = new Entrance();
		entrance.setSize(500, 300);
		entrance.setVisible(true);
	}
}
