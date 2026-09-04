package com.retail_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemSaleResponseDTO {

    private Long id;
    private Long productid;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subTotal;

}
