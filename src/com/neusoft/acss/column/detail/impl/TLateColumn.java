package com.neusoft.acss.column.detail.impl;

import java.util.Calendar;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.acss.bean.EvectionBean;
import com.neusoft.acss.bean.Info;
import com.neusoft.acss.bean.RecordBean;
import com.neusoft.acss.column.detail.IColumnDetail;
import com.neusoft.acss.util.DateUtil;
import com.neusoft.acss.util.PropUtil;

public class TLateColumn implements IColumnDetail {

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

		// 根据宽限时间重新计算上班下班等时间和中午休息打卡时间。
		Map<String, String> m = PropUtil.readProperties();
		EvectionBean evb = info.getEvectionBean();
		RecordBean rb = info.getRecordBean();

		// 如果出差登记表有今天对应的记录，那么说明今天有请假或者出差记录。所以是否有异常情况要根据出差登记表重新计算。
		if (evb != null) {

			// 出差时间、病假、事假、年假，可能有几种，1.全天 2.上午 3.下午 4.具体时间如：8:30~12:30 或 8:30-12:30

			// 有全天的情况，今天肯定不算异常情况。即使无打卡记录
			if (evb.hasType("全天")) {
				return null;
			}

			// 有上午的情况，说明上午是有出差或者请假记录，或者有人在早上上班时就出差或请假了。因此即使上午无打卡记录，也不能计算迟到。
			// 如果没有这样的情况或记录，则说明正常来说考勤记录应该有早上打卡记录的。因此这里就不再继续计算下去
			if (evb.hasType("上午") || evb.startWith(m.get("work.morning.time"))) {
				return null;
			}
		}

		// 说明是休息日，休息日即使迟到也不计算迟到，本来就是加班啊;
		if (StringUtils.isNotEmpty(rb.getRest())) {
			return null;
		}

		String tmorning = rb.getTmorning();
		// 如果有异常，则不计算迟到时间
		if (StringUtils.isEmpty(tmorning)) {
			return null;
		}
		String morning_grace = DateUtil.addMinutes(m.get("work.morning.time"), m.get("work.grace.time"));

		int min = DateUtil.minusDate(morning_grace, tmorning, Calendar.MINUTE);
		return min > 0 ? min + "" : null;

	}
}
