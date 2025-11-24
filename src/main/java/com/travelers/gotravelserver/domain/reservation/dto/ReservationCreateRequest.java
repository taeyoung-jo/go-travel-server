package com.travelers.gotravelserver.domain.reservation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationCreateRequest {

	@NotNull
	private Long productId;

	@NotNull
	private Long flightId;

	@Min(1)
	private int participants;
}
