package com.sda.jdbc.examples.mysql;

import com.sda.jdbc.examples.RegionsDAO;
import com.sda.jdbc.commons.connection.CustomConnection;
import com.sda.jdbc.commons.connection.MySqlConnector;
import com.sda.jdbc.commons.entity.Region;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class RegionJdbcDaoMySqlTest {

    private static final Logger logger = Logger.getLogger(RegionJdbcDaoMySqlTest.class);

    private RegionsDAO regionsDAO;
    private CustomConnection mySqlConnection;

    @BeforeClass
    public void setUp() {
        mySqlConnection = new MySqlConnector();
        regionsDAO = new RegionsDAO(mySqlConnection);
    }

    @Test
    public void shouldSaveRegion() {
        Region regionToSave = new Region(5, "Africa");
        Region regionFound = new Region();

        regionsDAO.save(regionToSave);

        regionFound = regionsDAO.findById(5);


        Assert.assertEquals(regionToSave, regionFound);
    }

    @Test(dependsOnMethods = {"shouldSaveRegion"})
    public void shouldUpdateRegion() {
        Region item = new Region();
        Region afterUpdate = new Region();

        item = regionsDAO.findById(5);

        item.setName("Africa Test");

        regionsDAO.update(item);

        afterUpdate = regionsDAO.findById(5);

        Assert.assertEquals(afterUpdate.getName(), "Africa Test");
    }

    @Test(dependsOnMethods = {"shouldSaveRegion", "shouldUpdateRegion"})
    public void shouldDeleteRegions() {
        int countBeforeDelete = 0;
        int countAfterDelete = 0;

        countBeforeDelete = regionsDAO.findAll().size();

        regionsDAO.delete(5);

        countAfterDelete = regionsDAO.findAll().size();

        Assert.assertEquals(countAfterDelete, countBeforeDelete - 1);
    }


    @Test(dependsOnMethods = {"shouldDeleteRegions"})
    public void shouldFindAllRegions() {
        List<Region> regions = new ArrayList<>(regionsDAO.findAll());

        logger.info("Regions count: " + regions.size());

        Assert.assertEquals(regions.size(), 7);
    }

    @Test()
    public void shouldFindMoreThanOneRegion() {
        List<Region> regions = new ArrayList<>(regionsDAO.findByName("'Europe' OR 1 = 1"));

        logger.info("Regions count: " + regions.size());

        Assert.assertTrue(regions.size() > 1);
    }

    @Test
    public void shouldSaveRegions() {
        int countAfterSave = 0;
        int countBeforeSave = 0;

        Region firstRegion = new Region(55, "Testowy region 1");
        Region secondRegion = new Region(66, "Testowy region 2");
        Region thirdRegion = new Region(77, "Testowy region 3");

        regionsDAO.deleteBatch(asList(firstRegion, secondRegion, thirdRegion));

        countBeforeSave = regionsDAO.findAll().size();
        logger.info("Regions before save: " + countBeforeSave);

        regionsDAO.saveBatch(asList(firstRegion, secondRegion, thirdRegion));

        countAfterSave = regionsDAO.findAll().size();
        logger.info("Regions after save: " + countAfterSave);

        Assert.assertTrue(countBeforeSave < countAfterSave);
    }
}
