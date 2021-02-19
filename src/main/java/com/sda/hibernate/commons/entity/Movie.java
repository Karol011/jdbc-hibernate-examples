package com.sda.hibernate.commons.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class Movie {

    private Integer id;
    private String title;
    private Integer productionYear;
    private Integer duration;

    private Country country;
    private Genre genre;
    private List<Person> persons = new ArrayList<>();
}
