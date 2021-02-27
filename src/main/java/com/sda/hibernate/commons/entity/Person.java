package com.sda.hibernate.commons.entity;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "persons")
public class Person {

    @Id
    @Column(name = "person_id")
    private Integer id;

    @Column(name = "first_name", nullable = false)
    @Size(min = 2)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(min = 2) // przykład użycia walidacji Hibernate: https://www.baeldung.com/hibernate-validator-constraints
    private String lastName;

    @Column(name = "date_of_brith")
    private String dateOfBirth;

    /*
        Dla sprawdzenia SELECT n+1 problem trzeba aktywować opcję FetchType.EAGER.
        Następnie w klasie PersonDaoTest uważnie śledzić zapytania generowane do bazy danych.
        Więcej informacji: https://vladmihalcea.com/n-plus-1-query-problem/
     */
    //@ManyToOne(fetch = FetchType.EAGER)
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @JoinColumn(name = "country_id")
    private Country country;
}
