package com.StayNest.StayNest.Services;

import com.StayNest.StayNest.DTO.BookingDTO;
import com.StayNest.StayNest.DTO.BookingRequest;
import com.StayNest.StayNest.DTO.GuestDTO;
import com.StayNest.StayNest.Entity.*;
import com.StayNest.StayNest.Entity.Enums.BookingStatus;
import com.StayNest.StayNest.Exceptions.ResourceNotFoundException;
import com.StayNest.StayNest.Repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private final GuestRepository guestRepository;
//    private final

    @Override
    @Transactional
    public BookingDTO initializeBooking(BookingRequest bookingRequest) {
        log.info("Initializing the booking for the hotel : {},room with id:{} from {},{}",
                bookingRequest.getHotelId(),bookingRequest.getRoomId(),bookingRequest.getCheckInDate(),
                bookingRequest.getCheckOutDate()
        );
        Hotel hotel=hotelRepository.findById(bookingRequest.getHotelId())
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with the id: "+bookingRequest.getHotelId()));
        Room room=roomRepository.findById(bookingRequest.getRoomId())
                .orElseThrow(()->new ResourceNotFoundException("Room not found with the id:"+bookingRequest.getRoomId()));
        List<Inventory>inventoryList=inventoryRepository.findAndLockAvailableInventory(
                room.getId(),bookingRequest.getCheckInDate(),
                bookingRequest.getCheckOutDate(),bookingRequest.getRoomsCount()
        );
        //We have to also make check for the availability of the rooms(of given type by user) between the start and the end date
        long daysCount= ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate())+1;
        if (inventoryList.size()!=daysCount){
            throw new IllegalStateException("Rooms are not available for now");
        }

        //Reserve the rooms from the inventory if the rooms are available for the given dates
        for (Inventory inventory:inventoryList){
            inventory.setReservedCount( inventory.getBookedCount() + bookingRequest.getRoomsCount());
        }

        //now the inventory are locked
        inventoryRepository.saveAll(inventoryList);

        //Now the next task is to create the booking
        //Here we will temporarily require the user but for now we dont have any user created we will just here create a user
        //which dummy and remove it later

        User user=new User();//TODO:REMOVE THIS DUMMY USER LATER
        user.setId(1L);
        //TODO:CALCULATE THE DYNAMIC AMOUNT FOR THE BOOKING
        Booking booking=Booking.builder()
                .bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequest.getCheckInDate())
                .checkOutDate(bookingRequest.getCheckOutDate())
                .user(user)
                .roomsCount(bookingRequest.getRoomsCount())
                .amount(BigDecimal.TEN)
                .build();

        booking=bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDTO.class);
    }

    @Override
    @Transactional
    public BookingDTO addGuests(Long bookingId, List<GuestDTO> guestDTOList) {
        log.info("Adding the guests for booking with id: {} ",bookingId);
        Booking booking=bookingRepository.findById(bookingId).orElseThrow(()->new ResourceNotFoundException("Booking not found with id: "+bookingId));
        if (hasBookingExpired(booking)){
            throw new IllegalStateException("Booking Already expire");
        }
        if(booking.getBookingStatus()!=BookingStatus.RESERVED){
            throw new IllegalStateException("Booking is not under reserved state,cannot add guests");
        }
        for (GuestDTO guestDTO: guestDTOList){
            Guest guest=modelMapper.map(guestDTO,Guest.class);
            guest.setUser(getCurrentUser());
            guest=guestRepository.save(guest);
            booking.getGuests().add(guest);
        }
        booking.setBookingStatus(BookingStatus.GUEST_ADDED);
        booking=bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDTO.class);
    }
    public boolean hasBookingExpired(Booking booking){
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }
    public User getCurrentUser(){
        User user=new User();
        user.setId(1L);//TODO: REMOVE THE DUMMY USER
        return user;
    }

}
