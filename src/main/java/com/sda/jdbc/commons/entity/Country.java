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
public class Country {

    private String countryId;
    private String countryName;

    private Region region;
}
