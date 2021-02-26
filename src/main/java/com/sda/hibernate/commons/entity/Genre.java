package com.sda.hibernate.commons.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@Data
@ToString
public class Genre {

    private Integer id;
    private String name;
}
