package com.sda.hibernate.examples;

import com.sda.hibernate.commons.dao.CountryDao;
import com.sda.hibernate.commons.entity.Country;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;

public class CountryDaoTest {

    private static final Logger logger = Logger.getLogger(CountryDaoTest.class);

    private CountryDao countryDao;

    @BeforeMethod
    public void setUp() {
        countryDao = new CountryDao();
    }

    @Test
    public void shouldSaveCountry() {
        Country country = new Country();

        country.setName("France");

        countryDao.save(country);

        Country france = countryDao.findByName("France");

        Assert.assertNotNull(france.getName());
    }

    @Test(dependsOnMethods = "shouldSaveCountry", expectedExceptions = NoResultException.class)
    public void shouldDeleteCountry() {
        Country franceBeforeDelete = countryDao.findByName("France");

        countryDao.delete(franceBeforeDelete);

        Country franceAfterDelete = countryDao.findByName("France");
        Assert.assertNull(franceAfterDelete.getName());
    }

    @Test
    public void shouldUpdatePoland() {
        Country poland = countryDao.findById(1);
        poland.setName("Poland 2");

        countryDao.update(poland);

        Country poland2 = countryDao.findById(1);
        poland2.setName("Poland 3");

        countryDao.update(poland2);
    }

    @Test(dependsOnMethods = "shouldUpdatePoland")
    public void shouldDisplayHistoryForRecord() {
        countryDao.shouldDisplayHistoryForRecord();
    }

    /*
        Przykład działania OptimisticLock w praktyce.
        Związane z adnotacją @OptimisticLocking w klasie Country.
        Więcej informacji: https://vladmihalcea.com/optimistic-locking-version-property-jpa-hibernate/
     */
    @Test(expectedExceptions = OptimisticLockException.class)
    public void testOptimisticLocking() {
        Country countryVersion1 = countryDao.findById(1);
        Country countryVersion2 = countryDao.findById(1);

        countryVersion1.setName("Poland 1");
        countryDao.update(countryVersion1);

        countryVersion2.setName("Poland 2");
        countryDao.update(countryVersion2);
    }
}