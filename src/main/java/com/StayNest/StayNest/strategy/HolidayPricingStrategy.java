package com.StayNest.StayNest.strategy;

import com.StayNest.StayNest.Entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@RequiredArgsConstructor
public class HolidayPricingStrategy implements Pricing_Strategy{
    private final Pricing_Strategy wrapped;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price=wrapped.calculatePrice(inventory);
        boolean isTodayHoliday=true; //call an a api r the local data
        if(isTodayHoliday){
            price=price.multiply(BigDecimal.valueOf(1.25));
        }
        return price;
    }
}
