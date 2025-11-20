package com.travelers.gotravelserver.domain.flight;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travelers.gotravelserver.domain.flight.domain.Flight;
import com.travelers.gotravelserver.domain.flight.dto.FlightResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flights")
public class FlightController {

	private final FlightService flightService;

	// 항공 단건 조회
	@GetMapping("/{id}")
	public ResponseEntity<FlightResponse> getFlightById(@PathVariable Long id) {
		Flight flight = flightService.getFlightById(id);
		return ResponseEntity.ok(FlightResponse.from(flight));
	}

	// 월별 조회
	@GetMapping
	public List<FlightResponse> getFlightsByMonth(
		@RequestParam int year,
		@RequestParam int month
	) {
		return flightService.getFlightsByMonth(year, month);
	}
}
