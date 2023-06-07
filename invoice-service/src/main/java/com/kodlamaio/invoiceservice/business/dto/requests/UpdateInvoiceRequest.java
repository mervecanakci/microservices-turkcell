package com.kodlamaio.invoiceservice.business.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInvoiceRequest {
    @NotBlank
    private String cardHolder;
    @NotBlank
    private String modelName;
    @NotBlank
    private String brandName;
    @NotBlank
    private String plate;
    @Min(1996)
    private int modelYear;
    @Min(1)
    private double dailyPrice;
    @Min(1)
    private int rentedForDays;
    private LocalDate rentedAt;

}
