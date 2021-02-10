package com.sda.jdbc;

import com.sda.jdbc.connection.CustomConnection;
import com.sda.jdbc.connection.MySqlConnector;
import com.sda.jdbc.entity.Country;
import com.sda.jdbc.entity.Location;
import lombok.extern.log4j.Log4j;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

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
        List<Country> countries = new ArrayList<>();

        try {
            countries.addAll(countryDAO.findCountryByName("Argentina"));
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }

        log.info("Countries count: " + countries.size());

        Assert.assertEquals(countries.size(), 1);
    }
}