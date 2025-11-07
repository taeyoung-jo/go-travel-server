package com.travelers.gotravelserver.domain.reservation.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationCreateRequest {

	@NotNull
	private Long productId;

	@Min(1)
	private int participants;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate deptDate;
}
