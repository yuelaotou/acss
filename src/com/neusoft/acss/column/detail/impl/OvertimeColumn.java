package com.neusoft.acss.column.detail.impl;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.acss.bean.EvectionBean;
import com.neusoft.acss.bean.Info;
import com.neusoft.acss.bean.RecordBean;
import com.neusoft.acss.column.detail.IColumnDetail;
import com.neusoft.acss.enums.Overtime;

public class OvertimeColumn implements IColumnDetail {

	private String name = "加班类型";

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
		RecordBean rb = info.getRecordBean();
		EvectionBean evb = info.getEvectionBean();
		if (evb != null) {
			if (StringUtils.isNotEmpty(evb.getOvertime())) {
				if (StringUtils.isNotEmpty(evb.getEvection_remote())) {
					return Overtime.REMOTE.toString();
				}
				if (StringUtils.isNotEmpty(rb.getRest())) {
					// 这里目前没判断是周末还是法定假日，以后再扩展
					return Overtime.WEEKEND.toString();
				} else {
					return Overtime.WORKDAY.toString();
				}
			}
		}
		return null;
	}

}
