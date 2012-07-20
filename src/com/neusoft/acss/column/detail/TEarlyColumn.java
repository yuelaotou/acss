package com.neusoft.acss.column.detail;

import java.util.Calendar;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.acss.bean.Info;
import com.neusoft.acss.bean.RecordBean;
import com.neusoft.acss.column.detail.impl.ColumnDetailImpl;
import com.neusoft.acss.util.DateUtil;
import com.neusoft.acss.util.PropUtil;

public class TEarlyColumn implements ColumnDetailImpl {

	private String name = "早退时间";

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

		RecordBean rb = info.getRecordBean();
		// 如果有异常，则不计算早退时间
		if (StringUtils.isEmpty(rb.getTmorning()) || StringUtils.isEmpty(rb.getTnooningA())
				|| StringUtils.isEmpty(rb.getTnooningB()) || StringUtils.isEmpty(rb.getTevening())) {
			return null;
		}
		// 根据宽限时间重新计算上班下班等时间和中午休息打卡时间。
		// 根据宽限时间重新计算上班下班等时间和中午休息打卡时间。
		Map<String, String> m = PropUtil.readProperties();
		String evening_grace = DateUtil.minusMinutes(m.get("work.evening.time"), m.get("work.grace.time"));

		String tevening = rb.getTevening();
		int min = DateUtil.minusDate(tevening, evening_grace, Calendar.MINUTE);
		return min > 0 ? min + "" : null;
	}

}
