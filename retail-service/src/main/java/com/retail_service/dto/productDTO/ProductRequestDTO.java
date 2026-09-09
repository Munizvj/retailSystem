package com.retail_service.dto.productDTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestDTO {

    private String name;
    private String description;
    private BigDecimal price;
    private Boolean active;

}
