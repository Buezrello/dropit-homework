package com.example.dropit.repositories;

import com.example.dropit.dto.Holiday;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface HolidayRepository extends MongoRepository<Holiday, String> {
    List<Holiday> findByDate(LocalDate date);
}
