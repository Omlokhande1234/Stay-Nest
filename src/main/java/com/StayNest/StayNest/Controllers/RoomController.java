package com.StayNest.StayNest.Controllers;

import com.StayNest.StayNest.DTO.HotelDTO;
import com.StayNest.StayNest.DTO.RoomDTO;
import com.StayNest.StayNest.Entity.Hotel;
import com.StayNest.StayNest.Entity.Room;
import com.StayNest.StayNest.Services.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/hotels/{hotelId}/rooms")
public class RoomController {
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomDTO> createNewRoom(@RequestBody RoomDTO roomDTO, @PathVariable Long hotelId){
        log.info("Attempting to create the new room");
        RoomDTO roomDTO1=roomService.createNewRoom(hotelId,roomDTO);
        return new ResponseEntity<>(roomDTO1, HttpStatus.CREATED);
    }
    @GetMapping("/{roomId}")
    public  ResponseEntity<RoomDTO> getRoom(@PathVariable Long roomId){
        log.info("Getting room for the hotel with id {}",roomId);
        RoomDTO roomDTO=roomService.getRoomById(roomId);
        return ResponseEntity.ok(roomDTO);
    }
    @GetMapping
    public ResponseEntity<List<RoomDTO>> getallRooms(@PathVariable Long hotelId){
        return  ResponseEntity.ok(roomService.getAllRoomsInHotel(hotelId));
    }
    @DeleteMapping("{roomId}")
    public ResponseEntity<Void> deleteRoomById(@PathVariable Long roomId){
        log.info("Deleting the room with Room Id {}",roomId);
        roomService.deleteRoomById(roomId);
        return ResponseEntity.noContent().build();
    }

}
