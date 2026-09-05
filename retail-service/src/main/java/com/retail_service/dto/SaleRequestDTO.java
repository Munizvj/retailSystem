package com.retail_service.dto;

import com.retail_service.domain.sale.PaymentMethod;
import com.retail_service.domain.sale.SaleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleRequestDTO {

    private Long userId;
    private PaymentMethod paymentMethod;
    private List<ItemSaleRequestDTO> items;

}
