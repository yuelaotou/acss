package com.kd.acss.column.total.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.kd.acss.bean.Info;
import com.kd.acss.column.detail.impl.D_B_Date;
import com.kd.acss.column.detail.impl.D_B_Name;
import com.kd.acss.column.total.IColumnTotal;
import com.kd.acss.consts.Consts;

public class T_I_IsEvection implements IColumnTotal {

	private String name = "是否有登记表";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private final int order = 22;

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

		List<Map<String, String>> list = info.getSubList();
		Map<String, String> m = list.get(0);
		String name = m.get(D_B_Name.class.getName());
		String date = m.get(D_B_Date.class.getName());
		date = date.replace("-", "");
		date = date.substring(0, 6);
		File file = new File(Consts.FOLDER_EVECTIONS + File.separator + name + "-" + date + ".xlsx");
		if (file.exists()) {
			return null;
		} else {
			return "无";
		}
	}

}
