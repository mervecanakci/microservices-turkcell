package com.kodlamio.filterservice.api.controllers;

import com.kodlamio.filterservice.business.abstracts.FilterService;
import com.kodlamio.filterservice.business.dto.responses.GetAllFiltersResponse;
import com.kodlamio.filterservice.business.dto.responses.GetFilterResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/filters")
public class FiltersController {
    private final FilterService service;


/*    @PostConstruct //db de bir tane ekleme işlemi yapıcak
    public void createDb() {
        var filter = new Filter();
        filter.setBrandName("test");
        filter.setModelName("testx");
        service.add(filter);
    }*/
    //? veri tabanı oluştu bir kere çalıştırdık o nedenle kaldırdık daha
    // var filter.... bu da veri olsun diye
/*
    @PostConstruct
    public void createDb() {
        var filter = new Filter();
        filter.setBrandName("test");
        filter.setModelName("testx");
        service.add(filter);

    }*/

    @GetMapping
    List<GetAllFiltersResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GetFilterResponse getById(@PathVariable UUID id) {
        return service.getById(id);
    }
}