package com.travelers.gotravelserver.domain.reservation.domain;

import java.math.BigDecimal;

import org.hibernate.annotations.SQLDelete;

import com.travelers.gotravelserver.domain.flight.domain.Flight;
import com.travelers.gotravelserver.domain.product.domain.Product;
import com.travelers.gotravelserver.domain.user.User;
import com.travelers.gotravelserver.global.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservations")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update reservations set deleted = true where id = ?")
public class Reservation extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "flight_id", nullable = false)
	private Flight flight;

	@Column(nullable = false)
	private int participants; // 예약 인원 수

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price; // 예약한 총 가격

	@Builder.Default
	@Enumerated(EnumType.STRING)
	private ReservationStatus status = ReservationStatus.PENDING;

	@Builder.Default
	private Boolean deleted = Boolean.FALSE;

	public void changeStatus(ReservationStatus newStatus) {
		if (this.status != newStatus)
			this.status = newStatus;
	}
}
