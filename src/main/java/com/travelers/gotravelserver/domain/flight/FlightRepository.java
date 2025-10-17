package com.travelers.gotravelserver.domain.flight;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

	Optional<Flight> findFlightById(Long id);

	List<Flight> findAllBy();
}
