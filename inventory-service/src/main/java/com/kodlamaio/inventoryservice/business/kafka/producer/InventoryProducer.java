package com.kodlamaio.inventoryservice.business.kafka.producer;

import com.kodlamaio.commonpackage.events.BrandDeletedEvent;
import com.kodlamaio.commonpackage.events.CarCreatedEvent;
import com.kodlamaio.commonpackage.events.CarDeletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor // final yazmazsak oluşturmayacak o işe yarar
@Service
public class InventoryProducer {
    //serialize -->
    // deserialize <--
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static Logger LOGGER = LoggerFactory.getLogger(InventoryProducer.class);

    public void sendMessage(CarCreatedEvent event) {
        log.info(String.format("car-created event ==> %s", event.toString()));
        Message<CarCreatedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, "car-created")
                .build();
        kafkaTemplate.send(message);
        // bu işlem araç oluştuğu için
    }

    public void sendMessage(CarDeletedEvent event) {
        log.info(String.format("car-deleted event ==> %s", event.toString()));
        Message<CarDeletedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, "car-deleted")
                .build();
        kafkaTemplate.send(message);
        //araç silince olanı yazdık
    }

    public void sendMessage(BrandDeletedEvent event) {
        log.info(String.format("brand-deleted event ==> %s", event.toString()));
        Message<BrandDeletedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, "brand-deleted")
                .build();
        kafkaTemplate.send(message);
        //araç silince olanı yazdık
    }
}
