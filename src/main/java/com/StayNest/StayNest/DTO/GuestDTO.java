package com.StayNest.StayNest.DTO;

import com.StayNest.StayNest.Entity.Enums.Gender;
import com.StayNest.StayNest.Entity.User;
import lombok.Data;

@Data
public class GuestDTO {
    private Long id;
    private User user;
    private String name;
    private Gender gender;
    private Integer age;
}
