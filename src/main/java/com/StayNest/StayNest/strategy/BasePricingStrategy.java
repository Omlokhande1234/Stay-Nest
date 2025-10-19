package com.StayNest.StayNest.strategy;

import com.StayNest.StayNest.Entity.Inventory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


public class BasePricingStrategy implements Pricing_Strategy {

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        return inventory.getRoom().getBasePrice();
    }
}
