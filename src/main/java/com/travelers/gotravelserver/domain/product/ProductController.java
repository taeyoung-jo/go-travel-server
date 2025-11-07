package com.travelers.gotravelserver.domain.product;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travelers.gotravelserver.domain.flight.domain.DeptTimeType;
import com.travelers.gotravelserver.domain.product.dto.ProductResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@GetMapping("/api/products")
	public List<ProductResponse> getProducts(
		@RequestParam(required = false) String region,
		@RequestParam(required = false) Long locationId,
		@RequestParam(required = false) String airline,
		@RequestParam(required = false) DeptTimeType deptTimeType,
		@RequestParam(required = false) BigDecimal minPrice,
		@RequestParam(required = false) BigDecimal maxPrice
	) {
		return productService.getProducts(region, locationId, airline, deptTimeType, minPrice, maxPrice);
	}
}
