package com.StayNest.StayNest.strategy;

import com.StayNest.StayNest.Entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


public interface Pricing_Strategy {

    BigDecimal calculatePrice(Inventory inventory);
}
