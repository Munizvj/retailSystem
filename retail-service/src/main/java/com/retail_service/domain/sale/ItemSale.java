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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal subTotal;

    public ItemSale(Sale sale, Product product, Integer quantity){
        if (quantity == null || quantity <= 0){
            throw new IllegalArgumentException("The quantity must be greater than 0");
        }

        this.sale = sale;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = product.getPrice();
        this.subTotal = calculateSubTotal();

    }

    private BigDecimal calculateSubTotal() {

        return this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));

    }

}
