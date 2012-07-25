package com.neusoft.acss.column.total.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.acss.bean.Info;
import com.neusoft.acss.column.detail.impl.D_I_Overtime;
import com.neusoft.acss.column.detail.impl.D_T_Overtime;
import com.neusoft.acss.column.total.IColumnTotal;
import com.neusoft.acss.enums.Overtime;

public class T_T_OvertimeRemote implements IColumnTotal {

	private String name = "外地加班时间（时）";

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
		BigDecimal t = new BigDecimal(0);
		List<Map<String, String>> list = info.getSubList();
		for (Map<String, String> m : list) {
			String time = m.get(D_I_Overtime.class.getName());
			if (StringUtils.isNotEmpty(time)) {
				if (time.equals(Overtime.REMOTE.toString())) {
					String time1 = m.get(D_T_Overtime.class.getName());
					if (StringUtils.isNotEmpty(time1)) {
						t = t.add(new BigDecimal(time1));
					}
				}
			}
		}
		return t.toString();
	}

}
