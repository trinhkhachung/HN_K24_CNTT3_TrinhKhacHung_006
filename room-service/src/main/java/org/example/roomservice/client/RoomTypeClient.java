package org.example.roomservice.client;

import org.example.roomservice.dto.RoomTypeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "roomtype-service")
public interface RoomTypeClient {

    @GetMapping("/api/roomtypes/{id}")
    RoomTypeDTO getRoomTypeById(@PathVariable("id") Long id);
}
