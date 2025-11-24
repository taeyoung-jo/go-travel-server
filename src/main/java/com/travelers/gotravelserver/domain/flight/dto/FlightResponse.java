package com.travelers.gotravelserver.domain.flight.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.travelers.gotravelserver.domain.flight.domain.Flight;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FlightResponse {

	private Long id;
	private Long locationId;
	private String flightNumber;
	private String airline;
	private LocalDate deptDate;
	private LocalDateTime deptTime;
	private LocalDateTime arrivalTime;
	private BigDecimal price;

	public static FlightResponse from(Flight flight) {
		return FlightResponse.builder()
			.id(flight.getId())
			.locationId(flight.getLocation().getId())
			.flightNumber(flight.getFlightNumber())
			.airline(flight.getAirline())
			.deptDate(flight.getDeptDate())
			.deptTime(flight.getDeptTime())
			.arrivalTime(flight.getArrivalTime())
			.price(flight.getPrice())
			.build();
	}
}
