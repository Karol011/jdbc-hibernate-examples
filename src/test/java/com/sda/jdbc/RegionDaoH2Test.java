package com.sda.jdbc;

import com.sda.jdbc.connection.CustomConnection;
import com.sda.jdbc.connection.H2Connector;
import com.sda.jdbc.entity.Region;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class RegionDaoH2Test {

    private static final Logger logger = Logger.getLogger(RegionDaoH2Test.class);

    private RegionsDAO regionsDAO;
    private CustomConnection h2Connection;

    @BeforeClass
    public void setUp() {
        h2Connection = new H2Connector();
        regionsDAO = new RegionsDAO(h2Connection);

        try {
            PreparedStatement statement =
                    h2Connection.getConnection().prepareStatement("runscript from 'classpath:/hr.sql'");
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
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
}
