package com.neusoft.acss.column.total;

import com.neusoft.acss.bean.Info;
import com.neusoft.acss.column.total.impl.ColumnTotalImpl;

public class NameColumn implements ColumnTotalImpl {

	private String name = "姓名";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return getName();
	}

	@Override
	public String generateColumn(Info info) {
		return info.getSubList().get(0).get(com.neusoft.acss.column.detail.NameColumn.class.getName());
	}

}
