package com.kodlamaio.inventoryservice.business.dto.responses.get.all;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllBrandsResponse {
    private UUID id;
    private String name;
}
