package com.neusoft.acss.column.detail;

import com.neusoft.acss.bean.EmployeeBean;
import com.neusoft.acss.bean.Info;
import com.neusoft.acss.column.detail.impl.ColumnDetailImpl;

public class DepartmentColumn implements ColumnDetailImpl {

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
		EmployeeBean eb = info.getEmployeeBean();
		return eb == null ? null : eb.getDepartment();
	}

}
