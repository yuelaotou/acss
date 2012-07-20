package com.neusoft.acss.column.detail;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.acss.bean.Info;
import com.neusoft.acss.bean.RecordBean;
import com.neusoft.acss.column.detail.impl.ColumnDetailImpl;

public class ExceptionColumn implements ColumnDetailImpl {

	private String name = "异常情况";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private final int order = 20;

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
		if (StringUtils.isEmpty(rb.getTmorning()) || StringUtils.isEmpty(rb.getTnooningA())
				|| StringUtils.isEmpty(rb.getTnooningB()) || StringUtils.isEmpty(rb.getTevening())) {
			return "有异常";
		}
		return null;
	}

}
