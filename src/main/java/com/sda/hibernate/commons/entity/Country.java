package com.sda.hibernate.commons.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@Data
@Entity
@Table(name = "countries")
@Audited // do pokazania działania zapisu historii
@OptimisticLocking(type = OptimisticLockType.VERSION) // do pokazania działania optimistic locking
public class Country {

    public Country(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "country_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Version
    private Integer version;

//    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
//    private List<Person> persons;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return id.equals(country.id) &&
                name.equals(country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
