package com.travelers.gotravelserver.domain.product.dto;

import java.math.BigDecimal;

import com.travelers.gotravelserver.domain.product.domain.Product;
import com.travelers.gotravelserver.domain.product.domain.ProductStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {

	private Long id;
	private String region;
	private String city;
	private String name;
	private BigDecimal price;
	private Byte days;
	private int seats;
	private String imageUrl;
    private String detailUrl;
	private ProductStatus status;

	public static ProductResponse from(Product product) {
		return ProductResponse.builder()
			.id(product.getId())
			.region(product.getLocation().getRegion())
			.city(product.getLocation().getCity())
			.name(product.getName())
			.price(product.getPrice())
			.days(product.getDays())
			.seats(product.getSeats())
			.imageUrl(product.getImageUrl())
            .detailUrl(product.getDetailUrl())
			.status(product.getStatus())
			.build();
	}
}
