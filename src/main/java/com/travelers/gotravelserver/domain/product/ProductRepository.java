package com.travelers.gotravelserver.domain.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travelers.gotravelserver.domain.product.domain.Product;
import com.travelers.gotravelserver.domain.product.domain.ProductStatus;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	// 상품 상태별 조회
	List<Product> findByStatus(ProductStatus status);

	// 상품명 중 일부 포함 검색
	List<Product> findByNameContaining(String keyword);
}
