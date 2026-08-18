package com.retail_service.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Table(name = "products")
@Entity(name = "product")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    // to simplify i`ll not use this right now. private String barCode;
    private BigDecimal price;



}
