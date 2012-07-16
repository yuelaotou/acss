package com.neusoft.acss.test;

import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

public class Test {

	/**
	 * <p>Discription:[方法功能中文描述]</p>
	 * Created on 2012-7-9
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @throws ParseException 
	 * @throws: 
	 */
	public static void main(String[] args) {

		System.out.println(StringUtils.isEmpty(null));
		System.out.println(StringUtils.isNumeric("5d"));

		try {
			Object value = MethodUtils.invokeExactMethod(new Test(), "generate" + "A", new Object[] { "1",
					new Integer(2) },
					new Class<?>[] { Class.forName("java.lang.String"), Class.forName("java.lang.Integer") });
			// Object value = FieldUtils.readField(Test.class, "generate" + "A('sssdd')", true);
			System.out.println(value);
			// edb.setValue(key.toString(), value);
		} catch (Exception e) {
			// throw new BizException("EmployeeDetailBean#getPropertiesMap配置错误，属性是：" + key, e);
			e.printStackTrace();
		}

	}

	public static String generateA() {
		return "dd";
	}

	public static String generateA(String s, Integer dd) {
		return s + dd;
	}

	public static String generateA(String s, int dd) {
		return s + dd;
	}

	public static String generateA(String s) {
		return s;
	}
}
