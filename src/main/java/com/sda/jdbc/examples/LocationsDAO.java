package com.sda.jdbc.examples;

import com.sda.jdbc.commons.connection.CustomConnection;
import com.sda.jdbc.commons.entity.Location;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Log4j // więcej informacji na temat tej adnotacji: https://projectlombok.org/features/log
public class LocationsDAO {

    private final CustomConnection connector;

    public LocationsDAO(CustomConnection connector) {
        this.connector = connector;
    }

    public List<Location> findAll() throws SQLException {
        List<Location> result = new ArrayList<>();

        String query = "SELECT * FROM locations";

        try (Connection connection = connector.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Location location = new Location();
                location.setLocationId(resultSet.getInt("location_id"));
                location.setCity(resultSet.getString("city"));
                location.setPostalCode(resultSet.getString("postal_code"));
                location.setStreetAddress(resultSet.getString("street_address"));
                location.setStateProvince(resultSet.getString("state_province"));
                location.setCountryId(resultSet.getString("country_id"));

                result.add(location);
            }

            resultSet.close();
        } catch (SQLException exception) {
            log.error(exception.getMessage());
        }

        return result;
    }

    public void saveBatch(List<Location> locations) throws SQLException {
        String query = "INSERT INTO locations (location_id, city, postal_code, street_address,state_province,country_id) ";
        query = query + "VALUES (?,?,?,?,?,?)";

        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            for (Location location : locations) {
                statement.setInt(1, location.getLocationId());
                statement.setString(2, location.getCity());
                statement.setString(3, location.getPostalCode());
                statement.setString(4, location.getStreetAddress());
                statement.setString(5, location.getStateProvince());
                statement.setString(6, location.getCountryId());

                statement.addBatch();
            }

            statement.executeBatch();
        }
    }

    public void deleteBatch(List<Location> locations) throws SQLException {
        String query = "DELETE FROM locations WHERE location_id = ? ";

        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            for (Location location : locations) {
                statement.setInt(1, location.getLocationId());

                statement.addBatch();
            }

            statement.executeBatch();
        }
    }

    public List<Location> findByCityWithSQLInjection(String cityName) throws SQLException {
        List<Location> result = new ArrayList<>();

        String query = "SELECT * FROM locations WHERE city = " + cityName;

        try (Connection connection = connector.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Location location = new Location();
                location.setLocationId(resultSet.getInt("location_id"));
                location.setCity(resultSet.getString("city"));
                location.setPostalCode(resultSet.getString("postal_code"));
                location.setStreetAddress(resultSet.getString("street_address"));
                location.setStateProvince(resultSet.getString("state_province"));
                location.setCountryId(resultSet.getString("country_id"));

                result.add(location);
            }

            resultSet.close();
        } catch (SQLException exception) {
            log.error(exception.getMessage());
        }

        return result;
    }
}
