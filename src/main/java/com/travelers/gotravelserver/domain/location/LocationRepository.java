package com.travelers.gotravelserver.domain.location;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

	List<Location> findByRegion(String region);

	List<Location> findByCity(String city);

	List<Location> findByRegionAndCity(String region, String city);
}
