package org.example.roomservice.controller;

import org.example.roomservice.dto.RoomRequestDTO;
import org.example.roomservice.dto.RoomResponseDTO;
import org.example.roomservice.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<RoomResponseDTO>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @PostMapping
    public ResponseEntity<RoomResponseDTO> createRoom(@RequestBody RoomRequestDTO requestDTO) {
        RoomResponseDTO createdRoom = roomService.createRoom(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
    }
}
