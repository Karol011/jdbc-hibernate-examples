package com.sda.hibernate.commons.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Objects;

/*
    Odwzorowanie w świecie obiektowym tabeli zawierającej dane o krajach (countries).
    Wykonane zostało za pomocą annotations.
    https://docs.jboss.org/hibernate/annotations/3.5/reference/en/html/entity.html
 */
@NoArgsConstructor // lombok: https://projectlombok.org/features/all
@Data
@Entity
@Table(name = "countries")
@Audited // do pokazania działania zapisu historii modyfikacji danych: https://vladmihalcea.com/the-best-way-to-implement-an-audit-log-using-hibernate-envers/
@OptimisticLocking(type = OptimisticLockType.VERSION) // do pokazania działania optimistic locking: https://vladmihalcea.com/optimistic-locking-version-property-jpa-hibernate/
public class Country {

    public Country(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Version
    private Integer version;

//    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
//    private List<Person> persons;
}
