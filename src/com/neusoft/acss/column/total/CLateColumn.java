package com.neusoft.acss.column.total;

import java.util.List;
import java.util.Map;

import com.neusoft.acss.column.total.impl.ColumnTotalImpl;

public class CLateColumn implements ColumnTotalImpl {

	private String name = "迟到次数";

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

		return "12";
	}

}
