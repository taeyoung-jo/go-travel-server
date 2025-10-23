package com.travelers.gotravelserver.domain.flight;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.travelers.gotravelserver.domain.location.Location;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "flights")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "location_id", nullable = false)
	private Location location;

	@Column(name = "flight_number", nullable = false)
	private String flightNumber; // 항공편명

	@Column(name = "airline", nullable = false)
	private String airline; // 항공사

	@Column(name = "dept_time", nullable = false)
	private LocalDateTime deptTime; // 출발시간

	@Column(name = "arrival_time", nullable = false)
	private LocalDateTime arrivalTime; // 도착시간

	@Column(name = "price", nullable = false, precision = 10, scale = 2)
	private BigDecimal price;
}
