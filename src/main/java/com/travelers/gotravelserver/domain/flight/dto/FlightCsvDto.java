package com.travelers.gotravelserver.domain.flight.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FlightCsvDto {

	private String flightNumber;
	private String airline;
	private LocalDate deptDate;
	private LocalDateTime deptTime;
	private LocalDateTime arrivalTime;
	private BigDecimal price;
	private String destination;
}
