package com.kd.acss.column.total.impl;

import java.util.List;
import java.util.Map;

import com.kd.acss.bean.Info;
import com.kd.acss.column.detail.impl.D_B_Id;
import com.kd.acss.column.total.IColumnTotal;

public class T_B_Id implements IColumnTotal {

	private String name = "登记号";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private final int order = 4;

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
		return m.get(D_B_Id.class.getName());
	}

}
