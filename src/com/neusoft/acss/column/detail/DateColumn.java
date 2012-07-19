package com.neusoft.acss.column.detail;

import com.neusoft.acss.bean.Info;
import com.neusoft.acss.bean.RecordBean;
import com.neusoft.acss.column.detail.impl.ColumnDetailImpl;

public class DateColumn implements ColumnDetailImpl {

	private String name = "打卡日期";

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
		// EmployeeBean eb = info.getEmployeeBean();
		RecordBean rb = info.getRecordBean();
		// EvectionBean evb = info.getEvectionBean();
		return rb.getDate();
		// return e.getString("company");
	}

}
