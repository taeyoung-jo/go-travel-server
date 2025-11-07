package com.travelers.gotravelserver.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travelers.gotravelserver.domain.product.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
