package com.neusoft.acss.test;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.neusoft.acss.bean.EmployeeDetailBean;

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
		List<EmployeeDetailBean> employeeDetailBeanList = new ArrayList<EmployeeDetailBean>();
		// try {
		// ExcelUtil.GenerateDetailExcel(Consts.PATH_EMPLOYEEDETAIL, employeeDetailBeanList);
		// } catch (IOException e1) {
		// e1.printStackTrace();
		// }

		EmployeeDetailBean employeeDetailBean = new EmployeeDetailBean();
		employeeDetailBean.setEvection_remote("我是外地出差");
		employeeDetailBean.setDepartment("我是部门");

		try {
			// Object o = FieldUtils.readField(employeeDetailBean, "evection_remote");
			Object o = FieldUtils.readField(employeeDetailBean, "evection_remote", true);
			Object o1 = FieldUtils.readField(employeeDetailBean, "department", true);
			Object o2 = FieldUtils.readField(employeeDetailBean, "evection_locale", true);
			System.out.println(o);
			System.out.println(o1);
			System.out.println(o2);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		// Class<?> clazz = employeeDetailBean.getClass();
		// PropertyDescriptor pd;
		// Field fs[] = clazz.getSuperclass().getDeclaredFields();
		// for (Field field : fs) {
		// try {
		// pd = new PropertyDescriptor(field.getName(), clazz);
		// Method m = pd.getReadMethod();
		// System.out.println("==>" + pd.getDisplayName() + pd.getShortDescription() + pd.getName());
		// System.out.println(field.getName() + "==" + field.getGenericType().toString());
		// if (field.getGenericType().toString().equals("class java.lang.String")) {
		// if (field.getName().equals("department")) {
		// String val = (String) m.invoke(employeeDetailBean);
		// System.out.println(val);
		// }
		// // String val = (String) m.invoke(EmployeeDetailBean.class);
		// } else if (field.getGenericType().toString().equals("class java.lang.Integer")) {
		// // Integer val = (Integer) m.invoke(EmployeeDetailBean.class);
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }

	}

	public String ss(Object obj) {
		Class<?> clazz = obj.getClass();
		PropertyDescriptor pd;

		Field fs[] = clazz.getSuperclass().getDeclaredFields();
		for (Field field : fs) {
			try {
				pd = new PropertyDescriptor(field.getName(), clazz);
				Method m = pd.getReadMethod();
				System.out.println(field.getName() + "==" + field.getGenericType().toString());
				if (field.getGenericType().toString().equals("class java.lang.String")) {
					if (field.getName().equals("department")) {
						String val = (String) m.invoke(obj);
						System.out.println(val);
					}
					// String val = (String) m.invoke(EmployeeDetailBean.class);
				} else if (field.getGenericType().toString().equals("class java.lang.Integer")) {
					// Integer val = (Integer) m.invoke(EmployeeDetailBean.class);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Object getValue(Object obj, String fieldName) {
		Class<?> clazz = obj.getClass();
		Object o = null;
		try {
			PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);
			Method m = pd.getReadMethod();
			o = m.invoke(obj);
			System.out.println(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}
}
