package com.travelers.gotravelserver.domain.location;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travelers.gotravelserver.global.exception.CustomException;
import com.travelers.gotravelserver.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {

	private final LocationRepository locationRepository;

	// ID로 조회
	public Location getLocationById(Long id) {
		return locationRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.LOCATION_NOT_FOUND));
	}

	// 지역(region)으로 조회
	public List<Location> getLocationsByRegion(String region) {
		return locationRepository.findByRegion(region);
	}

	// 도시(city)로 조회
	public List<Location> getLocationsByCity(String city) {
		return locationRepository.findByCity(city);
	}

	// 지역 + 도시로 정확히 조회
	public Location getLocationsByRegionAndCity(String region, String city) {
		return locationRepository.findByRegionAndCity(region, city)
			.orElseThrow(() -> new CustomException(ErrorCode.LOCATION_NOT_FOUND));
	}

	// 전체 조회
	public List<Location> getAllLocations() {
		return locationRepository.findAll();
	}
}
