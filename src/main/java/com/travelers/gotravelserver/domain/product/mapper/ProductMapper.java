package com.travelers.gotravelserver.domain.product.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.travelers.gotravelserver.domain.flight.domain.DeptTimeType;
import com.travelers.gotravelserver.domain.product.dto.ProductResponse;

@Mapper
public interface ProductMapper {

	List<ProductResponse> findProducts(
		@Param("region") String region,
		@Param("locationId") Long locationId,
		@Param("airline") String airline,
		@Param("deptTimeType") DeptTimeType deptTimeType,
		@Param("minPrice") BigDecimal minPrice,
		@Param("maxPrice") BigDecimal maxPrice,
		@Param("keyword") String keyword
	);
}
