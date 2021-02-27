package com.sda.jdbc.examples.mysql;

import com.sda.jdbc.examples.CountryDAO;
import com.sda.jdbc.commons.connection.CustomConnection;
import com.sda.jdbc.commons.connection.MySqlConnector;
import com.sda.jdbc.commons.entity.Country;
import lombok.extern.log4j.Log4j;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class CountryJdbcDaoMySqlTest {

    private CountryDAO countryDAO;
    private CustomConnection mySqlConnection;

    @BeforeClass
    public void setUp() {
        mySqlConnection = new MySqlConnector();
        countryDAO = new CountryDAO(mySqlConnection);
    }

    @Test
    public void shouldReturnCountryByName(){
        List<Country> countries = new ArrayList<>(countryDAO.findCountryByName("Germany"));

        log.info("Countries count: " + countries.size());

        Assert.assertEquals(countries.size(), 1);
    }
}