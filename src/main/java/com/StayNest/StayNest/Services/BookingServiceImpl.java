package com.StayNest.StayNest.Services;

import com.StayNest.StayNest.DTO.BookingDTO;
import com.StayNest.StayNest.DTO.BookingRequest;
import com.StayNest.StayNest.Entity.*;
import com.StayNest.StayNest.Entity.Enums.BookingStatus;
import com.StayNest.StayNest.Exceptions.ResoureNotFoundException;
import com.StayNest.StayNest.Repository.BookingRepository;
import com.StayNest.StayNest.Repository.HotelRepository;
import com.StayNest.StayNest.Repository.InventoryRepository;
import com.StayNest.StayNest.Repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public BookingDTO initializeBooking(BookingRequest bookingRequest) {
        log.info("Initializing the booking for the hotel : {},room with id:{} from {},{}",
                bookingRequest.getHotelId(),bookingRequest.getRoomId(),bookingRequest.getCheckInDate(),
                bookingRequest.getCheckOutDate()
        );
        Hotel hotel=hotelRepository.findById(bookingRequest.getHotelId())
                .orElseThrow(()-> new ResoureNotFoundException("Hotel not found with the id: "+bookingRequest.getHotelId()));
        Room room=roomRepository.findById(bookingRequest.getRoomId())
                .orElseThrow(()->new ResoureNotFoundException("Room not found with the id:"+bookingRequest.getRoomId()));
        List<Inventory>inventoryList=inventoryRepository.findAndLockAvailableInventory(
                room.getId(),bookingRequest.getCheckInDate(),
                bookingRequest.getCheckOutDate(),bookingRequest.getRoomCount()
        );
        //We have to also make check for the availability of the rooms(of given type by user) between the start and the end date
        long daysCount= ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate())+1;
        if (inventoryList.size()!=daysCount){
            throw new IllegalStateException("Rooms are not available for now");
        }

        //Reserve the rooms from the inventory if the rooms are available for the given dates
        for (Inventory inventory:inventoryList){
            inventory.setBookedCount(inventory.getBookedCount() + bookingRequest.getRoomCount());
        }

        //now the inventory are locked
        inventoryRepository.saveAll(inventoryList);

        //Now the next task is to create the booking
        //Here we will temporarily require the user but for now we dont have any user created we will just here create a user
        //which dummy and remove it later

        User user=new User();//TODO:REMOVE THIS DUMMY USER LATER
        user.setId(1l);
        //TODO:CALCULATE THE DYNAMIC AMOUNT FOR THE BOOKING
        Booking booking=Booking.builder()
                .bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequest.getCheckInDate())
                .checkOutDate(bookingRequest.getCheckOutDate())
                .user(user)
                .amount(BigDecimal.TEN)
                .build();

        booking=bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDTO.class);
    }
}
