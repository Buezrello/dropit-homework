package com.example.dropit.service;

import com.example.dropit.dto.SingleLineAddressDto;
import com.example.dropit.model.geoapify.Properties;

public interface DropitService {
    Properties resolveAddress(SingleLineAddressDto singleLineAddressDto);
}
