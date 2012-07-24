package com.neusoft.acss.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.neusoft.acss.bean.EmployeeBean;
import com.neusoft.acss.bean.EvectionBean;
import com.neusoft.acss.bean.Info;
import com.neusoft.acss.column.detail.IColumnDetail;
import com.neusoft.acss.column.total.IColumnTotal;
import com.neusoft.acss.consts.Consts;
import com.neusoft.acss.exception.BizException;

/**
 * <p> Title: [Excel表格操作类]</p>
 * <p> Description: [读取、导出Excel类。主要关注在读取外出登记表，导出生成详细信息表和统计总表]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public final class ExcelUtil {

	/**
	 * 隐藏公用构造方法<p>
	 */
	private ExcelUtil() {
		// noop
	}

	/**
	 * <p>Description:[解析EXCEL2007版本的外出登记表，存储在List&lt;{@link EvectionBean}&gt;中]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public static List<EvectionBean> parseExcel2EvectionList() {
		List<EvectionBean> list = new ArrayList<EvectionBean>();

		XSSFRow row = null;
		EvectionBean evb = null;
		File folder = new File(Consts.FOLDER_EVECTIONS);
		if (!folder.exists()) {
			folder.mkdir();
			throw new BizException("请在 " + Consts.FOLDER_EVECTIONS + " 路径下放入所有人员的外出登记表");
		}
		File files[] = folder.listFiles();
		for (File file : files) {
			String name = StringUtils.substringBefore(file.getName(), "-");
			String month = StringUtils.substringBetween(file.getName(), "-", ".");
			try {
				InputStream is = new FileInputStream(file);
				XSSFWorkbook xwb = new XSSFWorkbook(is);
				XSSFSheet sheet = xwb.getSheetAt(0);
				// 根据表格的样式，确定从第4行开始读取，读到总行-2。其实也是4+31行。
				for (int i = sheet.getFirstRowNum() + 3; i < sheet.getPhysicalNumberOfRows() - 2; i++) {
					row = sheet.getRow(i);
					evb = new EvectionBean();
					evb.setName(name);
					evb.setMonth(month);
					evb.setDay(StringUtils.leftPad(row.getCell(0).toString().replace("日", ""), 2, "0"));
					evb.setTevection(row.getCell(1).toString().trim());
					evb.setEvection_remote(row.getCell(2).toString().trim());
					evb.setEvection_locale(row.getCell(3).toString().trim());
					evb.setOvertime(row.getCell(4).toString().trim());
					evb.setLeave_sick(row.getCell(5).toString().trim());
					evb.setLeave_thing(row.getCell(6).toString().trim());
					evb.setLeave_year(row.getCell(7).toString().trim());
					if (evb.isEmpty()) {
						continue;
					}
					list.add(evb);
				}
				is.close();
			} catch (FileNotFoundException e) {
				throw new BizException(e.getMessage(), e);
			} catch (IOException e) {
				throw new BizException("读取外出登记表IO异常：" + file.getName(), e);
			}
		}
		return list;
	}

	/**
	 * <p>Description:[解析EXCEL2007版本的职工基本信息表，存储在List&lt;{@link EmployeeBean}&gt;中]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public static List<EmployeeBean> parseExcel2EmployeeList() {
		List<EmployeeBean> list = new ArrayList<EmployeeBean>();

		XSSFRow row = null;
		EmployeeBean eb = null;
		File file = new File(Consts.PATH_EMPLOYEEBASE);
		if (!file.exists()) {
			throw new BizException("找不到职工基本信息表: " + Consts.PATH_EMPLOYEEBASE);
		}

		try {
			InputStream is = new FileInputStream(file);
			XSSFWorkbook xwb = new XSSFWorkbook(is);
			XSSFSheet sheet = xwb.getSheetAt(0);
			// 根据表格的样式，确定从第4行开始读取，读到总行-2。其实也是4+31行。
			for (int i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				eb = new EmployeeBean();
				eb.setCompany(row.getCell(0).toString().trim());
				eb.setDepartment(row.getCell(1).toString().trim());
				eb.setName(row.getCell(2).toString().trim());
				eb.setLocale(row.getCell(3).toString().trim());
				eb.setId(row.getCell(4).toString().trim());
				if (eb.isEmpty()) {
					continue;
				}
				list.add(eb);
			}
			is.close();
		} catch (FileNotFoundException e) {
			throw new BizException(e.getMessage(), e);
		} catch (IOException e) {
			throw new BizException("读取职工基本信息表IO异常：" + file.getName(), e);
		}
		return list;
	}

	/**
	 * <p>Description:[根据传入的考勤详细统计信息列表生成DetailExcel，文件保存在Consts.PATH_EMPLOYEEDETAIL]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public static void exportEmployeeDetailExcel(List<Map<String, String>> lm, List<Class<?>> lc) {
		Workbook wb = new SXSSFWorkbook(500);
		Sheet sheet = wb.createSheet();
		sheet.createFreezePane(0, 1);
		// 生成表头
		Row row = sheet.createRow(0);
		row.setHeightInPoints(Consts.ROW_HEIGHT);
		CellStyle style_head = getHeadCellStyle(wb);

		try {
			int i = 0;
			for (Class<?> clz : lc) {
				IColumnDetail c = (IColumnDetail) clz.newInstance();
				String description = c.getDescription();
				if (description.length() > 6) {
					sheet.setColumnWidth(i, Consts.COLUMN_WIDTH + 1800);
				} else if (description.length() > 4) {
					sheet.setColumnWidth(i, Consts.COLUMN_WIDTH + 600);
				} else {
					sheet.setColumnWidth(i, Consts.COLUMN_WIDTH);
				}

				Cell cell = row.createCell(i);
				cell.setCellStyle(style_head);
				cell.setCellValue(description);
				i++;
			}

			// 生成表体，填充内容
			CellStyle style_body = getBodyCellStyle(wb);
			for (int rownum = 0; rownum < lm.size(); rownum++) {
				Map<String, String> m = lm.get(rownum);
				row = sheet.createRow(rownum + 1);
				row.setHeightInPoints(Consts.ROW_HEIGHT - 2);

				i = 0;
				for (Class<?> clz : lc) {
					Cell cell = row.createCell(i);
					cell.setCellStyle(style_body);
					cell.setCellValue(m.get(clz.getName()));
					i++;
				}
			}
			FileOutputStream out = new FileOutputStream(Consts.PATH_EMPLOYEEDETAIL);
			wb.write(out);
			out.close();
		} catch (FileNotFoundException e) {
			throw new BizException("找不到文件：" + Consts.PATH_EMPLOYEEDETAIL + "，或文件正在被占用", e);
		} catch (IOException e) {
			throw new BizException("导出EmployeeDetailExcel出错，IO异常", e);
		} catch (InstantiationException e) {
			throw new BizException("其他异常", e);
		} catch (IllegalAccessException e) {
			throw new BizException("其他异常", e);
		}
	}

	/**
	 * <p>Description:[根据传入的employeeTotalBeanList导出统计总表，文件保存在Consts.PATH_EMPLOYEETOTAL]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public static void exportEmployeeTotalExcel(Info info, List<Class<?>> lc) {
		List<Map<String, String>> lm = info.getTotalList();
		Workbook wb = new SXSSFWorkbook(500);
		Sheet sheet = wb.createSheet();
		sheet.createFreezePane(0, 1);

		// 生成表头
		Row row = sheet.createRow(0);
		row.setHeightInPoints(Consts.ROW_HEIGHT);
		CellStyle style_head = getHeadCellStyle(wb);

		try {
			int i = 0;
			for (Class<?> clz : lc) {
				IColumnTotal c = (IColumnTotal) clz.newInstance();
				String description = c.getDescription();
				if (description.length() > 6) {
					sheet.setColumnWidth(i, Consts.COLUMN_WIDTH + 1800);
				} else if (description.length() > 4) {
					sheet.setColumnWidth(i, Consts.COLUMN_WIDTH + 600);
				} else {
					sheet.setColumnWidth(i, Consts.COLUMN_WIDTH);
				}

				Cell cell = row.createCell(i);
				cell.setCellStyle(style_head);
				cell.setCellValue(description);
				i++;
			}

			// 生成表体，填充内容
			CellStyle style_body = getBodyCellStyle(wb);
			for (int rownum = 0; rownum < lm.size(); rownum++) {
				Map<String, String> m = lm.get(rownum);
				row = sheet.createRow(rownum + 1);
				row.setHeightInPoints(Consts.ROW_HEIGHT - 2);

				i = 0;
				for (Class<?> clz : lc) {
					Cell cell = row.createCell(i);
					cell.setCellStyle(style_body);
					cell.setCellValue(m.get(clz.getName()));
					i++;
				}
			}
			FileOutputStream out = new FileOutputStream(Consts.PATH_EMPLOYEETOTAL);
			wb.write(out);
			out.close();
		} catch (FileNotFoundException e) {
			throw new BizException("找不到文件：" + Consts.PATH_EMPLOYEETOTAL + "，或文件正在被占用", e);
		} catch (IOException e) {
			throw new BizException("导出EmployeeTotalExcel出错，IO异常", e);
		} catch (InstantiationException e) {
			throw new BizException("其他异常", e);
		} catch (IllegalAccessException e) {
			throw new BizException("其他异常", e);
		}
	}

	private static CellStyle getHeadCellStyle(Workbook wb) {
		XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
		XSSFFont font = (XSSFFont) wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		style.setFont(font);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		return style;
	}

	private static CellStyle getBodyCellStyle(Workbook wb) {
		XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
		XSSFFont font = (XSSFFont) wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font.setFontName("Arial");
		style.setFont(font);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		return style;
	}

}
