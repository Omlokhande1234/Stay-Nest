package com.StayNest.StayNest.Services;

import com.StayNest.StayNest.Entity.Room;

public interface InventoryService {
    void initializeRoomForaYear (Room room);

    void deleteFutureInventories(Room room);


}
