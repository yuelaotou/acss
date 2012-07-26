package com.kd.acss.consts;

/**
 * <p> Title: [Consts常量类]</p>
 * <p> Description: [常量类，各种文件保存的位置，路径，文件名等存入到此。]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * @version 1.0
 */
public final class Consts {

	/**
	 * 设置属性保存的位置，例如：上下班时间
	 */
	public final static String PATH_PROPERTIES = "C:\\acss\\info\\配置文件.properties";

	/**
	 * 个人出差登记表保存路径
	 */
	public final static String FOLDER_EVECTIONS = "C:\\acss\\evection";

	/**
	 * 假期维护表的保存位置
	 */
	public final static String PATH_HOLIDAYS = "C:\\acss\\info\\假期维护表.xlsx";

	/**
	 * 职工基本信息表保存位置
	 */
	public final static String PATH_EMPLOYEEBASE = "C:\\acss\\info\\职工基本信息表.xlsx";

	/**
	 * 考勤明细表保存位置
	 */
	public final static String PATH_EMPLOYEEDETAIL = "C:\\acss\\考勤详细信息表.xlsx";

	/**
	 * 考勤统计总表保存位置
	 */
	public final static String PATH_EMPLOYEETOTAL = "C:\\acss\\考勤统计总表.xlsx";

	/**
	 * 日期正则表达式 yyyy-MM-dd，满足基本判断
	 */
	public final static String REGEX_DATE = "[0-9]{4}-[0-9]{2}-[0-9]{2}";

	/**
	 * 时间正则表达式 HH:mm:ss，满足基本判断
	 */
	public final static String REGEX_TIME = "[0-9]{2}:[0-9]{2}:[0-9]{2}";

	/**
	 * 导出EXCEL表格的行高 = 18
	 */
	public final static int ROW_HEIGHT = 18;

	/**
	 * 导出EXCEL表格的列宽 = 2600
	 */
	public final static int COLUMN_WIDTH = 2600;

}
