package com.StayNest.StayNest.Repository;

import com.StayNest.StayNest.Entity.Hotel;
import com.StayNest.StayNest.Entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {
}
