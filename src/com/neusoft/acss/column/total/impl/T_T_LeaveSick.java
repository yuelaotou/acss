package com.neusoft.acss.column.total.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.acss.bean.Info;
import com.neusoft.acss.column.detail.impl.D_T_LeaveSick;
import com.neusoft.acss.column.total.IColumnTotal;

public class T_T_LeaveSick implements IColumnTotal {

	private String name = "病假时间（时）";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private final int order = 10;

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
		BigDecimal t = new BigDecimal(0);
		List<Map<String, String>> list = info.getSubList();
		for (Map<String, String> m : list) {
			String time = m.get(D_T_LeaveSick.class.getName());
			if (StringUtils.isNotEmpty(time)) {
				t = t.add(new BigDecimal(time));
			}
		}
		return t.toString();
	}

}
