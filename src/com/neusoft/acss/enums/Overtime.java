package com.neusoft.acss.enums;

/**
 * <p> Title: [加班类型]</p>
 * <p> Description: [描述加班类型的Enum类]</p>
 * <p> Description: [WORKDAY("工作日加班"), WEEKEND("周末加班"), HOLIDAY("法定假日加班")]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public enum Overtime {
	WORKDAY("工作日加班"), WEEKEND("周末加班"), HOLIDAY("法定假日加班");

	private final String overtime;

	private Overtime(String overtime) {
		this.overtime = overtime;
	}

	@Override
	public String toString() {
		return this.overtime;
	}

}
