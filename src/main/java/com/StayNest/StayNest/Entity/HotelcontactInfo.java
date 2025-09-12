package com.StayNest.StayNest.Entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class HotelcontactInfo {
    private String completeAddress;
    private String Location;
    private String email;
    private Long Phoneno;
}
