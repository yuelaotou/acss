package com.kd.acss.column.detail.impl;

import org.apache.commons.lang3.StringUtils;

import com.kd.acss.bean.EvectionBean;
import com.kd.acss.bean.Info;
import com.kd.acss.column.detail.IColumnDetail;
import com.kd.acss.enums.Leave;

public class D_I_Leave implements IColumnDetail {

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
			if (StringUtils.isNotEmpty(evb.getLeave_sick())) {
				return Leave.SICK.toString();
			} else if (StringUtils.isNotEmpty(evb.getLeave_thing())) {
				return Leave.THING.toString();
			} else if (StringUtils.isNotEmpty(evb.getLeave_year())) {
				return Leave.YEAR.toString();
			}
		}
		return null;
	}
}
