package com.neusoft.acss.column.detail;

import com.neusoft.acss.bean.EvectionBean;
import com.neusoft.acss.bean.Info;
import com.neusoft.acss.column.detail.impl.ColumnDetailImpl;
import com.neusoft.acss.enums.Leave;

public class LeaveColumn implements ColumnDetailImpl {

	private String name = "请假类型";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private final int order = 19;

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
		EvectionBean evb = info.getEvectionBean();
		if (evb != null) {
			if (evb.getLeave_sick() != null) {
				return Leave.SICK.toString();
			} else if (evb.getLeave_thing() != null) {
				return Leave.THING.toString();
			} else if (evb.getLeave_year() != null) {
				return Leave.YEAR.toString();
			}
		}
		return null;
	}
}
