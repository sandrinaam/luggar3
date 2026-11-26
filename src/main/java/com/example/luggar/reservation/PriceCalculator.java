package com.example.luggar.reservation;

import com.example.luggar.item.Item;
import com.example.luggar.item.enums.ItemType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

@Component
public class PriceCalculator {

    public BigDecimal calculate(Reservation reservation){
        BigDecimal basePrice = reservation.getStorageLocation().getBasePrice();
        long days = calculateDays(reservation);

        BigDecimal coefficientsSum = reservation.getItems().stream()
                .map(Item::getItemType)
                .map(ItemType::getCoefficient)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return basePrice
                .multiply(coefficientsSum)
                .multiply(BigDecimal.valueOf(days))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private long calculateDays(Reservation reservation){
        long minutes = Duration.between(reservation.getStartTime(), reservation.getEndTime()).toMinutes();
        long days = (minutes + 1439) / 1440;
        return Math.max(days, 1);
    }
}
