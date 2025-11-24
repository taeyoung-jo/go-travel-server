package com.travelers.gotravelserver.domain.reservation.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.travelers.gotravelserver.domain.reservation.domain.Reservation;
import com.travelers.gotravelserver.domain.reservation.domain.ReservationStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationDetailResponse {

	private Long id;
	private String productName;
	private LocalDateTime orderAt;
	private LocalDate deptDate;
	private int participants;
	private BigDecimal price;
	private ReservationStatus status;

	private String userName;
	private String airline;
	private String period;

	public static ReservationDetailResponse from(Reservation reservation) {
		StringBuilder sb = new StringBuilder();
		int days = reservation.getProduct().getDays();
		sb.append(days).append("박 ").append(days + 1).append("일");

		return ReservationDetailResponse.builder()
			.id(reservation.getId())
			.productName(reservation.getProduct().getName())
			.orderAt(reservation.getCreatedAt())
			.deptDate(reservation.getFlight().getDeptDate())
			.participants(reservation.getParticipants())
			.price(reservation.getPrice())
			.status(reservation.getStatus())
			.userName(reservation.getUser().getName())
			.airline(reservation.getFlight().getAirline())
			.period(sb.toString())
			.build();
	}
}
