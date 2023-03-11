package com.example.dropit.dto;

import com.example.dropit.enums.DeliveryStatusEnum;
import lombok.Data;
import lombok.Generated;
import org.springframework.data.annotation.Id;

@Data
@Generated
public class DeliveryDto {

    @Id
    private String id;

    public DeliveryDto(String userId, String timeSlotId, DeliveryStatusEnum deliveryStatusEnum) {
        this.userId = userId;
        this.timeSlotId = timeSlotId;
        this.deliveryStatusEnum = deliveryStatusEnum;
    }

    private String userId;
    private String timeSlotId;
    private DeliveryStatusEnum deliveryStatusEnum;
}

