package com.sda.jdbc.connection;

import com.sda.jdbc.properties.PropertiesReader;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*
 *   Klasa odpowiada za stworzenie połączenia do bazy danych H2.
 *   Implementuje interfejs CustomConnection wobec tego poprzez użycie metody getConnection
 *   powinniśmy otrzymać gotowy do użycia obiekt Connection.
 *   Więcej informacji na temat bazy danych H2: https://www.h2database.com/html/main.html
 *   oraz: https://www.baeldung.com/java-in-memory-databases
 */
public class H2Connector implements CustomConnection {

    private static final Logger logger = Logger.getLogger(H2Connector.class);

    private Properties properties;

    public H2Connector() {
        PropertiesReader propertiesReader = new PropertiesReader();
        this.properties = propertiesReader.loadFromFile("db.properties");
    }

    public Connection getConnection() {
        Connection connection;

        try {
            connection = DriverManager.getConnection(
                    properties.getProperty("H2_URL"));

            connection.setAutoCommit(true);

            return connection;

        } catch (SQLException e) {
            logger.error("Connection Failed! Check output console");
            logger.error(e);
        }

        return null;
    }
}
