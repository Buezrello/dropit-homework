package com.example.dropit.service;

import com.example.dropit.dto.FormattedAddressDto;
import com.example.dropit.dto.SingleLineAddressDto;
import com.example.dropit.dto.TimeSlot;

import java.util.List;

public interface DropitService {
    FormattedAddressDto resolveAddress(SingleLineAddressDto singleLineAddressDto);
    List<TimeSlot> availableTimeSlots(String dateTime, SingleLineAddressDto singleLineAddressDto);
}
