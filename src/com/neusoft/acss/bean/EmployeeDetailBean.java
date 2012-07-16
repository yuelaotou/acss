package com.neusoft.acss.bean;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

import com.neusoft.acss.bs.EmployeeDetailBS;

/**
 * <p> Title: [员工打卡详细信息实体]</p>
 * <p> Description: [员工每月详细打卡信息实体，存储Excel每行记录]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public class EmployeeDetailBean {

	private Object object = null;

	private BeanMap beanMap = null;

	public EmployeeDetailBean() {
		super();
	}

	public EmployeeDetailBean(Map<String, Object[]> propertyMap) {
		this.object = generateBean(propertyMap);
		this.beanMap = BeanMap.create(this.object);
	}

	/** 
	  * 给EmployeeDetailBean属性赋值 
	  * @param property 属性名 
	  * @param value 值 
	  */
	public void setValue(String property, Object value) {
		beanMap.put(property, value);
	}

	/** 
	  * 通过属性名得到属性值 
	  * @param property 属性名 
	  * @return 值 
	  */
	public Object getValue(String property) {
		return beanMap.get(property);
	}

	/** 
	  * 得到该实体bean对象 
	  * @return 
	  */
	public Object getObject() {
		return this.object;
	}

	/** 
	 * @param propertyMap 
	 * @return 
	 */
	private Object generateBean(Map<String, Object[]> m) {
		BeanGenerator generator = new BeanGenerator();
		Iterator<Entry<String, Object[]>> it = m.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object[]> entry = it.next();
			Object key = entry.getKey();
			Object[] values = entry.getValue();
			generator.addProperty(key.toString(), (Class<?>) values[0]);
		}
		return generator.create();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			Map<String, Object[]> m = EmployeeDetailBS.getPropertyMap();
			Iterator<Entry<String, Object[]>> it = m.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Object[]> entry = it.next();
				Object key = entry.getKey();
				sb.append("[" + key + " = " + getValue(key.toString()) + "], ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.substring(0, sb.length()-2);
	}

}
