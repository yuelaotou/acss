package com.neusoft.acss.column.total.impl;

import java.util.List;
import java.util.Map;

public interface ColumnTotalImpl {

	public String getDescription();

	public String generateColumn(List<Map<String, String>> lm);
}
