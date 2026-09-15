package org.example.roomservice.service;

import feign.FeignException;
import org.example.roomservice.client.RoomTypeClient;
import org.example.roomservice.dto.RoomRequestDTO;
import org.example.roomservice.dto.RoomResponseDTO;
import org.example.roomservice.dto.RoomTypeDTO;
import org.example.roomservice.entity.Room;
import org.example.roomservice.exception.InvalidRoomTypeException;
import org.example.roomservice.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomTypeClient roomTypeClient;

    public RoomService(RoomRepository roomRepository, RoomTypeClient roomTypeClient) {
        this.roomRepository = roomRepository;
        this.roomTypeClient = roomTypeClient;
    }

    public RoomResponseDTO createRoom(RoomRequestDTO requestDTO) {
        if (requestDTO.getRoomTypeId() == null) {
            throw new InvalidRoomTypeException("roomTypeId is required");
        }

        RoomTypeDTO roomType;
        try {
            roomType = roomTypeClient.getRoomTypeById(requestDTO.getRoomTypeId());
            if (roomType == null) {
                throw new InvalidRoomTypeException("RoomType with ID " + requestDTO.getRoomTypeId() + " does not exist");
            }
        } catch (FeignException.NotFound e) {
            throw new InvalidRoomTypeException("RoomType with ID " + requestDTO.getRoomTypeId() + " does not exist");
        }

        Room room = new Room();
        room.setRoomNumber(requestDTO.getRoomNumber());
        room.setRoomTypeId(requestDTO.getRoomTypeId());
        room.setStatus(requestDTO.getStatus() != null ? requestDTO.getStatus() : "AVAILABLE");
        room.setPrice(requestDTO.getPrice() != null ? requestDTO.getPrice() : roomType.getPrice());

        Room savedRoom = roomRepository.save(room);

        return new RoomResponseDTO(
                savedRoom.getId(),
                savedRoom.getRoomNumber(),
                savedRoom.getRoomTypeId(),
                savedRoom.getStatus(),
                savedRoom.getPrice(),
                roomType
        );
    }

    public List<RoomResponseDTO> getAllRooms() {
        return roomRepository.findAll().stream().map(room -> {
            RoomTypeDTO roomType = null;
            try {
                roomType = roomTypeClient.getRoomTypeById(room.getRoomTypeId());
            } catch (Exception e) {
                // Log or ignore if service is temporarily unavailable
            }
            return new RoomResponseDTO(
                    room.getId(),
                    room.getRoomNumber(),
                    room.getRoomTypeId(),
                    room.getStatus(),
                    room.getPrice(),
                    roomType
            );
        }).collect(Collectors.toList());
    }
}
