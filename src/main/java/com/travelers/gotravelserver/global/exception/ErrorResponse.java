package com.travelers.gotravelserver.global.exception;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
	private int status;
	private String error;
	private String message;
	private LocalDateTime timestamp;
}
