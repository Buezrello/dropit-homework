package com.example.dropit.dao;

import com.example.dropit.dto.Holiday;
import com.example.dropit.dto.TimeSlot;

import java.util.List;

public interface MongoGeneric {

    void initTimeSlots(List<TimeSlot> timeSlots);
    void initHolidays(List<Holiday> holidays);
}
