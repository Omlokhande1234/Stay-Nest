package com.StayNest.StayNest.Repository;

import com.StayNest.StayNest.Entity.Hotel;
import com.StayNest.StayNest.Entity.Inventory;
import com.StayNest.StayNest.Entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    void deleteByRoom( Room room);

    @Query("""
            SELECT DISTINCT i.hotel
            FROM Inventory i
            WHERE i.city=:city
                  AND i.date BETWEEN :startDate And :endDate
                  AND i.closed=false 
                  AND (i.totalCount-i.bookedCount)>=:roomsCount
            GROUP BY i.hotel,i.room
            HAVING COUNT(i.date)=:dateCount
  
    """)
    //By this quesry we would be only able to see the hotels having the inventory for the rooms for certain number
    //of days
    //Also we selecting only the distinct hotels as if there are two types rooms available with the given inventory
    //for the same hotel then same hotel will be called for two times which will create the problems in future
    Page<Hotel> findHotelsWithAvailableInventory(
            @Param("city") String city,
            @Param("startDate")LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomsCount") Integer roomsCount,

            //These dateCount is the number of days that we want to stay in the hotel(room)
            //These also helps to select the room having the inventory for 2 days or to select the room that
            //are available for at least two days
            @Param("dateCount") Long dateCount,

            Pageable pageable //If the "Pageable is bot passed then we will not get the paginated reponse"
            );

}
