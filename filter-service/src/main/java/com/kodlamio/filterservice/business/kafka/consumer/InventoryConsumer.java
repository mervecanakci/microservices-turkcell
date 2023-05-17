package com.kodlamio.filterservice.business.kafka.consumer;

import com.kodlamaio.commonpackage.events.BrandDeletedEvent;
import com.kodlamaio.commonpackage.events.CarCreatedEvent;
import com.kodlamaio.commonpackage.events.CarDeletedEvent;
import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamio.filterservice.business.abstracts.FilterService;
import com.kodlamio.filterservice.entities.Filter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class InventoryConsumer {
    //ARAÇ BİLGİLERİNİ CONSUME EDİCEK
    private final FilterService service;
    private final ModelMapperService mapper;

    @KafkaListener( //hangi topic e göre yapacağı falan
            topics = "car-created", //car-created ı dinleyecek
            groupId = "car-create"
    )


    public void consume(CarCreatedEvent event) {
        var filter = mapper.forRequest().map(event, Filter.class);
        service.add(filter);
        log.info("Car created event consumed {}", event);
    }


    @KafkaListener( //hangi topic e göre yapacağı falan
            topics = "car-deleted", //car-deleted ı dinleyecek
            groupId = "car-delete"
    )


    public void consume(CarDeletedEvent event) {
        service.deleteByCarId(event.getCarId());
        log.info("Car deleted event consumed {}", event);
    }//aracı da silmiş olacak bir yerden silindiğinde hepsinden de


    @KafkaListener( //hangi topic e göre yapacağı falan
            topics = "brand-deleted", //brand-deleted ı dinleyecek
            groupId = "brand-delete"
    )


    public void consume(BrandDeletedEvent event) {
        var filter = mapper.forRequest().map(event, Filter.class);
        service.deleteAllByBrandId(event.getBrandId());
        log.info("Brand deleted event consumed {}", event);
    }
}
