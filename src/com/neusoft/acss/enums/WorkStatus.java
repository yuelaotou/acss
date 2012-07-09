package com.neusoft.acss.enums;

public enum WorkStatus {
	ZHENGCHANG("正常"), CHIDAO("迟到"), ZAOTUI("早退"), JIABAN("加班"), QINGJIA("请假"), CHUCHAI("出差"), XIUXI("休息");

	private final String status;

	private WorkStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return this.status;
	}

}
