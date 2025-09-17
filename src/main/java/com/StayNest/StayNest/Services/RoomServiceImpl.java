package com.StayNest.StayNest.Services;

import com.StayNest.StayNest.DTO.RoomDTO;
import com.StayNest.StayNest.Entity.Hotel;
import com.StayNest.StayNest.Entity.Room;
import com.StayNest.StayNest.Exceptions.ResoureNotFoundException;
import com.StayNest.StayNest.Repository.HotelRepository;
import com.StayNest.StayNest.Repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;
    private final HotelRepository hotelRepository;

    @Override
    public RoomDTO createNewRoom(Long hotelId, RoomDTO roomDTO) {
        log.info("Creating the new room {}",roomDTO.getId());
        //Before creating the room check whether the hotel for which the room is created exists or not
        Hotel hotel=hotelRepository
                .findById(hotelId)
                .orElseThrow(()->new ResoureNotFoundException("Hotel not found with the id: "+hotelId));
        Room room=modelMapper.map(roomDTO,Room.class);
        room.setHotel(hotel);
        room= roomRepository.save(room);

        //TODO:CREATE INVENTORY AS SOON AS THE ROOM IS CREATED IF THE HOTEL IS ACTIVE
        return modelMapper.map(room,RoomDTO.class);
    }

    @Override
    public List<RoomDTO> getAllRoomsInHotel(Long hotelId) {
        log.info("Getting all rooms for the hotel{}",hotelId);
        Hotel hotel=hotelRepository
                .findById(hotelId)
                .orElseThrow(()->new ResoureNotFoundException("Hotel not found with ID: "+hotelId));
        return hotel.getRooms().stream()
                .map((element)->modelMapper.map(element,RoomDTO.class)).collect(Collectors.toList());
    }

    @Override
    public RoomDTO getRoomById(Long roomId) {
        log.info("Getting the hotel by id {}",roomId);
        Room room=roomRepository
                .findById(roomId)
                .orElseThrow(()->new ResoureNotFoundException("Room not found with the id: " +roomId));
        return modelMapper.map(room,RoomDTO.class);

    }

    @Override
    public void deleteRoomById(Long roomId) {
        log.info("Deleting the hotel by id {}",roomId);
        boolean exists=roomRepository.existsById(roomId);
        if (!exists){
            throw new ResoureNotFoundException("Room not found  with Id" +roomId);
        }
        roomRepository.deleteById(roomId);
        //TODO: DELETE ALL FUTURE INVENTORY FOR THIS ROW

    }
}
