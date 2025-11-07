package com.travelers.gotravelserver.domain.flight.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "flights")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "location_id", nullable = false)
	private Location location;

	@Column(nullable = false)
	private String flightNumber; // 항공편명

	@Column(nullable = false)
	private String airline; // 항공사

	@Column(nullable = false)
	private LocalDate deptDate; // 출발일

	@Column(nullable = false)
	private LocalDateTime deptTime; // 출발시간

	@Column(nullable = false)
	private LocalDateTime arrivalTime; // 도착시간

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	// deptTime 기준으로 departureDate 자동 세팅
	@PrePersist
	@PreUpdate
	private void syncDeptDate() {
		if (this.deptTime != null)
			this.deptDate = this.deptTime.toLocalDate();
	}
}
