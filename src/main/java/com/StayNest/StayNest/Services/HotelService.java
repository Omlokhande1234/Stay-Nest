package com.StayNest.StayNest.Services;

import com.StayNest.StayNest.DTO.HotelDTO;
import com.StayNest.StayNest.DTO.HotellnfoDTO;

public interface HotelService {
    HotelDTO createNewHotel(HotelDTO hotelDTO);

    HotelDTO getHotelById(Long id);

    HotelDTO updateHotelById(Long id,HotelDTO hotelDTO);

    void deleteHotelById(Long id);

    void activeHotel(Long id);

    HotellnfoDTO getHotelInfoById(Long hotelId);
}
