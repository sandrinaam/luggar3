package com.example.luggar.location.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record StorageLocationRequest(

        @NotBlank(message = "Name is required.")
        @Size(max = 100)
        String name,

        @NotBlank(message = "Address is required.")
        @Size(max = 255)
        String address,

        @NotNull(message = "Capacity is required.")
        @Min(value = 1, message = "Capacity must be at least 1.")
        Integer capacity,

        @NotNull(message = "Base price is required.")
        @Positive
        BigDecimal basePrice
) {}
