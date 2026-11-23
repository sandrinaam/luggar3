package com.example.luggar.location.dto;

public record StorageLocationResponse(
        Long storageId,
        String name,
        String address,
        Integer capacity,
        Long ownerId

) {
}
