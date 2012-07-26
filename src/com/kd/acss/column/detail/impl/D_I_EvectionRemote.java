package com.kd.acss.column.detail.impl;

import com.kd.acss.bean.EvectionBean;
import com.kd.acss.bean.Info;
import com.kd.acss.column.detail.IColumnDetail;

public class D_I_EvectionRemote implements IColumnDetail {

	private String name = "异地出差地点";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private final int order = 16;

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
		return evb == null ? null : evb.getEvection_remote();
	}

}