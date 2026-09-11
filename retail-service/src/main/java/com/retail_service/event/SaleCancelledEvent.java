package com.retail_service.event;

import com.retail_service.domain.sale.PaymentMethod;
import com.retail_service.domain.sale.SaleStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SaleCancelledEvent(

        Long saleId,
        Long userId,
        SaleStatus saleStatus

) {
}
