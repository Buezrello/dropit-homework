package com.example.dropit.service;

import com.example.dropit.dto.DeliveryDto;
import com.example.dropit.dto.FormattedAddressDto;
import com.example.dropit.dto.SingleLineAddressDto;
import com.example.dropit.dto.TimeSlot;
import com.example.dropit.model.response.StatusResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface DropitService {
    FormattedAddressDto resolveAddress(SingleLineAddressDto singleLineAddressDto);
    List<TimeSlot> availableTimeSlots(String dateTime, SingleLineAddressDto singleLineAddressDto);
    List<TimeSlot> availableTimeSlots(LocalDateTime dateTime, SingleLineAddressDto singleLineAddressDto);
    StatusResponse bookDelivery(String userId, SingleLineAddressDto singleLineAddressDto, LocalDateTime dateTime);
    StatusResponse cancelDelivery(String deliveryId);
    StatusResponse deliverDelivery(String deliveryId);
    List<DeliveryDto> todayDelivery();
    List<DeliveryDto> currentWeekDelivery();
}
