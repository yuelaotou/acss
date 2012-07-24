package com.neusoft.acss.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.neusoft.acss.consts.Consts;
import com.neusoft.acss.exception.BizException;

/**
 * <p> Title: [Properties操作类]</p>
 * <p> Description: [操作properties文件。主要是进行一些属性的设置，比如：上下班时间等]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public final class PropUtil {

	/**
	 * 隐藏公用构造方法<p>
	 */
	private PropUtil() {
		// noop
	}

	/**
	 * <p>Description:[读取所有键值对，放入Map<String, String>中]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public static Map<String, String> readProperties() {
		Properties properties = new Properties();
		Map<String, String> map;
		try {
			InputStream is = new FileInputStream(new File(Consts.PATH_PROPERTIES));
			properties.load(is);
			map = new HashMap<String, String>();
			@SuppressWarnings("unchecked")
			Enumeration<String> enumvalue = (Enumeration<String>) properties.propertyNames();
			while (enumvalue.hasMoreElements()) {
				String key = enumvalue.nextElement();
				String value = properties.getProperty(key);
				map.put(key, value);
			}
			is.close();
			return map;
		} catch (FileNotFoundException e) {
			throw new BizException("找不到属性文件：" + Consts.PATH_PROPERTIES, e);
		} catch (IOException e) {
			throw new BizException("操作文件出错：" + Consts.PATH_PROPERTIES, e);
		}
	}

	/**
	 * <p>Description:[把上下班时间等属性写入到配置文件中]</p>
	 * Created on 2012-7-10
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public static void writeProperties(String key, String value, String comments) {
		Properties properties = new Properties();
		try {
			InputStream is = new FileInputStream(new File(Consts.PATH_PROPERTIES));
			properties.load(is);
			properties.setProperty(key, value);
			OutputStream os = new FileOutputStream(new File(Consts.PATH_PROPERTIES));
			properties.store(os, comments);
			os.flush();
			os.close();
			is.close();
		} catch (FileNotFoundException e) {
			throw new BizException("找不到属性文件：" + Consts.PATH_PROPERTIES, e);
		} catch (IOException e) {
			throw new BizException("操作文件出错：" + Consts.PATH_PROPERTIES + "，无法写入", e);
		}
	}

}
