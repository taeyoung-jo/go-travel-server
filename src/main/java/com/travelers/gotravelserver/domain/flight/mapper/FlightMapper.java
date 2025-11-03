package com.travelers.gotravelserver.domain.flight.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.travelers.gotravelserver.domain.flight.dto.FlightCsvDto;

@Mapper
public interface FlightMapper {

	void insertFlights(@Param("flights") List<FlightCsvDto> flights);
}
