package com.kd.acss.column.detail.impl;

import org.apache.commons.lang3.StringUtils;

import com.kd.acss.bean.EvectionBean;
import com.kd.acss.bean.Info;
import com.kd.acss.bean.RecordBean;
import com.kd.acss.column.detail.IColumnDetail;
import com.kd.acss.enums.WorkSt;

public class D_I_WorkSt implements IColumnDetail {

	private String name = "工作情况";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private final int order = 12;

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
			if (StringUtils.isNotEmpty(evb.getEvection_locale()) || StringUtils.isNotEmpty(evb.getEvection_remote())) {
				// 有出差记录
				return WorkSt.EVECTION.toString();
			}
			if (StringUtils.isNotEmpty(evb.getLeave_sick()) || StringUtils.isNotEmpty(evb.getLeave_thing())
					|| StringUtils.isNotEmpty(evb.getLeave_year())) {
				// 有请假记录
				return WorkSt.LEAVE.toString();
			}
			if (StringUtils.isNotEmpty(evb.getOvertime())) {
				// 有加班记录
				return WorkSt.OVERTIME.toString();
			}
		}
		if (StringUtils.isEmpty(rb.getRest())) {
			// 如果Rest为空，则说明是上班日
			return WorkSt.WORK.toString();
		} else {
			return WorkSt.REST.toString();
		}
	}
}
