package com.neusoft.acss.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * <p> Title: [请假类型]</p>
 * <p> Description: [描述请假的Enum类]</p>
 * <p> Description: [THING("事假"), SICK("病假"), YEAR("年假"), OTHER("其他")]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public enum Leave {
	THING("事假"), SICK("病假"), YEAR("年假"), OTHER("其他");

	private final String leave;

	private Leave(String leave) {
		this.leave = leave;
	}

	@Override
	public String toString() {
		return this.leave;
	}

	public boolean isEmpty() {
		return StringUtils.isEmpty(leave);
	}

}
