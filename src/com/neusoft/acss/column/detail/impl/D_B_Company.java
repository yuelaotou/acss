package com.neusoft.acss.column.detail.impl;

import com.neusoft.acss.bean.EmployeeBean;
import com.neusoft.acss.bean.Info;
import com.neusoft.acss.column.detail.IColumnDetail;

public class D_B_Company implements IColumnDetail {

	private String name = "公司";

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
		EmployeeBean eb = info.getEmployeeBean();
		return eb == null ? null : eb.getCompany();
	}

}
