package com.travelers.gotravelserver.domain.location;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.travelers.gotravelserver.global.exception.CustomException;
import com.travelers.gotravelserver.global.exception.ErrorCode;

@SpringBootTest
@Transactional
class LocationServiceTest {

	@Autowired
	private LocationService locationService;

	@Autowired
	private LocationRepository locationRepository;

	@BeforeEach
	void setUp() {
		locationRepository.saveAll(List.of(
			new Location(null, "일본", "오사카/교토/고베"),
			new Location(null, "일본", "동경/시즈오카/이바라키"),
			new Location(null, "중국", "북경(베이징)")
		));
	}

	@Test
	@DisplayName("ID로 특정 지역 조회")
	void getLocationById() {
		// given
		Long id = locationRepository.findAll().get(0).getId();
		// when
		Location found = locationService.getLocationById(id);
		// then
		assertThat(found).isNotNull();
		assertThat(found.getRegion()).isEqualTo("일본");
	}

	@Test
	@DisplayName("존재하지 않는 ID 조회 시 예외 발생")
	void getLocationByIdNotFound() {
		// when & then
		assertThrows(CustomException.class, () -> locationService.getLocationById(999L));
	}

	@Test
	@DisplayName("지역(region)으로 조회")
	void getLocationsByRegion() {
		// when
		List<Location> results = locationService.getLocationsByRegion("일본");
		// then
		assertThat(results).hasSize(2);
		assertThat(results)
			.extracting(Location::getCity)
			.contains("오사카/교토/고베", "동경/시즈오카/이바라키");
	}

	@Test
	@DisplayName("도시(city)로 조회")
	void getLocationsByCity() {
		// when
		List<Location> results = locationService.getLocationsByCity("북경(베이징)");
		// then
		assertThat(results).hasSize(1);
		assertThat(results.get(0).getRegion()).isEqualTo("중국");
	}

	@Test
	@DisplayName("지역과 도시로 정확히 조회 - 존재하는 경우")
	void getLocationsByRegionAndCity() {
		// when
		Location result = locationService.getLocationsByRegionAndCity("일본", "오사카/교토/고베");
		// then
		assertThat(result).isNotNull();
		assertThat(result.getRegion()).isEqualTo("일본");
		assertThat(result.getCity()).isEqualTo("오사카/교토/고베");
	}

	@Test
	@DisplayName("지역과 도시로 정확히 조회 - 존재하지 않는 경우 예외 발생")
	void getLocationsByRegionAndCityNotFound() {
		CustomException exception = assertThrows(CustomException.class,
			() -> locationService.getLocationsByRegionAndCity("한국", "서울"));

		assertThat(exception.getCode()).isEqualTo(ErrorCode.LOCATION_NOT_FOUND);
	}

	@Test
	@DisplayName("전체 지역 조회")
	void getAllLocations() {
		// when
		List<Location> locations = locationService.getAllLocations();
		// then
		assertThat(locations).hasSize(3);
		assertThat(locations)
			.extracting(Location::getRegion)
			.contains("일본", "중국");
	}
}
