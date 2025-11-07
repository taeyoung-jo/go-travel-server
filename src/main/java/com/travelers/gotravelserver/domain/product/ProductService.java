package com.travelers.gotravelserver.domain.product;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travelers.gotravelserver.domain.flight.domain.DeptTimeType;
import com.travelers.gotravelserver.domain.product.dto.ProductResponse;
import com.travelers.gotravelserver.domain.product.mapper.ProductMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

	private final ProductMapper productMapper;

	public List<ProductResponse> getProducts(
		String region,
		Long locationId,
		String airline,
		DeptTimeType deptTimeType,
		BigDecimal minPrice,
		BigDecimal maxPrice
	) {
		return productMapper.findProducts(region, locationId, airline, deptTimeType, minPrice, maxPrice);
	}
}
