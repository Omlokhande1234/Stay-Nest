package com.StayNest.StayNest.DTO;

import com.StayNest.StayNest.Entity.Enums.BookingStatus;
import com.StayNest.StayNest.Entity.Guest;
import com.StayNest.StayNest.Entity.Hotel;
import com.StayNest.StayNest.Entity.Room;
import com.StayNest.StayNest.Entity.User;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDTO {
    private Long id;
    private Hotel hotel;
    private Room room;
    private User user;
    private Integer roomsCount;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BookingStatus bookingStatus;
    private Set<Guest> guests;


}
