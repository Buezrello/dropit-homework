package com.example.dropit.repositories;

import com.example.dropit.dto.TimeSlot;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TimeSlotRepository extends MongoRepository<TimeSlot, String> {
    List<TimeSlot> findByStartTimeBeforeAndEndTimeAfter(LocalDateTime startTime, LocalDateTime endTime);
    List<TimeSlot> findByStartTimeBeforeAndEndTimeAfterAndSupportedPostcodesContains
            (LocalDateTime startTime, LocalDateTime endTime, String postCode);
    List<TimeSlot> findByStartTimeAfterAndEndTimeBefore(LocalDateTime dayStart, LocalDateTime dayEnd);

}
