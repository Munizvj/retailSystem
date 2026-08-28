package com.retail_service.dto.stockDTO;

import com.retail_service.model.Product;
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
