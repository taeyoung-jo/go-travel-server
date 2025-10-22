package com.travelers.gotravelserver.domain.flight;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	// 항공편명으로 조회
	public List<Flight> getFlightsByFlightNumber(String flightNumber) {
		return flightRepository.findByFlightNumber(flightNumber);
	}

	// 항공사로 조회
	public List<Flight> getFlightsByAirline(String airline) {
		return flightRepository.findByAirline(airline);
	}

	// 도착지(location)로 조회
	public List<Flight> getFlightsByLocation(Location location) {
		return flightRepository.findByLocation(location);
	}

	// 전체 조회
	public List<Flight> getAllFlights() {
		return flightRepository.findAll();
	}
}
