package com.example.dropit.controller;

import com.example.dropit.dto.DeliveryDto;
import com.example.dropit.dto.FormattedAddressDto;
import com.example.dropit.dto.SingleLineAddressDto;
import com.example.dropit.dto.TimeSlot;
import com.example.dropit.model.response.StatusResponse;
import com.example.dropit.service.DropitService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
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

    /*@PostMapping("/timeslots")
    public ResponseEntity<List<TimeSlot>> retrieveAvailableTimeSlots(@RequestBody SingleLineAddressDto singleLineAddressDto, @RequestHeader String dateTime) {
        return ResponseEntity.ok(dropitService.availableTimeSlots(dateTime, singleLineAddressDto));
    }*/

    @PostMapping("/timeslots")
    public ResponseEntity<List<TimeSlot>> retrieveAvailableTimeSlots(@RequestBody SingleLineAddressDto singleLineAddressDto,
                                                                     @RequestHeader @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @Valid LocalDateTime dateTime) {
        return ResponseEntity.ok(dropitService.availableTimeSlots(dateTime, singleLineAddressDto));
    }

    @PostMapping("/deliveries")
    public ResponseEntity<StatusResponse> bookDelivery(@RequestBody SingleLineAddressDto singleLineAddressDto,
                                                       @RequestHeader String userId,
                                                       @RequestHeader @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @Valid LocalDateTime dateTime) {
        return ResponseEntity.ok(dropitService.bookDelivery(userId, singleLineAddressDto, dateTime));
    }

    @DeleteMapping("/deliveries/{delivery_id}")
    public ResponseEntity<StatusResponse> cancelDelivery(@PathVariable(value = "delivery_id") String deliveryId) {
        return ResponseEntity.ok(dropitService.cancelDelivery(deliveryId));
    }

    @PutMapping("/deliveries/{delivery_id}")
    public ResponseEntity<StatusResponse> deliverDelivery(@PathVariable(value = "delivery_id") String deliveryId) {
        return ResponseEntity.ok(dropitService.deliverDelivery(deliveryId));
    }

    @GetMapping("/deliveries/daily")
    public ResponseEntity<List<DeliveryDto>> getDailyDelivery() {
        return ResponseEntity.ok(dropitService.todayDelivery());
    }

    @GetMapping("/deliveries/weekly")
    public ResponseEntity<List<DeliveryDto>> getWeeklyDelivery() {
        return ResponseEntity.ok(dropitService.currentWeekDelivery());
    }
}
