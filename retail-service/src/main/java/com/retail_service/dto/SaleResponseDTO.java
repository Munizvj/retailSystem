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
public class SaleResponseDTO {

    Long id;
    Long userId;
    BigDecimal total;
    PaymentMethod paymentMethod;
    SaleStatus status;
    LocalDateTime createAt;
    LocalDateTime finalizeAt;
    List<ItemSaleResponseDTO> items;

}
