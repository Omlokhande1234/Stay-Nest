package com.StayNest.StayNest.DTO;

import com.StayNest.StayNest.Entity.HotelcontactInfo;
import lombok.Data;

@Data
public class HotelDTO {

    private Long id;
    private String name;
    private String city;
    private String[] photos;
    private String[] amenities;
    private HotelcontactInfo contactInfo;
    private boolean active;

//    This is the other way to map the relation between the rooms and hotel
//    @OneToMany(mappedBy = "hotel",fetch = FetchType.LAZY)
//    private List<Room> rooms;
}
