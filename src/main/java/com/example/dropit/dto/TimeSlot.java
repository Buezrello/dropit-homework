package com.example.dropit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlot {

    @Id
    private String id;
    @JsonProperty(value = "start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JsonProperty(value = "end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @JsonProperty(value = "supported_postcodes")
    private List<String> supportedPostcodes;

}
