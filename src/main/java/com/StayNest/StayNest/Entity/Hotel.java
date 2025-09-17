package com.StayNest.StayNest.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    @Column(columnDefinition = "TEXT[]")
    private String[] photos;

    @Column(columnDefinition = "TEXT[]")
    private String[] amenities;

    @Embedded
    private HotelcontactInfo contactInfo;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private boolean active;

    //Here one user/owner can have the multiple hotels
    @ManyToOne
    private User owner;

    @OneToMany(mappedBy = "hotel")
    @JsonManagedReference
    private List<Room> rooms;
//    This is the other way to map the relation between the rooms and hotel
//    @OneToMany(mappedBy = "hotel",fetch = FetchType.LAZY)
//    private List<Room> rooms;

}
