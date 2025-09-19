package com.StayNest.StayNest.Services;
import com.StayNest.StayNest.DTO.HotelDTO;
import com.StayNest.StayNest.Entity.Hotel;
import com.StayNest.StayNest.Entity.Room;
import com.StayNest.StayNest.Exceptions.ResoureNotFoundException;
import com.StayNest.StayNest.Repository.HotelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;

    @Override
    public HotelDTO createNewHotel(HotelDTO hotelDTO) {
       log.info("Creating a new Hotel with name: {}",hotelDTO.getName());
       Hotel hotel=modelMapper.map(hotelDTO,Hotel.class);
       hotel.setActive(false);
       hotel=hotelRepository.save(hotel);
       return modelMapper.map(hotel,HotelDTO.class);
    }

    @Override
    public HotelDTO getHotelById(Long id) {
        log.info("Getting the hotel by id{}",id);
        Hotel hotel=hotelRepository
                .findById(id)
                .orElseThrow(()->new ResoureNotFoundException("Hotel not found with the given id"));
        return modelMapper.map(hotel,HotelDTO.class);
    }

    @Override
    public HotelDTO updateHotelById(Long id,HotelDTO hotelDTO) {
        log.info("Updating the hotel by id{}",id);
        Hotel hotel=hotelRepository
                .findById(id)
                .orElseThrow(()->new ResoureNotFoundException("Hotel not found with the given id: "+id));
        modelMapper.map(hotelDTO,hotel);
        hotel.setId(id);
        hotel=hotelRepository.save(hotel);
        return modelMapper.map(hotel,HotelDTO.class);
    }

    @Transactional
    @Override
    public void deleteHotelById(Long id) {
        Hotel hotel=hotelRepository.findById(id)
                        .orElseThrow(()->new ResoureNotFoundException("Hotel not found with the id "+id));
        hotelRepository.deleteById(id);
        // delete the future inventories for this hotel
        for (Room room:hotel.getRooms()){
            inventoryService.deleteFutureInventories(room);
        }
    }

    @Transactional
    @Override
    public void activeHotel(Long id) {
        log.info("Activating the hotel with the given id {} ",id);
        Hotel hotel=hotelRepository
                .findById(id)
                .orElseThrow(()->new ResoureNotFoundException("Hotel not found with id "+id));
        hotel.setActive(true);

        //Creating the inventory for this newly created hotel
        for (Room room:hotel.getRooms()){
            inventoryService.initializeRoomForaYear(room);
        }

    }
}
