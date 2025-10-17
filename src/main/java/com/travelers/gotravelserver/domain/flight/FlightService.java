package com.travelers.gotravelserver.domain.flight;

import java.util.List;

import org.springframework.stereotype.Service;

import com.travelers.gotravelserver.global.exception.CustomException;
import com.travelers.gotravelserver.global.exception.ErrorCode;

@Service
public class FlightService {

	private final FlightRepository flightRepository;

	public FlightService(FlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}

	public Flight getFlightById(Long id) {
		return flightRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.FLIGHT_NOT_FOUND));
	}

	public List<Flight> getAllFlights() {
		return flightRepository.findAll();
	}
}
