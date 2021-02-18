package com.sda.jdbc.commons.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

    private int departmentId;
    private String departmentName;
    private Integer managerId;
    private Integer locationId;

    private Location location;
}
