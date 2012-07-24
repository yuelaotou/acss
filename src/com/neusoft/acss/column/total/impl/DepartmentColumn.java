package com.neusoft.acss.column.total.impl;

import java.util.List;
import java.util.Map;

import com.neusoft.acss.bean.Info;
import com.neusoft.acss.column.total.IColumnTotal;

public class DepartmentColumn implements IColumnTotal {

	private String name = "部门";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private final int order = 1;

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
		List<Map<String, String>> list = info.getSubList();
		Map<String, String> m = list.get(0);
		return m.get(com.neusoft.acss.column.detail.impl.DepartmentColumn.class.getName());
	}

}
