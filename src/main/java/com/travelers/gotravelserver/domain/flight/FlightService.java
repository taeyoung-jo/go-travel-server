package com.travelers.gotravelserver.domain.flight;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travelers.gotravelserver.domain.flight.domain.Flight;
import com.travelers.gotravelserver.domain.flight.dto.FlightResponse;
import com.travelers.gotravelserver.domain.location.Location;
import com.travelers.gotravelserver.global.exception.CustomException;
import com.travelers.gotravelserver.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FlightService {

	private final FlightRepository flightRepository;

	// ID로 조회
	public Flight getFlightById(Long id) {
		return flightRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.FLIGHT_NOT_FOUND));
	}

	// 도착지(location) + 출발일로 조회
	public List<FlightResponse> getFlightsByMonth(int year, int month) {
		LocalDate start = LocalDate.of(year, month, 1);
		LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
		return flightRepository.findByDeptDateBetween(start, end)
			.stream()
			.map(FlightResponse::from)
			.toList();
	}

	// 도착지(location) + 출발일 기준 최저가 항공편 1개 조회
	public Flight getCheapestFlight(Location location, LocalDate departureDate) {
		if (departureDate == null)
			throw new CustomException(ErrorCode.INVALID_DEPARTURE_DATE);
		return flightRepository.findTopByLocationAndDeptDateOrderByPriceAsc(location, departureDate)
			.orElseThrow(() -> new CustomException(ErrorCode.FLIGHT_NOT_FOUND));
	}

	// 전체 조회
	public List<Flight> getAllFlights() {
		return flightRepository.findAll();
	}
}
