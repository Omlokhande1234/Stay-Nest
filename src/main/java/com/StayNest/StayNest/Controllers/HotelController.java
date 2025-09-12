package com.StayNest.StayNest.Controllers;

import com.StayNest.StayNest.DTO.HotelDTO;
import com.StayNest.StayNest.Services.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.TreeSet;

@Controller
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
@Slf4j
public class HotelController {
    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelDTO> createNewHotel(@RequestBody HotelDTO hotelDTO){
        log.info("Attempting to create a new hotel{}",hotelDTO.getName());
        HotelDTO hotelDTO1=hotelService.createNewHotel(hotelDTO);
        return new ResponseEntity<>(hotelDTO1, HttpStatus.CREATED);

    }
    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable Long hotelId){
        HotelDTO hotelDTO=hotelService.getHotelById(hotelId);
        return ResponseEntity.ok(hotelDTO);
    }
    @PutMapping("/{hotelId}")
    public ResponseEntity<HotelDTO> updateHotelById(@PathVariable Long hotelId,@RequestBody HotelDTO hotelDTO){
        HotelDTO hotelDTO1=hotelService.updateHotelById(hotelId, hotelDTO);
        return ResponseEntity.ok(hotelDTO1);
    }
    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Void> deleteHotelById(@PathVariable Long hotelId){
        hotelService.deleteHotelById(hotelId);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{hotelId}")
    public ResponseEntity<Void> activeHotel(@PathVariable Long hotelId){
        hotelService.activeHotel(hotelId);
        return ResponseEntity.noContent().build();
    }

}
