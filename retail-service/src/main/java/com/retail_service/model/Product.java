package com.retail_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Table(name = "products")
@Entity(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean active;
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private Stock stock;

}
