package com.neusoft.acss.column.detail;

import com.neusoft.acss.bean.Info;
import com.neusoft.acss.column.detail.impl.ColumnDetailImpl;

public class ExceptionColumn implements ColumnDetailImpl {

	private String name = "异常情况";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private final int order = 20;

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
		// EmployeeBean eb = info.getEmployeeBean();
		// RecordBean rb = info.getRecordBean();
		// EvectionBean evb = info.getEvectionBean();
		return "部门";
		// return e.getString("company");
	}

}
