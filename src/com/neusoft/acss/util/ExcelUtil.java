package com.neusoft.acss.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.neusoft.acss.bean.EmployeeDetailBean;
import com.neusoft.acss.bean.EmployeeTotalBean;
import com.neusoft.acss.bean.EvectionBean;
import com.neusoft.acss.consts.Consts;
import com.neusoft.acss.enums.WorkStatus;

public class ExcelUtil {

	/**
	 * <p>
	 * Discription:[解析EXCEL2007版本的外出登记表，存储在List&lt;{@link EvectionBean}&gt;中]
	 * </p>
	 * Created on 2012-6-29
	 * 
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @throws IOException
	 * @throws ParseException
	 * @throws URISyntaxException 
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static List<EvectionBean> parseExcel2EvectionList() throws ParseException, IOException {
		List<EvectionBean> list = new ArrayList<EvectionBean>();

		XSSFRow row = null;
		EvectionBean eb = null;
		File folder = new File(Consts.FOLDER_EVECTIONS);
		if (!folder.exists()) {
			folder.mkdir();
			throw new IOException("请在 " + Consts.FOLDER_EVECTIONS + " 路径下放入所有人员的外出登记表");
		}
		File files[] = folder.listFiles();
		for (File file : files) {
			String name = StringUtils.substringBefore(file.getName(), "-");
			String month = StringUtils.substringBetween(file.getName(), "-", ".");
			InputStream is = new FileInputStream(file);
			XSSFWorkbook xwb = new XSSFWorkbook(is);
			XSSFSheet sheet = xwb.getSheetAt(0);
			// 根据表格的样式，确定从第4行开始读取，读到总行-2。其实也是4+31行。
			for (int i = sheet.getFirstRowNum() + 3; i < sheet.getPhysicalNumberOfRows() - 2; i++) {
				row = sheet.getRow(i);
				eb = new EvectionBean();
				eb.setName(name);
				eb.setMonth(month);
				eb.setDay(StringUtils.leftPad(row.getCell(0).toString().replace("日", ""), 2, "0"));
				eb.setEvection(row.getCell(1).toString().trim());
				eb.setOutPosition(row.getCell(2).toString().trim());
				eb.setInPosition(row.getCell(3).toString().trim());
				eb.setOvertime(row.getCell(4).toString().trim());
				eb.setSick_leave(row.getCell(5).toString().trim());
				eb.setThing_leave(row.getCell(6).toString().trim());
				eb.setYear_leave(row.getCell(7).toString().trim());
				if (eb.isEmpty()) {
					continue;
				}
				list.add(eb);
			}
			is.close();
		}
		return list;
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
					employeeDetailBean.setBeginTime("");
				}
				if (employeeDetailBean.getEndTime().equals("")) {
					employeeDetailBean.setEndTime("");
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
	 * <p>Discription:[方法功能中文描述]</p>
	 * Created on 2012-7-9
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 * @throws: 
	 */
	public static void main(String[] args) {

	}

}
