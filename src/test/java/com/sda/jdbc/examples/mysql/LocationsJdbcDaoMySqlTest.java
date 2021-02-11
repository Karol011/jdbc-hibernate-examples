package com.sda.jdbc.examples.mysql;

import com.sda.jdbc.examples.LocationsDAO;
import com.sda.jdbc.commons.connection.CustomConnection;
import com.sda.jdbc.commons.connection.MySqlConnector;
import com.sda.jdbc.commons.entity.Location;
import lombok.extern.log4j.Log4j;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

@Log4j
public class LocationsJdbcDaoMySqlTest {

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

    @Test
    public void shouldSaveAndDeleteExactNumberOfLocations() {
        int countAfterSave = 0;
        int countAfterDelete = 0;

        Location firstLocation = new Location();
        firstLocation.setLocationId(99);
        firstLocation.setStateProvince("Test state province 1");
        firstLocation.setStreetAddress("Test street address 1");
        firstLocation.setPostalCode("CODE1");
        firstLocation.setCity("City 1");

        Location secondLocation = new Location();
        secondLocation.setLocationId(100);
        secondLocation.setStateProvince("Test state province 2");
        secondLocation.setStreetAddress("Test street address 2");
        secondLocation.setPostalCode("CODE2");
        secondLocation.setCity("City 2");

        try {
            locationsDAO.deleteBatch(asList(firstLocation, secondLocation));

            locationsDAO.saveBatch(asList(firstLocation, secondLocation));
            List<Location> result = locationsDAO.findAll();

            countAfterSave = result.size();

            locationsDAO.deleteBatch(asList(firstLocation, secondLocation));

            result = locationsDAO.findAll();
            countAfterDelete = result.size();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        Assert.assertEquals(countAfterSave, 25);
        Assert.assertEquals(countAfterDelete, 23);
    }

    @Test
    public void shouldReturnAllLocationsWithPassingOnlyOneName() {
        List<Location> locations = new ArrayList<>();

        try {
            locations.addAll(locationsDAO.findByCityWithSQLInjection("'Roma' OR 1 = 1"));
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }

        log.info("Locations count: " + locations.size());

        Assert.assertEquals(locations.size(), 23);
    }
}