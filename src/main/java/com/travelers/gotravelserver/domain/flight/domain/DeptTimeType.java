package com.travelers.gotravelserver.domain.flight.domain;

import lombok.Getter;

@Getter
public enum DeptTimeType {
	AM("00:00:00", "11:59:59"),
	PM("12:00:00", "23:59:59");

	private final String startTime;
	private final String endTime;

	DeptTimeType(String startTime, String endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}
}
