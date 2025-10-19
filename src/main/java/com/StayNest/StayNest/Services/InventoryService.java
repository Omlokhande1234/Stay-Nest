package com.StayNest.StayNest.Services;

import com.StayNest.StayNest.DTO.HotelDTO;
import com.StayNest.StayNest.DTO.HotelPriceDTO;
import com.StayNest.StayNest.DTO.HotelSearchRequest;
import com.StayNest.StayNest.Entity.Room;
import org.springframework.data.domain.Page;
public interface InventoryService {
    void initializeRoomForaYear (Room room);

    void deleteFutureInventories(Room room);


    Page<HotelPriceDTO> searchHotels(HotelSearchRequest hotelSearchRequest);
}
