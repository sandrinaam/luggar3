package com.example.luggar.item;

import com.example.luggar.item.dto.ItemRequest;
import com.example.luggar.item.dto.ItemResponse;
import com.example.luggar.reservation.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public Item toEntity(ItemRequest request, Reservation reservation){
        Item item = new Item();
        item.setItemType(request.itemType());
        item.setReservation(reservation);
        return item;
    }

    public ItemResponse toResponse(Item item){
        return new ItemResponse(
                item.getId(),
                item.getItemType()
        );
    }

}
