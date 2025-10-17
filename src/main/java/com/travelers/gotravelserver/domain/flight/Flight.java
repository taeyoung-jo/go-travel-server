package com.travelers.gotravelserver.domain.flight;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {

	private Long id;
	private Long locationId;
	private String flightNumber; // 항공편명
	private String airline; // 항공사
	private LocalDateTime deptTime; // 출발시간
	private LocalDateTime arrivalTime; // 도착시간
	private Double price; // 가격
}
