package com.devs.product.api.kafka;


import com.devs.product.api.dto.ProductDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public ProductEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendProductCreatedEvent(ProductDTO productDTO) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String message = mapper.writeValueAsString(productDTO);
        kafkaTemplate.send("product-created-topic", message);
    }
}
