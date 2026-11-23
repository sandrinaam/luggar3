package com.example.luggar.item.dto;

import com.example.luggar.item.enums.ItemType;

public record ItemResponse(
        Long id,
        ItemType itemType
) {
}
