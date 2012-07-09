package com.neusoft.acss.test;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

public class Test {

	/**
	 * <p>Discription:[方法功能中文描述]</p>
	 * Created on 2012-7-9
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @throws ParseException 
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 * @throws: 
	 */
	public static void main(String[] args) throws ParseException {
		Date d_tevening = DateUtils.addMinutes(DateUtils.parseDate("17:30:10", "HH:mm:ss"), 5);
		String s = DateFormatUtils.format(d_tevening, "HH:mm:ss");
		System.out.println(s);
	}

}
