package com.example.luggar.location.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StorageLocationRequest(

        @NotBlank(message = "Name is required.")
        @Size(max = 100)
        String name,

        @NotBlank(message = "Address is required.")
        @Size(max = 255)
        String address,

        @NotNull(message = "Capacity is required.")
        @Min(value = 1, message = "Capacity must be at least 1.")
        Integer capacity
) {}
