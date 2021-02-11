package com.sda.jdbc.commons.entity;

import lombok.Data;

// Więcej na temat lombok: https://projectlombok.org/
@Data
public class Location {

    private int locationId;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String stateProvince;
    private String countryId;
}
