package com.sda.jdbc.examples.h2;

import com.sda.jdbc.examples.RegionsDAO;
import com.sda.jdbc.commons.connection.CustomConnection;
import com.sda.jdbc.commons.connection.H2Connector;
import com.sda.jdbc.commons.entity.Region;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class RegionJdbcDaoH2Test {

    private static final Logger logger = Logger.getLogger(RegionJdbcDaoH2Test.class);

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
        List<Region> regions = new ArrayList<>();

        regions.addAll(regionsDAO.findAll());

        logger.info("Regions count: " + regions.size());

        Assert.assertEquals(regions.size(), 4);
    }
}
