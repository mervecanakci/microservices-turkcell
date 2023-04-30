package com.kodlamaio.inventoryservice.business.dto.requests.create;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarRequest {
    @NotNull
    @NotBlank
    private UUID modelId;
    @Min(value = 2000)
    //TODO: not future custom annotations
    private int modelYear;
    @NotNull
    @NotBlank
    //TODO: add regex
    private String plate;
    @Min(value = 1)
    private double dailyPrice;

}
