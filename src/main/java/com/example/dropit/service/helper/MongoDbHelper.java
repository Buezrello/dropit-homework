package com.example.dropit.service.helper;

import com.example.dropit.dao.MongoGeneric;
import com.example.dropit.dto.DeliveryDto;
import com.example.dropit.dto.TimeSlot;
import com.example.dropit.enums.DeliveryStatusEnum;
import lombok.Data;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Component
@Data
@Generated
@Slf4j
public class MongoDbHelper {
    private final MongoGeneric mongoGeneric;

    public void saveNewDelivery(String timeSlotId) {
        String userId = UUID.randomUUID().toString();
        mongoGeneric.createNewDelivery(userId, timeSlotId, DeliveryStatusEnum.IN_PROGRESS);
    }

    public void updateDelivery(String deliveryId, DeliveryStatusEnum deliveryStatusEnum) {
        DeliveryDto delivery = mongoGeneric.findDeliveryById(deliveryId);
        delivery.setDeliveryStatusEnum(deliveryStatusEnum);
        mongoGeneric.updateDelivery(delivery);
    }

    public List<TimeSlot> findTimeSlotByDateTime(String dateTime) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return mongoGeneric.findTimeSlotByTime(localDateTime);
    }

    public List<TimeSlot> findTimeSlotByDateTimeAndPostCode(String dateTime, String postcode) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return mongoGeneric.findTimeSlotByTimeAndPostCode(localDateTime, postcode);
    }

    public int countNumberInProgressDeliveryByTimeSlots(String timeSlotId) {
        return mongoGeneric.findDeliveryByTimeSlotAndStatus(timeSlotId, DeliveryStatusEnum.IN_PROGRESS).size();
    }

    public int countNumberInProgressDelivery() {
        return mongoGeneric.findDeliveryByStatus(DeliveryStatusEnum.IN_PROGRESS).size();
    }
}
