package com.kd.acss.column.total.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.kd.acss.bean.Info;
import com.kd.acss.column.total.IColumnTotal;

public class T_C_Early implements IColumnTotal {

	private String name = "早退次数";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private final int order = 7;

	@Override
	public int getOrder() {
		return order;
	}

	@Override
	public String getDescription() {
		return getName();
	}

	@Override
	public String generateColumn(Info info) {
		int count = 0;
		List<Map<String, String>> list = info.getSubList();
		for (Map<String, String> m : list) {
			if (StringUtils.isNotEmpty(m.get(com.kd.acss.column.detail.impl.D_T_Early.class.getName()))) {
				count++;
			}
		}
		return count + "";
	}

}
