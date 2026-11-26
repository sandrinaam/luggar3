package com.example.luggar.location.dto;

import java.math.BigDecimal;

public record StorageLocationResponse(
        Long storageId,
        String name,
        String address,
        Integer capacity,
        Long ownerId,
        BigDecimal basePrice
) {
}
