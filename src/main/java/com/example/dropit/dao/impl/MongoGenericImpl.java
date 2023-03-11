package com.example.dropit.dao.impl;

import com.example.dropit.dao.MongoGeneric;
import com.example.dropit.dto.DeliveryDto;
import com.example.dropit.dto.Holiday;
import com.example.dropit.dto.TimeSlot;
import com.example.dropit.enums.DeliveryStatusEnum;
import com.example.dropit.exception.MongoDbNotFoundException;
import com.example.dropit.repositories.DeliveryRepository;
import com.example.dropit.repositories.HolidayRepository;
import com.example.dropit.repositories.TimeSlotRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Data
@Slf4j
public class MongoGenericImpl implements MongoGeneric {

    private final TimeSlotRepository timeSlotRepository;
    private final HolidayRepository holidayRepository;
    private final DeliveryRepository deliveryRepository;

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

    @Override
    public void createNewDelivery(String userId, String timeSlotId, DeliveryStatusEnum deliveryStatusEnum) {
        log.info("Add a new delivery, userId {}, timeSlotId {}", userId, timeSlotId);
        deliveryRepository.save(new DeliveryDto(userId, timeSlotId, deliveryStatusEnum));
    }

    @Override
    public DeliveryDto findDeliveryById(String deliveryId) {
        log.info("Find Delivery by id {}", deliveryId);
        Optional<DeliveryDto> byId = deliveryRepository.findById(deliveryId);
        return byId.orElseThrow(() -> new MongoDbNotFoundException(String.format("Delivery %s not found", deliveryId)));
    }

    @Override
    public void updateDelivery(DeliveryDto deliveryDto) {
        log.info("Update a delivery {}", deliveryDto.getId());
        deliveryRepository.save(deliveryDto);
    }

    @Override
    public List<TimeSlot> findTimeSlotByTime(LocalDateTime dateTime) {
        log.info("Find time slot for {}", dateTime);
        List<TimeSlot> result = timeSlotRepository.findByStartTimeBeforeAndEndTimeAfter(dateTime, dateTime);
        if (result.isEmpty()) {
            log.error("Time slot for {} not found", dateTime);
        }
        return  result;
    }

    @Override
    public List<TimeSlot> findTimeSlotByTimeAndPostCode(LocalDateTime dateTime, String postcode) {
        log.info("Find time slot for {} and postcode {}", dateTime, postcode);
        List<TimeSlot> result = timeSlotRepository
                .findByStartTimeBeforeAndEndTimeAfterAndSupportedPostcodesContains(dateTime, dateTime, postcode);
        if (result.isEmpty()) {
//            log.error("Time slot for {} and postcode {} not found", dateTime, postcode);
            throw new MongoDbNotFoundException(String.format("Time slot for %s and postcode %s not found", dateTime, postcode));
        }
        return  result;
    }

    @Override
    public List<DeliveryDto> findDeliveryByTimeSlotAndStatus(String timeSlotId, DeliveryStatusEnum deliveryStatusEnum) {
        log.info("Find delivery by time slot ID {} and status {}", timeSlotId, deliveryStatusEnum);
        List<DeliveryDto> result = deliveryRepository.findByTimeSlotIdAndDeliveryStatusEnum(timeSlotId, deliveryStatusEnum);
        if (result.isEmpty()) {
            log.error("Delivery by time slot ID {} and status {} not found", timeSlotId, deliveryStatusEnum);
        }
        return  result;
    }

    @Override
    public List<DeliveryDto> findDeliveryByStatus(DeliveryStatusEnum deliveryStatusEnum) {
        log.info("Find delivery by status {}", deliveryStatusEnum);
        List<DeliveryDto> result = deliveryRepository.findByDeliveryStatusEnum(deliveryStatusEnum);
        if (result.isEmpty()) {
            log.error("Delivery by status {} not found", deliveryStatusEnum);
        }
        return  result;
    }


}
