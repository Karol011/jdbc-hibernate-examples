package com.sda.jdbc;

import com.sda.jdbc.connection.CustomConnection;
import com.sda.jdbc.entity.Location;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
}
