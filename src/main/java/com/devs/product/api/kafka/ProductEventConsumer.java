package com.devs.product.api.kafka;


import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductEventConsumer {

    @KafkaListener(topics ="product-created-topic", groupId ="consumer")
    public void listen(String message) {
        log.info("Producto recibido desde Kafka: ".concat(message));

    }
    }
