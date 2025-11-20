package com.travelers.gotravelserver.domain.flight;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travelers.gotravelserver.domain.flight.domain.Flight;
import com.travelers.gotravelserver.domain.location.Location;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

	// 특정 location + 출발일의 모든 항공편 중 가장 저렴한 것 1개
	Optional<Flight> findTopByLocationAndDeptDateOrderByPriceAsc(Location location, LocalDate departureDate);

	// 한달치 항공편
	List<Flight> findByDeptDateBetween(LocalDate start, LocalDate end);
}
