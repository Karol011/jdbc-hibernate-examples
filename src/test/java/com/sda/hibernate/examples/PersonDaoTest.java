package com.sda.hibernate.examples;

import com.sda.hibernate.commons.dao.PersonDao;
import com.sda.hibernate.commons.entity.Country;
import com.sda.hibernate.commons.entity.Person;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class PersonDaoTest {

    private final Logger logger = Logger.getLogger(PersonDaoTest.class);

    private PersonDao personDao;

    public PersonDaoTest() {
        personDao = new PersonDao();
    }

    @Test
    public void shouldFindAllPersons() {
        List<Person> personList = personDao.findAll();

        personList.forEach(
                person ->
                        logger.info(person.getFirstName() + " " + person.getLastName() + " " + person.getCountry().getName())
        );

        assertTrue(personList.size() > 0);
    }

    @Test
    public void shouldSavePerson() {
        Person p = new Person();

        p.setId(10);
        p.setFirstName("Emmanuel");
        p.setLastName("Olisadebe");

        Country country = new Country(1, "Nigeria");
        p.setCountry(country);

        personDao.saveOrUpdate(p);

        Person savedPerson = personDao.findByFirstAndLastName("Emmanuel", "Olisadebe");
        assertEquals(savedPerson.getLastName(), "Olisadebe");
    }

    @Test(dependsOnMethods = "shouldSavePerson")
    public void deletePerson() {
        Person person = personDao.findByFirstAndLastName("Emmanuel", "Olisadebe");

        personDao.delete(person);
    }

    /*
        Poniższy test pokazuje przykład działania HibernateValidator.
        Więcej informacji tutaj: https://www.baeldung.com/hibernate-validator-constraints
     */
    @Test
    public void shouldRunValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Person person = Person.builder()
                .id(10)
                .firstName("P")
                .lastName("M")
                .build();

        Set<ConstraintViolation<Person>> constraintViolations =
                validator.validate(person);

        assertEquals( 2, constraintViolations.size() );
    }

}