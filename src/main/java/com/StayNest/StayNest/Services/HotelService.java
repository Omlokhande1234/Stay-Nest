package com.StayNest.StayNest.Services;

import com.StayNest.StayNest.DTO.HotelDTO;

public interface HotelService {
    HotelDTO createNewHotel(HotelDTO hotelDTO);

    HotelDTO getHotelById(Long id);

    HotelDTO updateHotelById(Long id,HotelDTO hotelDTO);

    void deleteHotelById(Long id);

}
