package com.StayNest.StayNest.Services;

import com.StayNest.StayNest.DTO.BookingDTO;
import com.StayNest.StayNest.DTO.BookingRequest;
import com.StayNest.StayNest.DTO.GuestDTO;

import java.util.List;

public interface BookingService {
    BookingDTO initializeBooking(BookingRequest bookingRequest);
    BookingDTO addGuests(Long bookingId, List<GuestDTO> guestDTOList);
}
