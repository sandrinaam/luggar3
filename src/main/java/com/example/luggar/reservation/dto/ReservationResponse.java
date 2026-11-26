package com.example.luggar.reservation.dto;

import com.example.luggar.item.dto.ItemResponse;
import com.example.luggar.reservation.enums.PaymentStatus;
import com.example.luggar.reservation.enums.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ReservationResponse(
        Long reservationId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        BigDecimal price,
        ReservationStatus reservationStatus,
        PaymentStatus paymentStatus,
        String qrCode,
        List<ItemResponse> items,
        Long clientId,
        Long storageLocationId
) {
}
