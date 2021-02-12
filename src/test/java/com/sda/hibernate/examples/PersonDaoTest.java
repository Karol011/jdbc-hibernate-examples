package com.sda.hibernate.examples;

import com.sda.hibernate.commons.dao.PersonDao;
import com.sda.hibernate.commons.entity.Country;
import com.sda.hibernate.commons.entity.Person;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.util.List;

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

        Country country = new Country(1, "Poland");
        p.setCountry(country);

        personDao.saveOrUpdate(p);

        Person savedPerson = personDao.findByFirstAndLastName("Emmanuel", "Olisadebe");
        assertEquals(savedPerson.getLastName(), "Olisadebe");
    }

    @Test(dependsOnMethods = "shouldSavePerson")
    public void deletePerson(){
        Person person = personDao.findByFirstAndLastName("Emmanuel", "Olisadebe");

        personDao.delete(person);
    }

}