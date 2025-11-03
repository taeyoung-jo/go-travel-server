package com.travelers.gotravelserver.global.component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CSVImporter implements CommandLineRunner {

	private final JdbcTemplate  jdbcTemplate;

	public CSVImporter(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void run(String... args) throws Exception {
		try (var reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("db/import/flights_data_updated.csv"), "UTF-8")))) {
			// 첫 줄(헤더) 건너뜀
			reader.lines().skip(1).forEach(line -> {
				String[] p = line.split(",", -1);
				try {
					String flightNumber = p[0].trim();
					String airline = p[1].trim();
					Timestamp deptTime =  Timestamp.valueOf(p[2].trim());
					Timestamp arrivalTime =  Timestamp.valueOf(p[3].trim());
					BigDecimal price = new  BigDecimal(p[4].trim());
					String destination = p[5].trim();
					jdbcTemplate.update(
						"insert into flights (location_id, flight_number, airline, dept_time, arrival_time, price) "
							+ "select id, ?, ?, ?, ?, ? from locations where city = ?",
						flightNumber, airline, deptTime, arrivalTime, price, destination);
				} catch (Exception e) {
					log.error("CSV 삽입 오류: {} → {}", line, e.getMessage());
				}
			});
		}
		log.info("✅ flights_data_updated.csv 데이터 삽입 완료");
	}
}
