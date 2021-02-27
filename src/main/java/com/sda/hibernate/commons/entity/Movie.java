package com.sda.hibernate.commons.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "movies")
public class Movie {

    @Id
    @Column(name = "movie_id")
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "production_year")
    private Integer productionYear;
    @Column(name = "duration")
    private Integer duration;

    @ManyToOne
    @JoinColumn(name ="country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Transient
    private List<Person> persons = new ArrayList<>();
}
