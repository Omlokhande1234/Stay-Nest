package com.StayNest.StayNest.Entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class HotelcontactInfo {
    private String completeAddress;
    private String Location;
    private String email;
    private Long Phoneno;
}
