package com.neusoft.acss.column.detail;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.acss.bean.EvectionBean;
import com.neusoft.acss.bean.Info;
import com.neusoft.acss.bean.RecordBean;
import com.neusoft.acss.column.detail.impl.ColumnDetailImpl;
import com.neusoft.acss.enums.WorkSt;

public class WorkStColumn implements ColumnDetailImpl {

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
			if (!StringUtils.isEmpty(evb.getLeave_sick()) || !StringUtils.isEmpty(evb.getLeave_thing())
					|| !StringUtils.isEmpty(evb.getLeave_year())) {
				// 有请假记录
				return WorkSt.LEAVE.toString();
			}
			if (!StringUtils.isEmpty(evb.getEvection_locale()) || !StringUtils.isEmpty(evb.getEvection_remote())) {
				// 有出差记录
				return WorkSt.EVECTION.toString();
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
