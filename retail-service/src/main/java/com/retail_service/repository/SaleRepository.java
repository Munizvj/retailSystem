package com.retail_service.repository;

import com.retail_service.domain.sale.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("""
                SELECT s
                FROM Sale s
                LEFT JOIN FETCH s.saleList i
                LEFT JOIN FETCH i.product
                WHERE s.id = :id
            """)
    Optional<Sale> findByIdWithItemsAndProducts(Long id);


}
