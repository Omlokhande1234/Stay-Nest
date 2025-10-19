package com.StayNest.StayNest.strategy;

import com.StayNest.StayNest.Entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
public class UrgencyPricingStrategy implements Pricing_Strategy {
    private final Pricing_Strategy wrapped;


    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price=wrapped.calculatePrice(inventory);
        LocalDate today=LocalDate.now();
        if(!inventory.getDate().isBefore(today) && !inventory.getDate().isBefore((today.plusDays(7)))){
            price=price.multiply(BigDecimal.valueOf(1.15));
        }
        return price;
    }
}
