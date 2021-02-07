package com.sda.jdbc;

import com.sda.jdbc.connection.CustomConnection;
import com.sda.jdbc.connection.MySqlConnector;
import com.sda.jdbc.entity.Location;
import com.sda.jdbc.entity.Region;
import lombok.extern.log4j.Log4j;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

@Log4j
public class LocationsDaoMySqlTest {

    private LocationsDAO locationsDAO;
    private CustomConnection mySqlConnection;

    @BeforeClass
    public void setUp() {
        mySqlConnection = new MySqlConnector();
        locationsDAO = new LocationsDAO(mySqlConnection);
    }


    @Test
    public void shouldReturnAllLocations() {
        List<Location> locations = new ArrayList<>();

        try {
            locations.addAll(locationsDAO.findAll());
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }

        log.info("Locations count: " + locations.size());

        Assert.assertEquals(locations.size(), 23);
    }
}