package com.example.luggar.reservation;

import com.example.luggar.item.Item;
import com.example.luggar.item.ItemMapper;
import com.example.luggar.item.enums.ItemType;
import com.example.luggar.location.StorageLocation;
import com.example.luggar.reservation.dto.ReservationRequest;
import com.example.luggar.reservation.dto.ReservationResponse;
import com.example.luggar.user.User;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    private final ItemMapper itemMapper;

    public ReservationMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    public Reservation toEntity(ReservationRequest request, User client, StorageLocation location){
        Reservation reservation = new Reservation();
        reservation.setStartTime(request.startTime());
        reservation.setEndTime(request.endTime());
        reservation.setClient(client);
        reservation.setStorageLocation(location);
        for(ItemType type : request.items()){
            Item item = new Item();
            item.setItemType(type);
            reservation.addItem(item);
        }

        return reservation;
    }

    public ReservationResponse toResponse(Reservation reservation){
        return new ReservationResponse(
                reservation.getId(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getPrice(),
                reservation.getReservationStatus(),
                reservation.getPaymentStatus(),
                reservation.getQrCode(),
                reservation.getItems().stream()
                        .map(itemMapper::toResponse)
                        .toList(),
                reservation.getClient().getId(),
                reservation.getStorageLocation().getId()
        );
    }
}
