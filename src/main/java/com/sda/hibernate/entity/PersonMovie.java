package com.sda.hibernate.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Data
@Entity
@Table(name = "persons_movies")
@EqualsAndHashCode
public class PersonMovie implements Serializable {

    @Id
    @Column(name = "movie_id")
    private Integer movieId;
    @Id
    @Column(name = "person_id")
    private Integer personId;
    @Id
    @Column(name = "type_id")
    private Integer typeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private PersonType personType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;
}

