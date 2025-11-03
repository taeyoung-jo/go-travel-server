package com.travelers.gotravelserver.domain.flight.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

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
	private Timestamp deptTime;
	private Timestamp arrivalTime;
	private BigDecimal price;
	private String destination;
}
