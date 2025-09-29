package com.StayNest.StayNest.Repository;

import com.StayNest.StayNest.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
}
