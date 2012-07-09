package com.neusoft.acss.enums;

public enum Week {
	SUN("日"), MON("一"), TUE("二"), WED("三"), THU("四"), FRI("五"), SAT("六");

	private final String week;

	private Week(String week) {
		this.week = week;
	}

	@Override
	public String toString() {
		return this.week;
	}

}
