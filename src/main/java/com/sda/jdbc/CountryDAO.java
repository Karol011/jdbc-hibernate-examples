package com.sda.jdbc;

import com.sda.jdbc.connection.CustomConnection;
import com.sda.jdbc.entity.Country;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Log4j // więcej informacji na temat tej adnotacji: https://projectlombok.org/features/log
public class CountryDAO {

    private final CustomConnection connector;

    public CountryDAO(CustomConnection connector) {
        this.connector = connector;
    }

    public List<Country> findCountryByName(String countryName) throws SQLException {
        List<Country> result = new ArrayList<>();

        String query = "{ CALL getCountryByName(?) }";

        try (Connection connection = connector.getConnection();
             CallableStatement statement = connection.prepareCall(query)) {

            statement.setString(1, countryName);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Country country = new Country();
                country.setCountryId(resultSet.getString("country_id"));
                country.setCountryName(resultSet.getString("country_name"));

                result.add(country);
            }

            resultSet.close();
        } catch (SQLException exception) {
            log.error(exception.getMessage());
        }

        return result;
    }
}
