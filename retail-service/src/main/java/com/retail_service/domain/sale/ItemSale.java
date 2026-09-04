package com.retail_service.domain.sale;

import com.retail_service.domain.Product;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "itemSales")
public class ItemSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;


    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal subTotal;

}
