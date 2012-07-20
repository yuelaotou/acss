package com.neusoft.acss.column.total.impl;

import com.neusoft.acss.bean.Info;
import com.neusoft.acss.column.total.IColumnTotal;

public class NameColumn implements IColumnTotal {

	private String name = "姓名";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	private final int order = 0;

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
		return info.getSubList().get(0).get(com.neusoft.acss.column.detail.impl.NameColumn.class.getName());
	}

}
