package com.StayNest.StayNest.Repository;

import com.StayNest.StayNest.Entity.Hotel;
import com.StayNest.StayNest.Entity.Inventory;
import com.StayNest.StayNest.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    void deleteByDateGreaterThanEqualAndRoom(LocalDate date, Room room);

}
