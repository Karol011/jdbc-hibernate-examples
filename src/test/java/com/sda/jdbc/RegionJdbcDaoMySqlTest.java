package com.sda.jdbc;

import com.sda.jdbc.connection.CustomConnection;
import com.sda.jdbc.connection.MySqlConnector;
import com.sda.jdbc.entity.Region;
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

        try {
            regionsDAO.save(regionToSave);

            regionFound = regionsDAO.findById(5);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        Assert.assertEquals(regionToSave, regionFound);
    }

    @Test(dependsOnMethods = {"shouldSaveRegion"})
    public void shouldUpdateRegion() {
        Region item = new Region();
        Region afterUpdate = new Region();

        try {
            item = regionsDAO.findById(5);

            item.setName("Africa Test");

            regionsDAO.update(item);

            afterUpdate = regionsDAO.findById(5);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        Assert.assertEquals(afterUpdate.getName(), "Africa Test");
    }

    @Test(dependsOnMethods = {"shouldSaveRegion", "shouldUpdateRegion"})
    public void shouldDeleteRegions() {
        int countBeforeDelete = 0;
        int countAfterDelete = 0;

        try {
            countBeforeDelete = regionsDAO.findAll().size();

            regionsDAO.delete(5);

            countAfterDelete = regionsDAO.findAll().size();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        Assert.assertEquals(countAfterDelete, countBeforeDelete - 1);
    }


    @Test(dependsOnMethods = {"shouldDeleteRegions"})
    public void shouldFindAllRegions() {
        List<Region> regions = new ArrayList<>();

        try {
            regions.addAll(regionsDAO.findAll());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        logger.info("Regions count: " + regions.size());

        Assert.assertEquals(regions.size(), 4);
    }

    @Test()
    public void shouldFindMoreThanOneRegion() {
        List<Region> regions = new ArrayList<>();

        try {
            regions.addAll(regionsDAO.findByName("'Europe' OR 1 = 1"));
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        logger.info("Regions count: " + regions.size());

        Assert.assertTrue(regions.size() > 1);
    }

    @Test
    public void shouldSaveRegions() {
        Region firstRegion = new Region(55, "Testowy region 1");
        Region secondRegion = new Region(66, "Testowy region 2");
        Region thirdRegion = new Region(77, "Testowy region 3");

        List<Region> regionsFound = new ArrayList<>();

        try {
            regionsDAO.deleteBatch(asList(firstRegion, secondRegion,thirdRegion));

            regionsDAO.saveBatch(asList(firstRegion, secondRegion,thirdRegion));

            regionsFound = regionsDAO.findAll();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        Assert.assertTrue(regionsFound.size() > 1);
    }
}
