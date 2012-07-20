package com.neusoft.acss.column.detail;

import com.neusoft.acss.bean.EvectionBean;
import com.neusoft.acss.bean.Info;
import com.neusoft.acss.column.detail.impl.ColumnDetailImpl;

public class EvectionRemoteColumn implements ColumnDetailImpl {

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
