package com.neusoft.acss.column.total;

import java.util.List;
import java.util.Map;

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
	public String generateColumn(List<Map<String, String>> lm) {
		Map<String, String> m = lm.get(0);
		return m.get("com.neusoft.acss.column.detail.NameColumn");
	}

}
