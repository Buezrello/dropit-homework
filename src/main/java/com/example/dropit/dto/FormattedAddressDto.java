package com.example.dropit.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
public class FormattedAddressDto {
    String street;
    String line1;
    String line2;
    String country;
    String postcode;
}
