package org.example.roomtypeservice.config;

import org.example.roomtypeservice.entity.RoomType;
import org.example.roomtypeservice.repository.RoomTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoomTypeRepository roomTypeRepository;

    public DataInitializer(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roomTypeRepository.count() == 0) {
            roomTypeRepository.save(new RoomType(null, "Standard", 500000.0, "Standard room with single bed"));
            roomTypeRepository.save(new RoomType(null, "Deluxe", 800000.0, "Deluxe room with double bed and balcony"));
            roomTypeRepository.save(new RoomType(null, "VIP", 1500000.0, "VIP suite with luxury amenities"));
        }
    }
}
