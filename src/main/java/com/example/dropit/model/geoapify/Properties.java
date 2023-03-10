package com.example.dropit.model.geoapify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Properties {
    String street;
    @JsonProperty(value = "address_line1")
    String line1;
    @JsonProperty(value = "address_line2")
    String line2;
    String country;
    String postcode;
}
