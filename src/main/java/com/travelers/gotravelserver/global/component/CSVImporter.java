package com.travelers.gotravelserver.global.component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.travelers.gotravelserver.domain.flight.dto.FlightCsvDto;
import com.travelers.gotravelserver.domain.flight.mapper.FlightMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CSVImporter implements CommandLineRunner {

	private final FlightMapper flightMapper;

	public CSVImporter(FlightMapper flightMapper) {
		this.flightMapper = flightMapper;
	}

	@Override
	public void run(String... args) throws Exception {
		List<FlightCsvDto> flights = new ArrayList<>();
		try (var reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(
			getClass().getClassLoader().getResourceAsStream("db/import/flights_data_updated.csv")),
			StandardCharsets.UTF_8))) {

			// 첫 줄(헤더) 건너뜀
			reader.lines().skip(1).forEach(line -> {
				try {
					String[] p = line.split(",", -1);
					flights.add(FlightCsvDto.builder()
						.flightNumber(p[0].trim())
						.airline(p[1].trim())
						.deptTime(Timestamp.valueOf(p[2].trim()))
						.arrivalTime(Timestamp.valueOf(p[3].trim()))
						.price(new BigDecimal(p[4].trim()))
						.destination(p[5].trim())
						.build());
				} catch (Exception e) {
					log.error("CSV 파싱 오류: {} → {}", line, e.getMessage());
				}
			});
		}
		flightMapper.insertFlights(flights);
		log.info("✅ flights_data_updated.csv {}건 삽입 완료", flights.size());
	}
}
