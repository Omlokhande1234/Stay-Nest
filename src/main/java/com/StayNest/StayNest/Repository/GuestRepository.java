package com.StayNest.StayNest.Repository;

import com.StayNest.StayNest.Entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest,Long> {

}
