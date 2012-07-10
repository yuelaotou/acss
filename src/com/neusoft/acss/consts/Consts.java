package com.neusoft.acss.consts;

/**
 * <p> Title: [Consts常量类]</p>
 * <p> Description: [常量类，各种文件保存的位置，路径，文件名等存入到此。]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public abstract class Consts {

	/**
	 * 设置属性保存的位置，例如：上下班时间
	 */
	public static String PATH_PROPERTIES = "D:\\acss\\info.properties";

	/**
	 * 导入节假日的文件保存位置
	 */
	public static String PATH_VACATIONS = "D:\\acss\\vacations.txt";

	/**
	 * 导入工作日或串休文件的保存位置
	 */
	public static String PATH_WORKDAYS = "D:\\acss\\workdates.txt";

	/**
	 * 个人出差登记表保存路径
	 */
	public static String FOLDER_EVECTIONS = "D:\\acss\\evection";

	/**
	 * 考勤明细表保存位置
	 */
	public static String PATH_EMPLOYEEDETAIL = "D:\\acss\\employeeDetail.xlsx";

	/**
	 * 考勤统计总表保存位置
	 */
	public static String PATH_EMPLOYEETOTAL = "D:\\acss\\employeeTotal.xlsx";

	/**
	 * 日期正则表达式 yyyy-MM-dd
	 */
	public static String REGEX_DATE = "[0-9]{4}-[0-9]{2}-[0-9]{2}";

	/**
	 * 时间正则表达式 HH:mm:ss
	 */
	public static String REGEX_TIME = "[0-9]{2}:[0-9]{2}:[0-9]{2}";
	
	/**
	 * 宽限时间正则表达式 Number(2)
	 */
	public static String REGEX_GRACE = "[0-9]{2}";

}
