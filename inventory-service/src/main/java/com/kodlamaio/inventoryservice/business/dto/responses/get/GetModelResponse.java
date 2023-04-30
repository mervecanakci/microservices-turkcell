package com.kodlamaio.inventoryservice.business.dto.responses.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetModelResponse {
    private UUID id;
    private UUID brandId;
    private String name;
}
