package com.StayNest.StayNest.strategy;

import com.StayNest.StayNest.Entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@RequiredArgsConstructor
public class OccupancyPricingStrategy implements Pricing_Strategy{
    private final Pricing_Strategy wrapped;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price=wrapped.calculatePrice(inventory);
        double occupancyRate=(double) inventory.getBookedCount()/inventory.getTotalCount();
        if(occupancyRate>0.8){
            price=price.multiply(BigDecimal.valueOf(1.2));
        }
        return price;
    }
}
