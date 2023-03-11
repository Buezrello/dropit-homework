package com.example.dropit.service.impl;

import com.example.dropit.config.GeoapifyConfigurationProperties;
import com.example.dropit.dto.DeliveryDto;
import com.example.dropit.dto.FormattedAddressDto;
import com.example.dropit.dto.SingleLineAddressDto;
import com.example.dropit.dto.TimeSlot;
import com.example.dropit.enums.DeliveryStatusEnum;
import com.example.dropit.exception.BusinessCapacityException;
import com.example.dropit.exception.MongoDbNotFoundException;
import com.example.dropit.exception.ResolveAddressException;
import com.example.dropit.feign.GeoapifyFeignClient;
import com.example.dropit.model.geoapify.GeoapifyResponse;
import com.example.dropit.model.geoapify.Properties;
import com.example.dropit.model.response.StatusResponse;
import com.example.dropit.service.DropitService;
import com.example.dropit.service.helper.MongoDbHelper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Data
public class DropitServiceImpl implements DropitService {

    private final GeoapifyConfigurationProperties geoapifyConfigurationProperties;
    private final GeoapifyFeignClient geoapifyFeignClient;
    private final MongoDbHelper mongoDbHelper;

    private final int BUSINESS_CAPACITY = 10;

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
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return  availableTimeSlots(localDateTime, singleLineAddressDto);
    }


    @Override
    public List<TimeSlot> availableTimeSlots(LocalDateTime dateTime, SingleLineAddressDto singleLineAddressDto) {
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

    @Override
    public StatusResponse bookDelivery(String userId, SingleLineAddressDto singleLineAddressDto, LocalDateTime dateTime) {
        log.info("Book a new delivery");

        if (mongoDbHelper.countNumberInProgressDelivery() >= BUSINESS_CAPACITY) {
            String error = "Temporary impossible to delivery, daily capacity problem, try again latter";
            log.error(error);
            throw new BusinessCapacityException(error);
        }

        if (mongoDbHelper.isHoliday(dateTime)) {
            String error = String.format("%s is holiday, no delivery", dateTime);
            log.error(error);
            throw new MongoDbNotFoundException(error);
        }

        List<TimeSlot> availableTimeSlots = availableTimeSlots(dateTime, singleLineAddressDto);

        mongoDbHelper.createNewDelivery(userId, availableTimeSlots.get(0).getId());

        return new StatusResponse(DeliveryStatusEnum.IN_PROGRESS.name(), "Delivery booked");
    }

    @Override
    public StatusResponse cancelDelivery(String deliveryId) {
        log.info("Cancelling delivery {}", deliveryId);
        mongoDbHelper.updateDelivery(deliveryId, DeliveryStatusEnum.CANCELED);
        return new StatusResponse(DeliveryStatusEnum.CANCELED.name(), "Delivery canceled");
    }

    @Override
    public StatusResponse deliverDelivery(String deliveryId) {
        log.info("Delivering delivery {}", deliveryId);
        mongoDbHelper.updateDelivery(deliveryId, DeliveryStatusEnum.DELIVERED);
        return new StatusResponse(DeliveryStatusEnum.DELIVERED.name(), "Delivery delivered");
    }

    @Override
    public List<DeliveryDto> todayDelivery() {
        LocalDate localDate = LocalDate.now();
        log.info("Find today delivery {}", localDate);
        return getDeliveryDtos(localDate, localDate.atTime(LocalTime.MAX));
    }

    @Override
    public List<DeliveryDto> currentWeekDelivery() {
        log.info("Find current week delivery");
        LocalDate localDate = LocalDate.now();
        LocalDate startWeek = localDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate endWeek = localDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
        return getDeliveryDtos(startWeek, endWeek.atTime(LocalTime.MAX));
    }

    private List<DeliveryDto> getDeliveryDtos(LocalDate localDate, LocalDateTime dateTime) {
        LocalDateTime startOfDay = localDate.atStartOfDay();
        List<DeliveryDto> deliveryByDate = mongoDbHelper.findDeliveryByDate(startOfDay, dateTime);
        if (deliveryByDate.isEmpty()) {
            throw new MongoDbNotFoundException("Deliveries not found");
        }
        return deliveryByDate;
    }
}
