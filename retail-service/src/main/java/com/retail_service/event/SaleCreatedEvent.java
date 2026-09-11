package com.retail_service.event;

import com.retail_service.domain.sale.PaymentMethod;
import com.retail_service.domain.sale.SaleStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SaleCreatedEvent(

        Long saleId,
        Long userId,
        BigDecimal total,
        PaymentMethod paymentMethod,
        SaleStatus saleStatus,
        LocalDateTime finalizedAt

) {}
