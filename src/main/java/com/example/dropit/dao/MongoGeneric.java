package com.example.dropit.dao;

import com.example.dropit.dto.DeliveryDto;
import com.example.dropit.dto.Holiday;
import com.example.dropit.dto.TimeSlot;
import com.example.dropit.enums.DeliveryStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

public interface MongoGeneric {

    void initTimeSlots(List<TimeSlot> timeSlots);
    void initHolidays(List<Holiday> holidays);
    void createNewDelivery(String userId, String timeSlotId, DeliveryStatusEnum deliveryStatusEnum);
    DeliveryDto findDeliveryById(String deliveryId);
    void updateDelivery(DeliveryDto deliveryDto);
    List<TimeSlot> findTimeSlotByTime(LocalDateTime dateTime);
    List<TimeSlot> findTimeSlotByTimeAndPostCode(LocalDateTime dateTime, String postcode);
    List<TimeSlot> findTimeSlotsByDate(LocalDateTime startDate, LocalDateTime endDate);
    List<DeliveryDto> findDeliveryByTimeSlotAndStatus(String timeSlotId, DeliveryStatusEnum deliveryStatusEnum);
    List<DeliveryDto> findDeliveryByStatus(DeliveryStatusEnum deliveryStatusEnum);
    List<DeliveryDto> findDeliveryByTimeSlot(String timeSlotId);
}
