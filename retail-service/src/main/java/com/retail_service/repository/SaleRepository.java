package com.retail_service.repository;

import com.retail_service.domain.sale.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
