package com.neusoft.acss.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.neusoft.acss.bean.AcssBean;


public class NoneUsedUtil {

	/**
	 * <p>Discription:[方法功能中文描述]</p>
	 * Created on 2012-7-9
	 * @author: 杨光 - admin@ursun.cn
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static void main(String[] args) {

	}
	

	/**
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
	public static List<AcssBean> ParseExcel2003(File file) throws IOException, ParseException {
		InputStream is = new FileInputStream(file);
		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(is));
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = null;
		AcssBean acssBean = null;

		// List<Vacation> vacationList = TxtUtil.getVacations();
		// List<WorkDay> workDateList = TxtUtil.getWorkDays();

		List<AcssBean> list = new ArrayList<AcssBean>();
		for (int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			acssBean = new AcssBean();

			acssBean.setDepartment(row.getCell(0).toString().trim());
			acssBean.setName(row.getCell(1).toString().trim());
			acssBean.setId(Integer.parseInt(row.getCell(2).toString().trim()));

			String date = StringUtils.substringBefore(row.getCell(3).toString(), " ");
			String time = StringUtils.substringAfter(row.getCell(3).toString(), " ");
			acssBean.setDate(date);
			acssBean.setTMorning(StringUtils.leftPad(time, 8, "0"));
			acssBean.setMachine(Integer.parseInt(row.getCell(4).toString().trim()));
			acssBean.setCode(row.getCell(5).toString().trim());
			acssBean.setType(row.getCell(6).toString().trim());
			acssBean.setCardnum(row.getCell(7).toString().trim());
			list.add(acssBean);
		}
		// Acss.checkVacation(list, vacationList, workDateList);
		is.close();
		return list;
	}

	/**
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
	public static List<AcssBean> ParseExcel2007(File file) throws ParseException, IOException {
		InputStream is = new FileInputStream(file);
		XSSFWorkbook xwb = new XSSFWorkbook(is);
		XSSFSheet sheet = xwb.getSheetAt(0);
		XSSFRow row = null;
		AcssBean acssBean = null;

		// List<Vacation> vacationList = TxtUtil.getVacations();
		// List<WorkDay> workDateList = TxtUtil.getWorkDays();

		List<AcssBean> list = new ArrayList<AcssBean>();
		for (int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			acssBean = new AcssBean();
			acssBean.setDepartment(row.getCell(0).toString().trim());
			acssBean.setName(row.getCell(1).toString().trim());
			acssBean.setId(Integer.parseInt(row.getCell(2).toString().trim()));

			String date = StringUtils.substringBefore(row.getCell(3).toString(), " ");
			String time = StringUtils.substringAfter(row.getCell(3).toString(), " ");
			acssBean.setDate(date);
			acssBean.setTMorning(StringUtils.leftPad(time, 8, "0"));
			acssBean.setMachine(Integer.parseInt(row.getCell(4).toString().trim()));
			acssBean.setCode(row.getCell(5).toString().trim());
			acssBean.setType(row.getCell(6).toString().trim());
			acssBean.setCardnum(row.getCell(7).toString().trim());
			list.add(acssBean);
		}
		// Acss.checkVacation(list, vacationList, workDateList);
		is.close();
		return list;
	}


}
