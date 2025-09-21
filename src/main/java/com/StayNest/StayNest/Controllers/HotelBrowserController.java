package com.StayNest.StayNest.Controllers;

import com.StayNest.StayNest.DTO.HotelDTO;
import com.StayNest.StayNest.DTO.HotelSearchRequest;
import com.StayNest.StayNest.Services.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/hotels")
public class HotelBrowserController {
    private final InventoryService inventoryService;

    //We will the inventory service in order search the hotels
    @GetMapping("/search")
    public ResponseEntity<Page<HotelDTO>> searchHotel(@RequestBody HotelSearchRequest hotelSearchRequest){
        Page<HotelDTO> page=inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(page);

    }

}
