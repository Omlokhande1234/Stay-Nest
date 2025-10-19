package com.StayNest.StayNest.strategy;

import com.StayNest.StayNest.Entity.Inventory;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PricingService {
    public BigDecimal calculateDynamicPricing(Inventory inventory){
        Pricing_Strategy pricingStrategy =new BasePricingStrategy();
        pricingStrategy =new SurgePricingStrategy(pricingStrategy);
        pricingStrategy=new OccupancyPricingStrategy(pricingStrategy);
        pricingStrategy=new UrgencyPricingStrategy(pricingStrategy);
        pricingStrategy=new HolidayPricingStrategy(pricingStrategy);

        return pricingStrategy.calculatePrice(inventory);
    }
}
