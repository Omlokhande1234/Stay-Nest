package com.StayNest.StayNest.Services;

import com.StayNest.StayNest.DTO.BookingDTO;
import com.StayNest.StayNest.DTO.BookingRequest;

public interface BookingService {
    BookingDTO initializeBooking(BookingRequest bookingRequest);
}
