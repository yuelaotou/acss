package com.kd.acss.column.detail.impl;

import com.kd.acss.bean.Info;
import com.kd.acss.bean.RecordBean;
import com.kd.acss.column.detail.IColumnDetail;

public class D_T_Evening implements IColumnDetail {

	private String name = "下班时间";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private final int order = 11;

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
		RecordBean rb = info.getRecordBean();
		return rb.getTevening();
	}

}
