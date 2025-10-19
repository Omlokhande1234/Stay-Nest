package com.StayNest.StayNest.Services;

import com.StayNest.StayNest.Entity.Hotel;
import com.StayNest.StayNest.Entity.HotelMinPrice;
import com.StayNest.StayNest.Entity.Inventory;
import com.StayNest.StayNest.Repository.HotelMinPriceRepository;
import com.StayNest.StayNest.Repository.HotelRepository;
import com.StayNest.StayNest.Repository.InventoryRepository;
import com.StayNest.StayNest.strategy.PricingService;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PricingUpdateService {
    private final HotelRepository hotelRepository;
    private final InventoryRepository inventoryRepository;
    private final HotelMinPriceRepository hotelMinPriceRepository;
    private final PricingService pricingService;

//    @Scheduled(cron = "0 0 * * * *")
    @Scheduled(cron = "*/5 * * * * *") //These means that every hour at 0 minutes and 0 seconds run this method
    public void updatePrice(){
        int page=0;
        int batchsize=100;

        while (true){
            Page<Hotel> hotelPage=hotelRepository.findAll(PageRequest.of(page,batchsize));
            if (hotelPage.isEmpty()){
                break;
            }
            hotelPage.getContent().forEach(hotel->updateHotelPrices(hotel));
            page++;
        }
    }
    private void updateHotelPrices(Hotel hotel){
        log.info("Updating the hotel prices for hotel ID: {}" ,hotel.getId());
        LocalDate startDate=LocalDate.now();
        LocalDate endDate=LocalDate.now().plusYears(1);
        List<Inventory> inventoryList=inventoryRepository.findByHotelAndDateBetween(hotel,startDate,endDate);
        updateHotelMinPrice(hotel,inventoryList,startDate,endDate);
        updateInventoryPrices(inventoryList);

    }

    private void updateHotelMinPrice(Hotel hotel, List<Inventory> inventoryList, LocalDate startDate, LocalDate endDate) {
        //Compute the minimum price per day for the hotel
        Map<LocalDate,BigDecimal> dailyMinPrices=inventoryList.stream()
                .collect(Collectors.groupingBy(
                        Inventory::getDate,
                        Collectors.mapping(Inventory::getPrice,Collectors.minBy(Comparator.naturalOrder()))
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,e->e.getValue().orElse(BigDecimal.ZERO)));

        //Prepare HotelPrice entities in bulk
        List<HotelMinPrice> hotelPrices=new ArrayList<>();
        dailyMinPrices.forEach((date,price)->{
            HotelMinPrice hotelPrice=hotelMinPriceRepository.findByHotelAndDate(hotel, date)
                    .orElse(new HotelMinPrice(hotel,date));
            hotelPrice.setPrice(price);
            hotelPrices.add(hotelPrice);
        });

        //Save all the hotelPrice entity in bulk
        hotelMinPriceRepository.saveAll(hotelPrices);
    }

    private void updateInventoryPrices(List<Inventory> inventoryList){
        inventoryList.forEach(inventory -> {
            BigDecimal dynamicPrice= pricingService.calculateDynamicPricing(inventory);
            inventory.setPrice(dynamicPrice);
        });
        inventoryRepository.saveAll(inventoryList);
    }


}
