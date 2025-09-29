package com.StayNest.StayNest.DTO;

import com.StayNest.StayNest.Entity.Hotel;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BookingRequest {
    private Long hotelId;
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer roomCount;

}
