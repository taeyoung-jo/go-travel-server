package com.travelers.gotravelserver.domain.product;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travelers.gotravelserver.domain.flight.domain.DeptTimeType;
import com.travelers.gotravelserver.domain.product.domain.Product;
import com.travelers.gotravelserver.domain.product.dto.ProductResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@GetMapping
	public ResponseEntity<List<ProductResponse>> getProducts(
		@RequestParam(required = false) String region,
		@RequestParam(required = false) Long locationId,
		@RequestParam(required = false) String airline,
		@RequestParam(required = false) DeptTimeType deptTimeType,
		@RequestParam(required = false) BigDecimal minPrice,
		@RequestParam(required = false) BigDecimal maxPrice,
		@RequestParam(required = false) String keyword
	) {
		List<ProductResponse> response = productService.getProducts(
			region, locationId, airline, deptTimeType, minPrice, maxPrice, keyword);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> getProductDetail(@PathVariable Long id) {
		Product product = productService.getProductById(id);
		return ResponseEntity.ok(ProductResponse.from(product));
	}
}
