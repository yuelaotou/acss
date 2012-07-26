package com.kd.acss.column.detail.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.kd.acss.bean.EvectionBean;
import com.kd.acss.bean.Info;
import com.kd.acss.bean.RecordBean;
import com.kd.acss.column.detail.IColumnDetail;
import com.kd.acss.util.PropUtil;

public class D_I_Exception implements IColumnDetail {

	private String name = "异常情况";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private final int order = 23;

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

			// 有上午并且有下午的，肯定也算做是全天的情况，因此今天肯定不算异常情况。即使无打卡记录
			if (evb.hasType("上午") || evb.startWith(m.get("work.morning.time"))
					|| StringUtils.isNotEmpty(rb.getTmorning())) {

				// 看看是否有人出差或请假到晚上下班。有这样情况的话，今天肯定不算异常情况。即使无打卡记录
				// 如果没有这样的情况或记录，则说明正常来说考勤记录应该有下班打卡记录的。因此这里就不再继续计算下去
				if (evb.hasType("下午") || evb.endWith(m.get("work.evening.time"))
						|| StringUtils.isNotEmpty(rb.getTevening())) {
					return null;
				}
			}

			// 有加班记录
			if (StringUtils.isNotEmpty(evb.getOvertime())) {
				String overtime = StringUtils.substringBefore(evb.getOvertime().replace("-", "~"), "~");
				// 包装成HH:mm:ss格式
				overtime = StringUtils.rightPad(StringUtils.leftPad(overtime, 5, "0"), 8, ":00");
				if (!overtime.equals("00000:00") && overtime.compareTo(m.get("work.evening.time")) >= 0) {
					// 说明晚上有加班，那么如果只有晚上没打卡也不能算是有异常
					if (StringUtils.isNotEmpty(rb.getTmorning()) && StringUtils.isNotEmpty(rb.getTnooningA())
							&& StringUtils.isNotEmpty(rb.getTnooningB())) {
						return null;
					}
				}
			}
		}

		// 说明是休息日，休息日不管什么情况都不应该出异常。本来就算加班嘛;
		if (StringUtils.isNotEmpty(rb.getRest())) {
			return null;
		}

		if (StringUtils.isEmpty(rb.getTmorning()) || StringUtils.isEmpty(rb.getTnooningA())
				|| StringUtils.isEmpty(rb.getTnooningB()) || StringUtils.isEmpty(rb.getTevening())) {

			return "有异常";
		}
		return null;
	}

}
