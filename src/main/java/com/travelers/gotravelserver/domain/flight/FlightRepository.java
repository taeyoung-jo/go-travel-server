package com.travelers.gotravelserver.domain.flight;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travelers.gotravelserver.domain.location.Location;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

	List<Flight> findByFlightNumber(String flightNumber);

	List<Flight> findByAirline(String airline);

	List<Flight> findByLocation(Location location);
}
