package com.StayNest.StayNest.strategy;

import com.StayNest.StayNest.Entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@RequiredArgsConstructor
public class SurgePricingStrategy implements Pricing_Strategy {
    //The surgePricing strategy will wrap the particular surgePricingStrategy
    private final Pricing_Strategy wrapped;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price=wrapped.calculatePrice(inventory);
        return price.multiply(inventory.getSurgeFactor());
    }
}
