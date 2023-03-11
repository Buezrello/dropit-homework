package com.example.dropit.repositories;

import com.example.dropit.dto.DeliveryDto;
import com.example.dropit.enums.DeliveryStatusEnum;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeliveryRepository extends MongoRepository<DeliveryDto, String> {
    List<DeliveryDto> findByTimeSlotIdAndDeliveryStatusEnum(String timeSlotId, DeliveryStatusEnum deliveryStatusEnum);
    List<DeliveryDto> findByDeliveryStatusEnum(DeliveryStatusEnum deliveryStatusEnum);
    List<DeliveryDto> findByTimeSlotId(String timeSlotId);
}
