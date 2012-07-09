package com.neusoft.acss.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.neusoft.acss.Acss;
import com.neusoft.acss.consts.Consts;

public class PropUtil {

	/**
	 * 
	 * <p>Discription:[读取所有键值对，放入Map<String, String>中]</p>
	 * Created on 2012-6-30
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @throws IOException 
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static Map<String, String> readProperties() throws IOException {
		Properties properties = new Properties();
		InputStream is = new FileInputStream(new File(Consts.PATH_PROPERTIES));
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
	}

	/**
	 * 
	 * <p>Discription:[把上下班时间等属性写入到配置文件中]</p>
	 * Created on 2012-7-3
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static void writeProperties(String key, String value, String comments) throws IOException {
		Properties properties = new Properties();
		InputStream is = new FileInputStream(new File(Consts.PATH_PROPERTIES));
		properties.load(is);
		properties.setProperty(key, value);
		OutputStream os = new FileOutputStream(new File(Consts.PATH_PROPERTIES));
		properties.store(os, comments);
		os.flush();
		os.close();
		is.close();
	}
	
	public static void main(String args[]) {
		Acss a = new Acss();
		a.setSize(500, 300);
		a.setVisible(true);
	}

}
