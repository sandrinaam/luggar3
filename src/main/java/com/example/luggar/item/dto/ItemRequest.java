package com.example.luggar.item.dto;

import com.example.luggar.item.enums.ItemType;
import jakarta.validation.constraints.NotNull;

public record ItemRequest(
        @NotNull ItemType itemType
        ) {
}
