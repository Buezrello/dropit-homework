package com.example.dropit.listener;

import com.example.dropit.dao.MongoGeneric;
import com.example.dropit.dto.Holiday;
import com.example.dropit.dto.TimeSlot;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@Data
@Slf4j
public class EventListenerBean {

    private final MongoGeneric mongoGeneric;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) throws IOException {

        log.info("Initialization: reading holiday and timeslot from resources.");

        File holidays = ResourceUtils.getFile("classpath:holidays.json");
        File timeslots = ResourceUtils.getFile("classpath:timeslots.json");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
//        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

        List<Holiday> holidayList = objectMapper.readValue(holidays, new TypeReference<>() {
        });

        List<TimeSlot> timeslotList = objectMapper.readValue(timeslots, new TypeReference<>() {
        });

        mongoGeneric.initHolidays(holidayList);
        mongoGeneric.initTimeSlots(timeslotList);
    }
}
