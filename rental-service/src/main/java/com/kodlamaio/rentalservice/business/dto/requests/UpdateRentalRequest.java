package com.kodlamaio.rentalservice.business.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRentalRequest {
    @NotNull
    private UUID carId;
    @Min(1)
    private double dailyPrice;
    @Min(1)
    private int rentedForDays;
    @NotNull
    private LocalDate rentedAt;
    //  private UUID customerId;
    //todo: customer ı da ekle zaman bulursan
}