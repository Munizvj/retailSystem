package com.retail_service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SaleEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String SALE_CREATED_TOPIC = "sale-created";
    private static final String SALE_CANCELLED_TOPIC = "sale-cancelled";

    public void sendSaleCreatedEvent(SaleCreatedEvent event) {

        kafkaTemplate.send(
                SALE_CREATED_TOPIC,
                event.saleId().toString(),
                event
        );

        log.info("Sale created event sent, Sale ID: {}, Sale Status: {}", event.saleId(), event.saleStatus());

    }

    public void sendSaleCancelledEvent(SaleCancelledEvent event){

        kafkaTemplate.send(
                SALE_CANCELLED_TOPIC,
                event.saleId().toString(),
                event
        );

        log.info("Sale cancelled event sent, Sale ID: {}, Sale Status: {}", event.saleId(), event.saleStatus());

    }


}
