package com.travelers.gotravelserver.global.exception;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
		ErrorCode code = ex.getCode();
		ErrorResponse response = ErrorResponse.builder()
			.status(code.getStatus())
			.error(code.name())
			.message(code.getMessage())
			.timestamp(LocalDateTime.now())
			.build();
		return ResponseEntity.status(code.getStatus()).body(response);
	}

	// 잘못된 입력 값 → 400
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
		ErrorResponse response = ErrorResponse.builder()
			.status(400)
			.error("INVALID_ARGUMENT")
			.message(ex.getMessage())
			.timestamp(LocalDateTime.now())
			.build();
		return ResponseEntity.badRequest().body(response);
	}

	// 요청 경로 없음 → 404
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(NoHandlerFoundException ex) {
		ErrorResponse response = ErrorResponse.builder()
			.status(404)
			.error("NOT_FOUND")
			.message("요청한 URL을 찾을 수 없습니다: " + ex.getRequestURL())
			.timestamp(LocalDateTime.now())
			.build();
		return ResponseEntity.status(404).body(response);
	}

	// 메서드 불일치 → 405
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
		ErrorResponse response = ErrorResponse.builder()
			.status(405)
			.error("METHOD_NOT_ALLOWED")
			.message("허용되지 않은 HTTP 메서드입니다: " + ex.getMethod())
			.timestamp(LocalDateTime.now())
			.build();
		return ResponseEntity.status(405).body(response);
	}

	// 예상치 못한 예외 → 500
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
		ErrorResponse response = ErrorResponse.builder()
			.status(500)
			.error("INTERNAL_ERROR")
			.message(ex.getMessage())
			.timestamp(LocalDateTime.now())
			.build();
		return ResponseEntity.internalServerError().body(response);
	}
}
