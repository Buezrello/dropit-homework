package com.example.dropit.repositories;

import com.example.dropit.dto.Holiday;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HolidayRepository extends MongoRepository<Holiday, String> {
}
