package com.travelers.gotravelserver.domain.product.domain;

import java.math.BigDecimal;

import com.travelers.gotravelserver.domain.location.Location;
import com.travelers.gotravelserver.global.entity.BaseTimeEntity;
import com.travelers.gotravelserver.global.exception.CustomException;
import com.travelers.gotravelserver.global.exception.ErrorCode;

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
	private BigDecimal price; // 패키지의 기본 가격

	@Column(nullable = false)
	private Byte days; // 체류일수 tinyint

	@Builder.Default
	@Column(nullable = false)
	private int seats = 20; // 예약 가능한 좌석 수

	@Column(name = "image_url", length = 500)
	private String imageUrl;

    @Column(name = "detail_url", length = 500)
    private String detailUrl;

	@Builder.Default
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ProductStatus status = ProductStatus.AVAILABLE;

	// 좌석 차감 (예약)
	public void decreaseSeats(int count) {
		if (seats < count)
			throw new CustomException(ErrorCode.INSUFFICIENT_REMAINING_SEATS);
		this.seats -= count;
	}

	// 좌석 복원 (예약 취소)
	public void increaseSeats(int count) {
		this.seats += count;
	}

	// 상품 상태 변경
	public void changeStatus(ProductStatus newStatus) {
		if (this.status != newStatus)
			this.status = newStatus;
	}
}
