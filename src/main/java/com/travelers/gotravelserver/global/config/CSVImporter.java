package com.travelers.gotravelserver.global.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
		// 이미 데이터 있으면 삽입 건너뜀
		if (flightMapper.countFlights() > 0) {
			log.info("⚠️ flights 테이블에 이미 데이터 존재. CSV import 건너뜀.");
			return;
		}

		final int BATCH_SIZE = 1000;
		List<FlightCsvDto> batch = new ArrayList<>(BATCH_SIZE);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		try (var reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(
			getClass().getClassLoader().getResourceAsStream("db/import/flights_data_updated.csv")),
			StandardCharsets.UTF_8))) {

			// 첫 줄(헤더) 건너뜀
			reader.lines().skip(1).forEach(line -> {
				try {
					String[] p = line.split(",", -1);
					LocalDateTime deptTime = LocalDateTime.parse(p[2].trim(), formatter);
					LocalDateTime arrivalTime = LocalDateTime.parse(p[3].trim(), formatter);
					batch.add(FlightCsvDto.builder()
						.flightNumber(p[0].trim())
						.airline(p[1].trim())
						.deptDate(deptTime.toLocalDate())
						.deptTime(deptTime)
						.arrivalTime(arrivalTime)
						.price(new BigDecimal(p[4].trim()))
						.destination(p[5].trim())
						.build());

					// 일정 크기 도달 시 flush
					if (batch.size() >= BATCH_SIZE) {
						flightMapper.insertFlights(batch);
						log.info("✅ {}건 삽입 완료 (누적)", BATCH_SIZE);
						batch.clear();
					}
				} catch (Exception e) {
					log.error("CSV 파싱 오류: {} → {}", line, e.getMessage());
				}
			});

			// 마지막 남은 데이터 처리
			if (!batch.isEmpty())
				flightMapper.insertFlights(batch);
		}
		log.info("✅ flights_data_updated.csv {}건 삽입 완료", batch.size());
	}
}
