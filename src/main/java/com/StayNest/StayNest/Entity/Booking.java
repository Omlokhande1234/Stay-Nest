package com.StayNest.StayNest.Entity;


import com.StayNest.StayNest.Entity.Enums.BookingStatus;
import com.StayNest.StayNest.Entity.Hotel;
import com.StayNest.StayNest.Entity.Payment;
import com.StayNest.StayNest.Entity.Room;
import com.StayNest.StayNest.Entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(nullable = false)
    private Integer roomsCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate checkInDate;
    @Column(nullable = false)
    private LocalDate checkOutDate;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;



    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus bookingStatus;

    @ManyToMany
    @JoinTable(
            name = "booking_guest",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "guest_id")
    )
    private Set<Guest> guests;

    @Column(nullable = false,precision = 10,scale = 2)
    private BigDecimal amount;




}
