package com.neusoft.acss.column.detail.impl;

import com.neusoft.acss.bean.EvectionBean;
import com.neusoft.acss.bean.Info;
import com.neusoft.acss.column.detail.IColumnDetail;

public class D_I_EvectionLocale implements IColumnDetail {

	private String name = "本地出差地点";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private final int order = 15;

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
		return evb == null ? null : evb.getEvection_locale();
	}

}
