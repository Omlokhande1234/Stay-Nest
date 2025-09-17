package com.StayNest.StayNest.Repository;

import com.StayNest.StayNest.Entity.Hotel;
import com.StayNest.StayNest.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {

}
