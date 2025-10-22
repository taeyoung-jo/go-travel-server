package com.travelers.gotravelserver.global.exception;

import lombok.Getter;

public enum ErrorCode {

	// 공통
	INVALID_INPUT_VALUE(400, "잘못된 입력 값입니다."),

	// 사용자 관련
	DUPLICATED_EMAIL(400, "이미 사용 중인 이메일입니다."),
	USER_NOT_FOUND(404, "존재하지 않는 회원입니다."),
	NO_AUTHORITY(403, "권한이 없습니다."),
	INVALID_PASSWORD(400, "비밀번호가 틀렸습니다."),

	// 항공 관련
	FLIGHT_NOT_FOUND(404, "존재하지 않는 항공편입니다."),

	// 지역 관련
	LOCATION_NOT_FOUND(404, "존재하지 않는 여행지입니다.");

	public final int status;
	@Getter
	public final String message;

	ErrorCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}
