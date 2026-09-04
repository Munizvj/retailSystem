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

    private Long Userid;

    private BigDecimal total;

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
    private List<Product> saleList = new ArrayList<>();

}
