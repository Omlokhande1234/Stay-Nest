package com.StayNest.StayNest.Services;


import com.StayNest.StayNest.DTO.HotelDTO;
import com.StayNest.StayNest.DTO.HotelSearchRequest;
import com.StayNest.StayNest.Entity.Hotel;
import com.StayNest.StayNest.Entity.Inventory;
import com.StayNest.StayNest.Entity.Room;
import com.StayNest.StayNest.Repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public void initializeRoomForaYear(Room room) {

        LocalDate today=LocalDate.now();
        LocalDate endDate=today.plusYears(1);

        for (;!today.isAfter(endDate);today=today.plusDays(1)){
            Inventory inventory=Inventory
                    .builder()
                    .hotel(room.getHotel())
                    .bookedCount(0)
                    .city(room.getHotel().getCity())
                    .date(today)
                    .price(room.getBasePrice())
                    .surgeFactor(BigDecimal.ONE)
                    .totalCount(room.getTotalCount())
                    .room(room)
                    .closed(false)
                    .build();
            inventoryRepository.save(inventory);
        }

    }

    @Override
    public void deleteFutureInventories(Room room) {
        log.info("Deleting the inventories of room with id: {} ",room.getId());
        inventoryRepository.deleteByRoom(room);
    }

    //We need to get all the hotels from the inventory having atleast 1 room between all dates between start and
    //end;
    //The query wee will do for it is:
//           1)startdate-date-Enddate
//           2)City
//           3)Availability:(totalcount-bookedCount)>=roomsCount
//           4)group the reponse by room(i.if user wants the 2 deluxe room then check in the inventory that
//             whether this rooms are available or not this way group different types of rooms and then provide result
//           5)Then also group by hotels and get the reponse by distinct hotels
    @Override
    public Page<HotelDTO> searchHotels(HotelSearchRequest hotelSearchRequest) {
        log.info("Searching hotels for {} city, from {} to {} ",hotelSearchRequest.getCity(),hotelSearchRequest.getStartDate(),
                hotelSearchRequest.getEndDate());
        Pageable pageable= PageRequest.of(hotelSearchRequest.getPage(),hotelSearchRequest.getSize());
        long dateCount= ChronoUnit.DAYS.between(hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate())+1;
        Page<Hotel> hotelPage=inventoryRepository.findHotelsWithAvailableInventory(hotelSearchRequest.getCity(),
                hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate(),hotelSearchRequest.getRoomsCount()
        ,dateCount,pageable);
        return hotelPage.map((element)->modelMapper.map(element,HotelDTO.class));

    }

}
