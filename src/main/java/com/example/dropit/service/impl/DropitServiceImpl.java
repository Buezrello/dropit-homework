package com.example.dropit.service.impl;

import com.example.dropit.config.GeoapifyConfigurationProperties;
import com.example.dropit.dto.FormattedAddressDto;
import com.example.dropit.dto.SingleLineAddressDto;
import com.example.dropit.dto.TimeSlot;
import com.example.dropit.exception.MongoDbNotFoundException;
import com.example.dropit.exception.ResolveAddressException;
import com.example.dropit.feign.GeoapifyFeignClient;
import com.example.dropit.model.geoapify.GeoapifyResponse;
import com.example.dropit.model.geoapify.Properties;
import com.example.dropit.service.DropitService;
import com.example.dropit.service.helper.MongoDbHelper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Data
public class DropitServiceImpl implements DropitService {

    private final GeoapifyConfigurationProperties geoapifyConfigurationProperties;
    private final GeoapifyFeignClient geoapifyFeignClient;
    private final MongoDbHelper mongoDbHelper;

    @Override
    public FormattedAddressDto resolveAddress(SingleLineAddressDto singleLineAddressDto) {
        log.info("Resolving an address {}", singleLineAddressDto);
        GeoapifyResponse geoapifyResponse = geoapifyFeignClient.getGeoapifyResponse(singleLineAddressDto.getSearchTerm(), geoapifyConfigurationProperties.getToken());

        if (CollectionUtils.isEmpty(geoapifyResponse.getFeatures())
                || !StringUtils.hasLength(geoapifyResponse.getFeatures().get(0).getProperties().getPostcode())) {
            throw new ResolveAddressException("Postcode not found. Check address string: <Building number> <Street> <City> <Country>");
        }

        Properties properties = geoapifyResponse.getFeatures().get(0).getProperties();
        FormattedAddressDto formattedAddressDto = new FormattedAddressDto();
        BeanUtils.copyProperties(properties, formattedAddressDto);

        return formattedAddressDto;
    }

    @Override
    public List<TimeSlot> availableTimeSlots(String dateTime, SingleLineAddressDto singleLineAddressDto) {
        log.info("Find available time slots for address {}", singleLineAddressDto);
        FormattedAddressDto formattedAddressDto = resolveAddress(singleLineAddressDto);
        List<TimeSlot> timeSlotByDateTimeAndPostCode = mongoDbHelper.findTimeSlotByDateTimeAndPostCode(dateTime, formattedAddressDto.getPostcode());
        List<TimeSlot> timeSlots =
                timeSlotByDateTimeAndPostCode.stream()
                        .filter(e -> mongoDbHelper.countNumberInProgressDeliveryByTimeSlots(e.getId()) < 2)
                        .collect(Collectors.toList());

        if (timeSlots.isEmpty()) {
            String error = "Available time slots not found";
            log.error(error);
            throw new MongoDbNotFoundException(error);
        }

        return  timeSlots;
    }
}
