package com.kodlamaio.inventoryservice.business.dto.responses.get.all;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllModelsResponse {
    private UUID id;
    private UUID brandId;
    private String name;
}
