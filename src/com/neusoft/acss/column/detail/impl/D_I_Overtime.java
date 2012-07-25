package com.neusoft.acss.column.detail.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.acss.bean.EvectionBean;
import com.neusoft.acss.bean.Holiday;
import com.neusoft.acss.bean.Info;
import com.neusoft.acss.bean.RecordBean;
import com.neusoft.acss.column.detail.IColumnDetail;
import com.neusoft.acss.enums.Overtime;

public class D_I_Overtime implements IColumnDetail {

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
					List<Holiday> list = info.getHolidayList();
					if (list != null && list.size() != 0) {
						for (Holiday h : list) {
							if (h.getDate().equals(rb.getDate())) {
								return Overtime.HOLIDAY.toString();
							}
						}
					}
					return Overtime.WEEKEND.toString();
				} else {
					return Overtime.WORKDAY.toString();
				}
			}
		}
		return null;
	}

}
