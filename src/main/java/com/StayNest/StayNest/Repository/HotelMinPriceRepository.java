package com.StayNest.StayNest.Repository;

import com.StayNest.StayNest.DTO.HotelPriceDTO;
import com.StayNest.StayNest.Entity.Hotel;
import com.StayNest.StayNest.Entity.HotelMinPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.Optional;

public interface HotelMinPriceRepository extends JpaRepository<HotelMinPrice,Long> {


    @Query("""
            SELECT new com.StayNest.StayNest.DTO.HotelPriceDTO(i.hotel,AVG(i.price))
            FROM HotelMinPrice i
            WHERE i.hotel.city=:city
                  AND i.date BETWEEN :startDate And :endDate
                  AND i.hotel.active=true 
            GROUP BY i.hotel
           
  
    """)
    Page<HotelPriceDTO> findHotelsWithAvailableInventory(
            @Param("city") String city,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomsCount") Integer roomsCount,
            @Param("dateCount") Long dateCount,

            Pageable pageable
    );


    Optional<HotelMinPrice> findByHotelAndDate(Hotel hotel, LocalDate date);
}
