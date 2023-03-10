package com.example.dropit.controller;

import com.example.dropit.dto.SingleLineAddressDto;
import com.example.dropit.model.geoapify.Properties;
import com.example.dropit.service.DropitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RestController
@RequiredArgsConstructor
public class DropitController {

    private final DropitService dropitService;

    @PostMapping("/resolve-address")
    public ResponseEntity<Properties> resolveAddress(@RequestBody SingleLineAddressDto singleLineAddressDto) {
        return ResponseEntity.ok(dropitService.resolveAddress(singleLineAddressDto));
    }
}
