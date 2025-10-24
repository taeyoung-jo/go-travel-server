package com.travelers.gotravelserver.domain.product.domain;

import java.math.BigDecimal;

import com.travelers.gotravelserver.domain.location.Location;
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
@Table(name = "products")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "location_id", nullable = false)
	private Location location; // 여행지

	@Column(nullable = false)
	private String name; // 상품명

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	@Column(precision = 2, scale = 1)
	private double rating;

	@Column(nullable = false)
	private int maxParticipants; // 예약 가능한 최대 인원

	@Column(length = 500)
	private String imageUrl;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	private ProductStatus status = ProductStatus.AVAILABLE;

	public void changeStatus(ProductStatus newStatus) {
		if (this.status != newStatus)
			this.status = newStatus;
	}
}
