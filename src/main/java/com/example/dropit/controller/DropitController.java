package com.example.dropit.controller;

import com.example.dropit.dto.FormattedAddressDto;
import com.example.dropit.dto.SingleLineAddressDto;
import com.example.dropit.dto.TimeSlot;
import com.example.dropit.service.DropitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Validated
@RestController
@RequiredArgsConstructor
public class DropitController {

    private final DropitService dropitService;

    @PostMapping("/resolve-address")
    public ResponseEntity<FormattedAddressDto> resolveAddress(@RequestBody SingleLineAddressDto singleLineAddressDto) {
        return ResponseEntity.ok(dropitService.resolveAddress(singleLineAddressDto));
    }

    @PostMapping("/timeslots")
    public ResponseEntity<List<TimeSlot>> retrieveAvailableTimeSlots(@RequestBody SingleLineAddressDto singleLineAddressDto, @RequestHeader String dateTime) {
        return ResponseEntity.ok(dropitService.availableTimeSlots(dateTime, singleLineAddressDto));
    }
}
