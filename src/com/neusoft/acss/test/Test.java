package com.neusoft.acss.test;

import java.io.IOException;
import java.text.ParseException;

import com.neusoft.acss.util.ExcelUtil;

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
		try {
			ExcelUtil.parseExcel2EvectionList();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
