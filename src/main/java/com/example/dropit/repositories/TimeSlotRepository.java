package com.example.dropit.repositories;

import com.example.dropit.dto.TimeSlot;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TimeSlotRepository extends MongoRepository<TimeSlot, String> {
}
