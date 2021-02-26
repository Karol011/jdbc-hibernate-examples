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
    @Size(min = 2)
    private String lastName;

    @Column(name = "date_of_brith")
    private String dateOfBirth;

    //@ManyToOne(fetch = FetchType.EAGER) // dla sprawdzenia SELECT n+1 problem
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @JoinColumn(name = "country_id")
    private Country country;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "persons_movies",
//            joinColumns = {@JoinColumn(name = "person_id")},
//            inverseJoinColumns = {@JoinColumn(name = "type_id")})
//    private List<PersonType> personTypes = new ArrayList<>();

//    @ManyToMany(mappedBy = "persons", fetch = FetchType.LAZY)
//    private List<Movie> movies = new ArrayList<>();
}
