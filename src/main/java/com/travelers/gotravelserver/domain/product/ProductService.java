package com.travelers.gotravelserver.domain.product;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travelers.gotravelserver.domain.product.domain.Product;
import com.travelers.gotravelserver.domain.product.domain.ProductStatus;
import com.travelers.gotravelserver.domain.reservation.ReservationService;
import com.travelers.gotravelserver.global.exception.CustomException;
import com.travelers.gotravelserver.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

	private final ProductRepository productRepository;
	private final ReservationService reservationService;

	// ID로 패키지 조회
	public Product getProductById(Long id) {
		return productRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
	}

	// 상태별 패키지 조회
	public List<Product> getProductsByStatus(ProductStatus status) {
		return productRepository.findByStatus(status);
	}

	// 상품 상태 변경
	@Transactional
	public Product updateProductStatus(Long productId, ProductStatus newStatus) {
		Product product = getProductById(productId);
		product.changeStatus(newStatus);
		return product;
	}
}
