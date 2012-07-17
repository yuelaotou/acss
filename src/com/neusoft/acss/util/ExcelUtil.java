package com.neusoft.acss.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

import com.neusoft.acss.Acss;
import com.neusoft.acss.bean.EmployeeDetailBean;
import com.neusoft.acss.bean.EmployeeTotalBean;
import com.neusoft.acss.bean.EvectionBean;
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
	 * <p>Discription:[解析EXCEL2007版本的外出登记表，存储在List&lt;{@link EvectionBean}&gt;中]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public static List<EvectionBean> parseExcel2EvectionList() {
		List<EvectionBean> list = new ArrayList<EvectionBean>();

		XSSFRow row = null;
		EvectionBean eb = null;
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
					eb = new EvectionBean();
					eb.setName(name);
					eb.setMonth(month);
					eb.setDay(StringUtils.leftPad(row.getCell(0).toString().replace("日", ""), 2, "0"));
					eb.setTevection(row.getCell(1).toString().trim());
					eb.setEvection_remote(row.getCell(2).toString().trim());
					eb.setEvection_locale(row.getCell(3).toString().trim());
					eb.setOvertime(row.getCell(4).toString().trim());
					eb.setLeave_sick(row.getCell(5).toString().trim());
					eb.setLeave_thing(row.getCell(6).toString().trim());
					eb.setLeave_year(row.getCell(7).toString().trim());
					if (eb.isEmpty()) {
						continue;
					}
					list.add(eb);
				}
				is.close();
			} catch (FileNotFoundException e) {
				throw new BizException("能进循环，就不会发生的异常，吗的", e);
			} catch (IOException e) {
				throw new BizException("读取外出登记表IO异常：" + file.getName(), e);
			}
		}
		return list;
	}

	/**
	 * <p>Discription:[根据传入的employeeDetailBeanList生成DetailExcel，文件保存在Consts.PATH_EMPLOYEEDETAIL]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public static void exportEmployeeDetailExcel(List<EmployeeDetailBean> employeeDetailBeanList,
			Map<String, Object[]> m) {
		Workbook wb = new SXSSFWorkbook(500);
		Sheet sheet = wb.createSheet();
		sheet.createFreezePane(0, 1);
		// 生成表头
		Row row = sheet.createRow(0);
		row.setHeightInPoints(Consts.ROW_HEIGHT);
		Iterator<Entry<String, Object[]>> it = m.entrySet().iterator();
		CellStyle style_head = getHeadCellStyle(wb);
		int i = 0;
		while (it.hasNext()) {
			Map.Entry<String, Object[]> entry = it.next();
			Object[] values = entry.getValue();
			if (values[1].toString().length() > 6) {
				sheet.setColumnWidth(i, Consts.COLUMN_WIDTH + 1800);
			} else if (values[1].toString().length() > 4) {
				sheet.setColumnWidth(i, Consts.COLUMN_WIDTH + 600);
			} else {
				sheet.setColumnWidth(i, Consts.COLUMN_WIDTH);
			}
			Cell c = row.createCell(i);
			c.setCellStyle(style_head);
			c.setCellValue(values[1].toString());
			i++;
		}

		// 生成表体，填充内容
		EmployeeDetailBean edb = null;
		CellStyle style_body = getBodyCellStyle(wb);
		for (int rownum = 0; rownum < employeeDetailBeanList.size(); rownum++) {
			edb = employeeDetailBeanList.get(rownum);
			row = sheet.createRow(rownum + 1);
			row.setHeightInPoints(Consts.ROW_HEIGHT - 2);

			it = m.entrySet().iterator();
			i = 0;
			while (it.hasNext()) {
				Map.Entry<String, Object[]> entry = it.next();
				Object key = entry.getKey();
				Object value = edb.getValue(key.toString());

				Cell c = row.createCell(i);
				c.setCellStyle(style_body);
				c.setCellValue(value == null ? "" : value.toString());
				i++;
			}
		}
		try {
			FileOutputStream out = new FileOutputStream(Consts.PATH_EMPLOYEEDETAIL);
			wb.write(out);
			out.close();
		} catch (FileNotFoundException e) {
			throw new BizException("找不到文件：" + Consts.PATH_EMPLOYEEDETAIL + "，或文件正在被占用", e);
		} catch (IOException e) {
			throw new BizException("导出EmployeeDetailExcel出错，IO异常", e);
		}
	}

	/**
	 * <p>Discription:[根据传入的employeeTotalBeanList导出统计总表，文件保存在Consts.PATH_EMPLOYEETOTAL]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public static void exportEmployeeTotalExcel(List<EmployeeTotalBean> employeeTotalBeanList, Map<String, Object[]> m) {
		Workbook wb = new SXSSFWorkbook(500);
		Sheet sheet = wb.createSheet();
		sheet.createFreezePane(0, 1);

		// 生成表头
		Row row = sheet.createRow(0);
		row.setHeightInPoints(Consts.ROW_HEIGHT);
		Iterator<Entry<String, Object[]>> it = m.entrySet().iterator();
		CellStyle style_head = getHeadCellStyle(wb);
		int i = 0;
		while (it.hasNext()) {
			Map.Entry<String, Object[]> entry = it.next();
			Object[] values = entry.getValue();
			if (values[1].toString().length() > 6) {
				sheet.setColumnWidth(i, Consts.COLUMN_WIDTH + 1800);
			} else if (values[1].toString().length() > 4) {
				sheet.setColumnWidth(i, Consts.COLUMN_WIDTH + 600);
			} else {
				sheet.setColumnWidth(i, Consts.COLUMN_WIDTH);
			}
			Cell c = row.createCell(i);
			c.setCellStyle(style_head);
			c.setCellValue(values[1].toString());
			i++;
		}

		// 生成表体，填充内容
		EmployeeTotalBean etb = null;
		CellStyle style_body = getBodyCellStyle(wb);
		for (int rownum = 0; rownum < employeeTotalBeanList.size(); rownum++) {
			etb = employeeTotalBeanList.get(rownum);
			row = sheet.createRow(rownum + 1);
			row.setHeightInPoints(Consts.ROW_HEIGHT - 2);

			it = m.entrySet().iterator();
			i = 0;
			while (it.hasNext()) {
				Map.Entry<String, Object[]> entry = it.next();
				Object key = entry.getKey();
				Object value = etb.getValue(key.toString());

				Cell c = row.createCell(i);
				c.setCellStyle(style_body);
				c.setCellValue(value == null ? "" : value.toString());
				i++;
			}
		}

		try {
			FileOutputStream out = new FileOutputStream(Consts.PATH_EMPLOYEETOTAL);
			wb.write(out);
			out.close();
		} catch (FileNotFoundException e) {
			throw new BizException("找不到文件：" + Consts.PATH_EMPLOYEETOTAL + "，或文件正在被占用", e);
		} catch (IOException e) {
			throw new BizException("导出EmployeeTotalExcel出错，IO异常", e);
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

	public static void main(String[] args) {
		Acss a = new Acss();
		a.setSize(500, 300);
		a.setVisible(true);
	}

}
