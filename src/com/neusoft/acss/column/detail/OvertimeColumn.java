package com.neusoft.acss.column.detail;

import com.neusoft.acss.bean.Info;
import com.neusoft.acss.column.detail.impl.ColumnDetailImpl;
import com.neusoft.acss.enums.Overtime;

public class OvertimeColumn implements ColumnDetailImpl {

	private String name = "加班类型";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private final int order = 17;

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
		//RecordBean rb = info.getRecordBean();
		//		EvectionBean evb = info.getEvectionBean();
		return Overtime.HOLIDAY.toString();
	}

}
