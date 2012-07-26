package com.kd.acss.enums;

/**
 * <p> Title: [工作情况]</p>
 * <p> Description: [描述工作情况的Enum类，有需要扩展请来这里]</p>
 * <p> Description: [WORK("上班"), REST("休息"), LEAVE("请假"), EVECTION("出差"), OVERTIME("加班")]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * @version 1.0
 */
public enum WorkSt {
	WORK("上班"), REST("休息"), LEAVE("请假"), EVECTION("出差"), OVERTIME("加班");

	private final String status;

	private WorkSt(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return this.status;
	}

}
