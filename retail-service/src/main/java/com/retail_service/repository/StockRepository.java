package com.retail_service.repository;

import com.retail_service.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByProductId(Long Productid);
    boolean existsByProductId(Long productId);
}
