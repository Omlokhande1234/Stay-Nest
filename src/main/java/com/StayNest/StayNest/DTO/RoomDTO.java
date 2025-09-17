package com.StayNest.StayNest.DTO;

import com.StayNest.StayNest.Entity.Hotel;
import com.StayNest.StayNest.Entity.HotelcontactInfo;
import lombok.Data;


import java.math.BigDecimal;

@Data
public class RoomDTO {
    private Long id;
    private String type;
    private BigDecimal basePrice;
    private String[] photos;
    private String[] amenities;
    private HotelcontactInfo contactInfo;
    private int totalCount;
    private int capacity;

}
