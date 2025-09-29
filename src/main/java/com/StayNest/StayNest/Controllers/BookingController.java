package com.StayNest.StayNest.Controllers;

import com.StayNest.StayNest.DTO.BookingDTO;
import com.StayNest.StayNest.DTO.BookingRequest;
import com.StayNest.StayNest.Services.BookingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")

public class BookingController {
    private final BookingService bookingService;
    //In order to initialize the booking it would also need the booking request so we would create the bookingRequestDto
    public ResponseEntity<BookingDTO> initializeBooking(@RequestBody BookingRequest bookingRequest){
        return ResponseEntity.ok(bookingService.initializeBooking(bookingRequest));


    }

}
