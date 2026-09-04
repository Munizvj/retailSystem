package com.retail_service.dto.stockDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockRequestDTO {

    private Long productId;
    private Integer quantity;

}
