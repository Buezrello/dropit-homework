package com.example.dropit.dao.impl;

import com.example.dropit.dao.MongoGeneric;
import com.example.dropit.dto.Holiday;
import com.example.dropit.dto.TimeSlot;
import com.example.dropit.repositories.HolidayRepository;
import com.example.dropit.repositories.TimeSlotRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Data
@Slf4j
public class MongoGenericImpl implements MongoGeneric {

    private final TimeSlotRepository timeSlotRepository;
    private final HolidayRepository holidayRepository;

    @Override
    public void initTimeSlots(List<TimeSlot> timeSlots) {
        log.info("Drop old timeslots");
        timeSlotRepository.deleteAll();
        log.info("Save new timeslots");
        timeSlotRepository.saveAll(timeSlots);
        log.info("Timeslots saved");
    }

    @Override
    public void initHolidays(List<Holiday> holidays) {
        log.info("Drop old holidays");
        holidayRepository.deleteAll();
        log.info("Save new holidays");
        holidayRepository.saveAll(holidays);
        log.info("Holidays saved");
    }
}
