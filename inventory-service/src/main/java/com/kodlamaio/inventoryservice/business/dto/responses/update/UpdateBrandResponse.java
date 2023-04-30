package com.kodlamaio.inventoryservice.business.dto.responses.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBrandResponse {
    private UUID id;
    private String name;

}
