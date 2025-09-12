package com.StayNest.StayNest.Services;


import com.StayNest.StayNest.DTO.HotelDTO;
import com.StayNest.StayNest.Entity.Hotel;
import com.StayNest.StayNest.Exceptions.ResoureNotFoundException;
import com.StayNest.StayNest.Repository.HotelRepository;
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

    @Override
    public void deleteHotelById(Long id) {
        boolean exists=hotelRepository.existsById(id);
        if (!exists) throw new ResoureNotFoundException("Hotel not found with ID: "+id);
        hotelRepository.deleteById(id);
        //TODO: delete the future inventories for this hotel
    }
}
