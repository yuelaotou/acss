package com.neusoft.acss.column.detail;

import java.util.Calendar;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.acss.bean.Info;
import com.neusoft.acss.bean.RecordBean;
import com.neusoft.acss.column.detail.impl.ColumnDetailImpl;
import com.neusoft.acss.util.DateUtil;
import com.neusoft.acss.util.PropUtil;

public class TLateColumn implements ColumnDetailImpl {

	private String name = "迟到时间";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private final int order = 13;

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
		// 如果有异常，则不计算迟到时间
		if (StringUtils.isEmpty(rb.getTmorning()) || StringUtils.isEmpty(rb.getTnooningA())
				|| StringUtils.isEmpty(rb.getTnooningB()) || StringUtils.isEmpty(rb.getTevening())) {
			return null;
		}
		// 根据宽限时间重新计算上班下班等时间和中午休息打卡时间。
		Map<String, String> m = PropUtil.readProperties();
		String morning_grace = DateUtil.addMinutes(m.get("work.morning.time"), m.get("work.grace.time"));

		String tmorning = rb.getTmorning();
		int min = DateUtil.minusDate(morning_grace, tmorning, Calendar.MINUTE);
		return min > 0 ? min + "" : null;
	}

}
