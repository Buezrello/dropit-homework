package com.example.dropit.controller;

import com.example.dropit.dto.DeliveryDto;
import com.example.dropit.dto.FormattedAddressDto;
import com.example.dropit.dto.SingleLineAddressDto;
import com.example.dropit.dto.TimeSlot;
import com.example.dropit.model.response.StatusResponse;
import com.example.dropit.service.DropitService;
import com.example.dropit.ult.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary="Retrieve Formatted Address")
    @ApiResponses(value= {
            @ApiResponse(responseCode="200",description= "Formatted Address found"),
            @ApiResponse(responseCode="400",description= Constants.BAD_REQUEST, content=@Content),
            @ApiResponse(responseCode="401",description= Constants.UNAUTHORIZED, content=@Content),
            @ApiResponse(responseCode="403",description= Constants.FORBIDDEN, content=@Content),
            @ApiResponse(responseCode="404",description= Constants.NOT_FOUND, content=@Content),
            @ApiResponse(responseCode="500",description=Constants.INTERNAL_SERVER_ERROR, content=@Content)})
    @PostMapping("/resolve-address")
    public ResponseEntity<FormattedAddressDto> resolveAddress(@RequestBody SingleLineAddressDto singleLineAddressDto) {
        return ResponseEntity.ok(dropitService.resolveAddress(singleLineAddressDto));
    }

    /*@PostMapping("/timeslots")
    public ResponseEntity<List<TimeSlot>> retrieveAvailableTimeSlots(@RequestBody SingleLineAddressDto singleLineAddressDto, @RequestHeader String dateTime) {
        return ResponseEntity.ok(dropitService.availableTimeSlots(dateTime, singleLineAddressDto));
    }*/

    @Operation(summary="Retrieve Available Time Slots")
    @ApiResponses(value= {
            @ApiResponse(responseCode="200",description= "Available Time Slots found"),
            @ApiResponse(responseCode="400",description= Constants.BAD_REQUEST, content=@Content),
            @ApiResponse(responseCode="401",description= Constants.UNAUTHORIZED, content=@Content),
            @ApiResponse(responseCode="403",description= Constants.FORBIDDEN, content=@Content),
            @ApiResponse(responseCode="404",description= Constants.NOT_FOUND, content=@Content),
            @ApiResponse(responseCode="500",description=Constants.INTERNAL_SERVER_ERROR, content=@Content)})
    @PostMapping("/timeslots")
    public ResponseEntity<List<TimeSlot>> retrieveAvailableTimeSlots(@RequestBody SingleLineAddressDto singleLineAddressDto,
                                                                     @RequestHeader @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @Valid LocalDateTime dateTime) {
        return ResponseEntity.ok(dropitService.availableTimeSlots(dateTime, singleLineAddressDto));
    }

    @Operation(summary="Booking a New Delivery")
    @ApiResponses(value= {
            @ApiResponse(responseCode="200",description= "New Delivery successfully booked"),
            @ApiResponse(responseCode="400",description= Constants.BAD_REQUEST, content=@Content),
            @ApiResponse(responseCode="401",description= Constants.UNAUTHORIZED, content=@Content),
            @ApiResponse(responseCode="403",description= Constants.FORBIDDEN, content=@Content),
            @ApiResponse(responseCode="404",description= Constants.NOT_FOUND, content=@Content),
            @ApiResponse(responseCode="500",description=Constants.INTERNAL_SERVER_ERROR, content=@Content)})
    @PostMapping("/deliveries")
    public ResponseEntity<StatusResponse> bookDelivery(@RequestBody SingleLineAddressDto singleLineAddressDto,
                                                       @RequestHeader String userId,
                                                       @RequestHeader @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @Valid LocalDateTime dateTime) {
        return ResponseEntity.ok(dropitService.bookDelivery(userId, singleLineAddressDto, dateTime));
    }

    @Operation(summary="Canceling a Delivery")
    @ApiResponses(value= {
            @ApiResponse(responseCode="200",description= "Delivery successfully canceled"),
            @ApiResponse(responseCode="400",description= Constants.BAD_REQUEST, content=@Content),
            @ApiResponse(responseCode="401",description= Constants.UNAUTHORIZED, content=@Content),
            @ApiResponse(responseCode="403",description= Constants.FORBIDDEN, content=@Content),
            @ApiResponse(responseCode="404",description= Constants.NOT_FOUND, content=@Content),
            @ApiResponse(responseCode="500",description=Constants.INTERNAL_SERVER_ERROR, content=@Content)})
    @DeleteMapping("/deliveries/{delivery_id}")
    public ResponseEntity<StatusResponse> cancelDelivery(@PathVariable(value = "delivery_id") String deliveryId) {
        return ResponseEntity.ok(dropitService.cancelDelivery(deliveryId));
    }

    @Operation(summary="Change Delivery Status to Delivered")
    @ApiResponses(value= {
            @ApiResponse(responseCode="200",description= "Delivery status successfully updated"),
            @ApiResponse(responseCode="400",description= Constants.BAD_REQUEST, content=@Content),
            @ApiResponse(responseCode="401",description= Constants.UNAUTHORIZED, content=@Content),
            @ApiResponse(responseCode="403",description= Constants.FORBIDDEN, content=@Content),
            @ApiResponse(responseCode="404",description= Constants.NOT_FOUND, content=@Content),
            @ApiResponse(responseCode="500",description=Constants.INTERNAL_SERVER_ERROR, content=@Content)})
    @PutMapping("/deliveries/{delivery_id}")
    public ResponseEntity<StatusResponse> deliverDelivery(@PathVariable(value = "delivery_id") String deliveryId) {
        return ResponseEntity.ok(dropitService.deliverDelivery(deliveryId));
    }

    @Operation(summary="Retrieve All Daily Deliveries")
    @ApiResponses(value= {
            @ApiResponse(responseCode="200",description= "Deliveries found"),
            @ApiResponse(responseCode="400",description= Constants.BAD_REQUEST, content=@Content),
            @ApiResponse(responseCode="401",description= Constants.UNAUTHORIZED, content=@Content),
            @ApiResponse(responseCode="403",description= Constants.FORBIDDEN, content=@Content),
            @ApiResponse(responseCode="404",description= Constants.NOT_FOUND, content=@Content),
            @ApiResponse(responseCode="500",description=Constants.INTERNAL_SERVER_ERROR, content=@Content)})
    @GetMapping("/deliveries/daily")
    public ResponseEntity<List<DeliveryDto>> getDailyDelivery() {
        return ResponseEntity.ok(dropitService.todayDelivery());
    }

    @Operation(summary="Retrieve All Weekly Deliveries")
    @ApiResponses(value= {
            @ApiResponse(responseCode="200",description= "Deliveries found"),
            @ApiResponse(responseCode="400",description= Constants.BAD_REQUEST, content=@Content),
            @ApiResponse(responseCode="401",description= Constants.UNAUTHORIZED, content=@Content),
            @ApiResponse(responseCode="403",description= Constants.FORBIDDEN, content=@Content),
            @ApiResponse(responseCode="404",description= Constants.NOT_FOUND, content=@Content),
            @ApiResponse(responseCode="500",description=Constants.INTERNAL_SERVER_ERROR, content=@Content)})
    @GetMapping("/deliveries/weekly")
    public ResponseEntity<List<DeliveryDto>> getWeeklyDelivery() {
        return ResponseEntity.ok(dropitService.currentWeekDelivery());
    }
}
