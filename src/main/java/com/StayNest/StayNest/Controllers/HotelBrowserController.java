package com.StayNest.StayNest.Controllers;

import com.StayNest.StayNest.DTO.HotelDTO;
import com.StayNest.StayNest.DTO.HotelPriceDTO;
import com.StayNest.StayNest.DTO.HotelSearchRequest;
import com.StayNest.StayNest.DTO.HotellnfoDTO;
import com.StayNest.StayNest.Services.HotelService;
import com.StayNest.StayNest.Services.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/hotels")
public class HotelBrowserController {
    private final InventoryService inventoryService;
    private final HotelService hotelService;

    //We will the inventory service in order search the hotels
    @GetMapping("/search")
    public ResponseEntity<Page<HotelPriceDTO>> searchHotel(@RequestBody HotelSearchRequest hotelSearchRequest){
        Page<HotelPriceDTO> page=inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(page);
    }
    @GetMapping("/{hotelId}/info")
    public ResponseEntity<HotellnfoDTO> getHotelInfo(@PathVariable Long hotelId){
        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }

}
