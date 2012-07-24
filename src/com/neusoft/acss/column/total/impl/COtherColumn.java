package com.neusoft.acss.column.total.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.acss.bean.Info;
import com.neusoft.acss.column.detail.impl.LeaveColumn;
import com.neusoft.acss.column.total.IColumnTotal;
import com.neusoft.acss.enums.Leave;

public class COtherColumn implements IColumnTotal {

	private String name = "其他假期次数";

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
		int count = 0;
		List<Map<String, String>> list = info.getSubList();
		for (Map<String, String> m : list) {
			if (StringUtils.isNotEmpty(m.get(LeaveColumn.class.getName()))) {
				if (m.get(LeaveColumn.class.getName()).equals(Leave.OTHER.toString())) {
					count++;
				}
			}
		}
		return count + "";
	}

}
