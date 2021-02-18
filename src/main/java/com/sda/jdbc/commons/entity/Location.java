package com.sda.jdbc.commons.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Więcej na temat lombok: https://projectlombok.org/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {

    private int locationId;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String stateProvince;
    private String countryId;

    private Country country;
}
