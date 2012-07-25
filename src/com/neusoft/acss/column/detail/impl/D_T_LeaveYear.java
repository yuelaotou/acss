package com.neusoft.acss.column.detail.impl;

import java.math.BigDecimal;
import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.acss.bean.EvectionBean;
import com.neusoft.acss.bean.Info;
import com.neusoft.acss.column.detail.IColumnDetail;
import com.neusoft.acss.util.DateUtil;

public class D_T_LeaveYear implements IColumnDetail {

	private String name = "年假时间（时）";

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
		EvectionBean evb = info.getEvectionBean();
		if (evb != null) {

			// 有年假信息
			if (StringUtils.isNotEmpty(evb.getLeave_year())) {
				if (evb.getLeave_year().equals("全天")) {
					return "8";
				} else if (evb.getLeave_year().equals("上午")) {
					return "4";
				} else if (evb.getLeave_year().equals("下午")) {
					return "4";
				} else {
					String beforetime = StringUtils.substringBefore(evb.getLeave_year().replace("-", "~"), "~");
					String endtime = StringUtils.substringAfter(evb.getLeave_year().replace("-", "~"), "~");
					beforetime = StringUtils.rightPad(StringUtils.leftPad(beforetime, 5, "0"), 8, ":00");
					endtime = StringUtils.rightPad(StringUtils.leftPad(endtime, 5, "0"), 8, ":00");
					int min = DateUtil.minusDate(beforetime, endtime, Calendar.MINUTE);

					// 返回小时。带两位小数
					return new BigDecimal(min).divide(new BigDecimal(60), 2, BigDecimal.ROUND_HALF_UP).toString();
				}
			}
		}
		return null;
	}
}
