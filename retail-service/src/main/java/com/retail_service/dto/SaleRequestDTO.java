package com.retail_service.dto;

import com.retail_service.domain.sale.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleRequestDTO {

    private Long userId;
    private PaymentMethod paymentMethod;
    private List<ItemSaleRequestDTO> items;

}
