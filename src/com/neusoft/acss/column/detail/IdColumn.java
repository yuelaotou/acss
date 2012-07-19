package com.neusoft.acss.column.detail;

import com.neusoft.acss.bean.Info;
import com.neusoft.acss.column.detail.impl.ColumnDetailImpl;

public class IdColumn implements ColumnDetailImpl {

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
		// EmployeeBean eb = info.getEmployeeBean();
		// RecordBean rb = info.getRecordBean();
		// EvectionBean evb = info.getEvectionBean();
		return "部门";
		// return e.getString("company");
	}

}
