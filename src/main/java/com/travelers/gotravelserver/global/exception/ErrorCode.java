package com.travelers.gotravelserver.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	// 공통
	INVALID_INPUT_VALUE(400, "잘못된 입력 값입니다."),
	INVALID_REQUEST(400, "잘못된 요청입니다."),
	INTERNAL_ERROR(500, "서버 내부 오류가 발생했습니다."),

	// 사용자 관련
	DUPLICATED_EMAIL(400, "이미 사용 중인 이메일입니다."),
	USER_NOT_FOUND(404, "존재하지 않는 회원입니다."),
	NO_AUTHORITY(403, "권한이 없습니다."),
	INVALID_PASSWORD(400, "비밀번호가 틀렸습니다."),

	// 항공 관련
	FLIGHT_NOT_FOUND(404, "해당 조건에 맞는 항공편이 없습니다."),
	INVALID_DEPARTURE_DATE(400, "출발일은 반드시 선택해야 합니다."),

	// 지역 관련
	LOCATION_NOT_FOUND(404, "존재하지 않는 여행지입니다."),

	// 패키지 관련
	PRODUCT_NOT_FOUND(404, "존재하지 않는 패키지입니다."),
	PRODUCT_NOT_AVAILABLE(400, "예약이 불가한 패키지입니다."),

	// 예약 관련
	INSUFFICIENT_REMAINING_SEATS(400, "잔여 인원이 부족합니다."),
	RESERVATION_NOT_FOUND(404, "존재하지 않는 예약 정보입니다."),
	RESERVATION_FAILED(400, "예약에 실패했습니다."),
	RESERVATION_ALREADY_CANCELLED(400, "이미 취소된 예약입니다.");

	public final int status;
	public final String message;
}
