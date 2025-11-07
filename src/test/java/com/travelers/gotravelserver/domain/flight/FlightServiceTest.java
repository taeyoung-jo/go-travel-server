package com.travelers.gotravelserver.domain.flight;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.travelers.gotravelserver.domain.flight.domain.Flight;
import com.travelers.gotravelserver.domain.location.Location;
import com.travelers.gotravelserver.domain.location.LocationRepository;

@SpringBootTest
@Transactional
class FlightServiceTest {

	@Autowired
	private FlightService flightService;
	@Autowired
	private FlightRepository flightRepository;
	@Autowired
	private LocationRepository locationRepository;

	@BeforeEach
	void setUp() {
		List<Location> locations = locationRepository.saveAll(List.of(
			new Location(null, "일본", "동경/시즈오카/이바라키"),
			new Location(null, "일본", "오사카/교토/고베")
		));

		List<Flight> flights = flightRepository.saveAll(List.of(
			Flight.builder()
				.flightNumber("JL101")
				.airline("JAL")
				.deptTime(LocalDateTime.of(2025, 10, 22, 10, 0))
				.arrivalTime(LocalDateTime.of(2025, 10, 22, 12, 30))
				.price(new BigDecimal("450000"))
				.location(locations.get(0)) // 첫 번째 지역
				.build(),
			Flight.builder()
				.flightNumber("JL102")
				.airline("ANA")
				.deptTime(LocalDateTime.of(2025, 10, 23, 9, 30))
				.arrivalTime(LocalDateTime.of(2025, 10, 23, 12, 0))
				.price(new BigDecimal("420000"))
				.location(locations.get(1)) // 두 번째 지역
				.build()
		));
	}

	@Test
	@DisplayName("ID로 항공편 조회")
	void getFlightById() {
		// given
		Long id = flightRepository.findAll().get(0).getId();
		// when
		Flight flight = flightService.getFlightById(id);
		// then
		assertThat(flight).isNotNull();
		assertThat(flight.getFlightNumber()).isEqualTo("JL101");
	}

	@Test
	@DisplayName("항공편명으로 항공편 조회")
	void getFlightsByFlightNumber() {
		// given
		String airline = "JAL";

		// 테스트용 데이터 추가 (JAL 항공편 1개 더)
		Location location = locationRepository.findByCity("동경/시즈오카/이바라키").get(0);
		flightRepository.save(
			Flight.builder()
				.flightNumber("JL103")
				.airline("JAL")
				.deptTime(LocalDateTime.of(2025, 10, 25, 8, 0))
				.arrivalTime(LocalDateTime.of(2025, 10, 25, 10, 30))
				.price(new BigDecimal("430000"))
				.location(location)
				.build()
		);
		// when
		List<Flight> flights = flightService.getFlightsByAirline(airline);
		// then
		assertThat(flights).hasSizeGreaterThanOrEqualTo(2);
		assertThat(flights)
			.extracting(Flight::getFlightNumber)
			.contains("JL101", "JL103");
	}

	@Test
	@DisplayName("항공사로 항공편 조회")
	void getFlightsByAirline() {
		// given
		String airline = "JAL";
		// when
		List<Flight> flights = flightService.getFlightsByAirline(airline);
		// then
		assertThat(flights).hasSize(1);
		assertThat(flights.get(0).getFlightNumber()).isEqualTo("JL101");
	}

	@Test
	@DisplayName("도착지로 항공편 조회")
	void getFlightsByLocation() {
		// given
		Location location = locationRepository.findByCity("오사카/교토/고베").get(0);
		// when
		List<Flight> flights = flightService.getFlightsByLocation(location);
		// then
		assertThat(flights).hasSize(1);
		assertThat(flights.get(0).getFlightNumber()).isEqualTo("JL102");
	}

	@Test
	@DisplayName("전체 항공편 조회")
	void getAllFlights() {
		// when
		List<Flight> flights = flightService.getAllFlights();
		// then
		assertThat(flights).hasSize(2);
		assertThat(flights)
			.extracting(Flight::getFlightNumber)
			.containsExactlyInAnyOrder("JL101", "JL102");
	}
}
