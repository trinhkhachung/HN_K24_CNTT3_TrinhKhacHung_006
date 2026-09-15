package org.example.roomservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequestDTO {
    private String roomNumber;
    private Long roomTypeId;
    private String status;
    private Double price;
}
