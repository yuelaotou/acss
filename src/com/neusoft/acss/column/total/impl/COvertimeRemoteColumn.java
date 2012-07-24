package com.neusoft.acss.column.total.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.acss.bean.Info;
import com.neusoft.acss.column.detail.impl.OvertimeColumn;
import com.neusoft.acss.column.total.IColumnTotal;
import com.neusoft.acss.enums.Overtime;

public class COvertimeRemoteColumn implements IColumnTotal {

	private String name = "外地加班天数";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private final int order = 14;

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
		int count = 0;
		List<Map<String, String>> list = info.getSubList();
		for (Map<String, String> m : list) {
			if (StringUtils.isNotEmpty(m.get(OvertimeColumn.class.getName()))) {
				if (m.get(OvertimeColumn.class.getName()).equals(Overtime.REMOTE.toString())) {
					count++;
				}
			}
		}
		return count + "";
	}

}
