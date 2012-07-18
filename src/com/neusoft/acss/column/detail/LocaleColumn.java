package com.neusoft.acss.column.detail;

import com.neusoft.acss.bean.EmployeeBean;
import com.neusoft.acss.bean.EvectionBean;
import com.neusoft.acss.bean.RecordBean;
import com.neusoft.acss.column.detail.impl.ColumnDetailImpl;

public class LocaleColumn implements ColumnDetailImpl {

	private String name = "归属地";

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
	public String generateColumn(EmployeeBean eb, RecordBean rb, EvectionBean evb) {
		return eb.getLocale();
	}

}
