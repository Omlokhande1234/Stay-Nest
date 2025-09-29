package com.StayNest.StayNest.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HotellnfoDTO {
    private HotelDTO hotel;
    private List<RoomDTO> rooms;

}
