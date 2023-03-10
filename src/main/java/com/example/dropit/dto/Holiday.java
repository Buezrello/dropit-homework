package com.example.dropit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
public class Holiday {
    @Id
    private String id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String country;
}
