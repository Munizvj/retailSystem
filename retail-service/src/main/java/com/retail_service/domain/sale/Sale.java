package com.retail_service.domain.sale;

import com.retail_service.domain.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sales")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private BigDecimal total = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

    private LocalDateTime createdAt;

    private LocalDateTime finalizeAt;

    @OneToMany(
            mappedBy = "sale",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ItemSale> saleList = new ArrayList<>();

    public static Sale create(Long userId, PaymentMethod paymentMethod) {
        Sale sale = new Sale();
        sale.setUserId(userId);
        sale.setPaymentMethod(paymentMethod);
        sale.setSaleStatus(SaleStatus.PENDING);
        sale.setCreatedAt(LocalDateTime.now());
        sale.setTotal(BigDecimal.ZERO);
        return sale;
    }

    public void addItem(Product product, Integer quantity) {
        ItemSale item = new ItemSale(this, product, quantity);
        this.saleList.add(item);

        if (item.getSubTotal() != null) {
            this.total = this.total.add(item.getSubTotal());
        }
    }

    public void finalizeSale() {
        if (this.saleStatus != SaleStatus.PENDING) {
            throw new IllegalArgumentException("Only Sales with pending status can be finished");
        }
        this.saleStatus = SaleStatus.FINISHED;
        this.finalizeAt = LocalDateTime.now();
    }

    public void cancelSale() {
        this.saleStatus = SaleStatus.CANCELLED;
    }

}
