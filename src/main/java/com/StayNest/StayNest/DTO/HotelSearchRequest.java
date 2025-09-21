package com.StayNest.StayNest.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HotelSearchRequest {
    private String city;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer roomsCount;


    //Here if no page and size is provided then this will be taken by default
    private Integer page=0;
    private Integer size=10;
}
