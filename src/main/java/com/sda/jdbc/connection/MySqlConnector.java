package com.sda.jdbc.connection;

import com.sda.jdbc.properties.PropertiesReader;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*
*   Klasa odpowiada za stworzenie połączenia do bazy danych MySQL.
*   Implementuje interfejs CustomConnection wobec tego poprzez użycie metody getConnection
*   powinniśmy otrzymać gotowy do użycia obiekt Connection.
 */
public class MySqlConnector implements CustomConnection {

    private static final Logger logger = Logger.getLogger(MySqlConnector.class);

    // Więcej na temat używania Properties tutaj: https://www.baeldung.com/java-properties
    private Properties properties;

    public MySqlConnector() {
        /*
        *   Wczytujemy parametry połączenia do bazy danych z pliku db.properties
        *   przy pomocy klasy pomocniczej PropertiesReader
         */
        PropertiesReader propertiesReader = new PropertiesReader();
        this.properties = propertiesReader.loadFromFile("db.properties");
    }

    public Connection getConnection() {
        /*
        *   Przy pomocy wczytanych wcześniej parametrów przygotowujemy połączenie do bazy danych MySQL.
        *   Wywołujemy metodę getConnection z klasy DriverManager i przekazujemy potrzebne informacje o URL, username i password.
         */

        Connection connection;

        try {
            connection = DriverManager.getConnection(
                    properties.getProperty("MYSQL_URL"),
                    properties.getProperty("MYSQL_USERNAME"),
                    properties.getProperty("MYSQL_PASSWORD"));

            connection.setAutoCommit(true);

            return connection;

        } catch (SQLException e) {
            logger.error("Connection Failed! Check output console");
            logger.error(e);
        }

        return null;
    }
}
