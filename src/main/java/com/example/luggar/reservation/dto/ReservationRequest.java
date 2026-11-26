package com.example.luggar.reservation.dto;

import com.example.luggar.item.enums.ItemType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record ReservationRequest(

        @NotNull(message = "Start time is required.")
        @Future(message = "Start time must be in the future.")
        LocalDateTime startTime,

        @NotNull(message = "End time is required.")
        @Future(message = "End time must be in the future.")
        LocalDateTime endTime,

        @NotNull(message = "Client is required.")
        Long clientId,

        @NotNull(message = "Storage location is required.")
        Long storageLocationId,

        @NotEmpty(message = "At least one item is required.")
        List<@NotNull ItemType> items
) {}
