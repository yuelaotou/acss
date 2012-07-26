package com.kd.acss.column.total.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.kd.acss.bean.Info;
import com.kd.acss.column.detail.impl.D_I_Overtime;
import com.kd.acss.column.total.IColumnTotal;
import com.kd.acss.enums.Overtime;

public class T_C_OvertimeLocale implements IColumnTotal {

	private String name = "本地加班次数";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private final int order = 17;

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
			String time = m.get(D_I_Overtime.class.getName());
			if (StringUtils.isNotEmpty(time)) {
				if (!time.equals(Overtime.REMOTE.toString())) {
					count++;
				}
			}
		}
		return count + "";
	}

}
