package com.sda.jdbc;

import com.sda.jdbc.connection.CustomConnection;
import com.sda.jdbc.entity.Region;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 *   Przykład klasy do obsługi zapisu, odczytu, wyszukiwania danych o kontynentach z tabeli REGIONS z bazy HR
 *   napisanej w oparciu o wzorzec DAO (więcej na temat tego wzorca: https://www.baeldung.com/java-dao-pattern).
 */
public class RegionsDAO {

    /*
     *   Zamiast używać System.out.println możemy skorzystać z loggera.
     *   Więcej informacji na ten temat: https://www.baeldung.com/java-system-out-println-vs-loggers .
     */
    private static final Logger logger = Logger.getLogger(RegionsDAO.class);

    /*
     *   Tworząc obiekt klasy RegionsDAO nie chcemy jej wiązać z konkretną implementacją połączenia do bazy danych.
     *   Wobec tego zamiast używać obiektów klas z konkretną implementacją połączenia (np. MySqlConnector lub H2Connector)
     *   możemy skorzystać z polimorfizmu (więcej na ten temat tutaj: https://dzone.com/articles/learning-java-what-vs-why).
     *   Następnie dzięki temu będziemy w stanie użyć naszej klasy do zarządzania danymi o kontynentach zarówno po połączeniu
     *   do bazy MySQL, Oracle jak i H2. Oczywiście wszystko będzie działać dopóki nasze zapytania SQL będą kompatybilne z wszystkimi
     *   typami serwerów do których będziemy się łączyć. W przypadku bardziej skomplikowanych zapytań SQL trzeba po prostu pisać
     *   różne implementacje dla zapytania SQL do danej metody w zależności od bazy danych z którą się łączymy.
     */
    private final CustomConnection connector;

    public RegionsDAO(CustomConnection connector) {
        this.connector = connector;
    }

    // Wyszukiwanie kontynentu po nazwie.
    public List<Region> findByName(String name) throws SQLException {

        /*
         *    Budujemy zapytanie SQL, które wyszuka kontynent o podanej nazwie.
         *    Warto zwrócić uwagę, że parametr metody - name po prostu 'doklejamy' do naszego zapytania SQL.
         *    W pewnych sytuacjach taki sposób przekazywania parametrów może byc niebezpieczny dlatego, że otwiera furtkę
         *    do tzw. SQL Injections (więcej na ten temat tutaj: https://www.developer.com/db/how-to-protect-a-jdbc-application-against-sql-injection.html).
         */
        String query = "SELECT region_id, region_name FROM regions " +
                "WHERE region_name = " + name;

        /*
         *   Przy pomocy konstrukcji try with resources (więcej na ten temat tutaj: https://www.baeldung.com/java-try-with-resources)
         *   inicjujemy połączenie z bazą danych poprzez wywołanie metody getConnection z klasy implementującej interfejs CustomConnection.
         *   Następnie gdy mamy już połączenie możemy przy pomocy metody createStatement rozpocząć budowanie zapytania do bazy danych.
         */
        try (Connection connection = connector.getConnection();
             Statement statement = connection.createStatement()) {

            /*
             *   Przy pomocy naszego loggera na poziomie info podczas uruchomienia naszej metody będziemy w stanie zobaczyć
             *   zbudowane zapytanie np. w konsoli terminala. Więcej o poziomach logowania tutaj: https://www.tutorialspoint.com/log4j/log4j_logging_levels.htm
             */
            logger.info(query);

            /*
             *   Wykorzystujemy przygotowany wcześniej obiekt Statement i wywołujemy metodę executeQuery,
             *   gdzie przekazujemy zapytanie SQL. W rezultacie otrzymujemy obiekt klasy ResultSet.
             */
            ResultSet rs = statement.executeQuery(query);

            // deklarujemy listę obiektów klasy Region, która zostanie wypełniona danymi otrzymanymi w wyniku wykonanego zapytania
            List<Region> result = new ArrayList<>();

            // po obiekcie ResultSet możemy poruszać się przy pomocy pętli i poprzez wywołanie metody next analizować kolejne elementy wyniku
            while (rs.next()) {

                /*
                 *    W klasie ResultSet jest mnóstwo metod, które umożliwiają odczytanie wartości kolumny z bazy danych.
                 *    Należy użyć odpowiedniej metody  w zależności od typu jakiego jest kolumna w tabeli.
                 */
                int id = rs.getInt("region_id");
                String regionName = rs.getString("region_name");

                // zapełniamy zadeklarowaną wcześniej listę obiektów klasy Region
                result.add(new Region(id, regionName));
            }

            // po zakończeniu działania na ResultSet trzeba zadbać o to aby został zamknięty przy pomocy metody Close.
            rs.close();

            return result;
        } catch (SQLException e) {

            /*
             *   W sekcji catch mamy możliwość 'złapania' wyjątków, które mogą wystąpić podczas działania na bazie danych.
             *   Logujemy te informacje na poziomie error.
             */
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    public Region findById(int regionId) throws SQLException {

        String query = "SELECT region_id, region_name FROM regions " +
                "WHERE region_id = ?";

        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, regionId);

            logger.info(query);

            // execute select SQL statement
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("region_id");
                String name = rs.getString("region_name");

                return new Region(id, name);
            }

            rs.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    public List<Region> findAll() throws SQLException {

        String query = "SELECT * FROM regions ";

        List<Region> regions = new ArrayList<>();

        try (Connection connection = connector.getConnection();
             Statement statement = connection.createStatement()) {

            logger.info(query);

            // execute select SQL stetement
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("region_id");
                String name = rs.getString("region_name");

                regions.add(new Region(id, name));
            }

            rs.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return regions;
    }

    public void save(Region region) throws SQLException {

        String query = "INSERT INTO regions(region_id, region_name)  " +
                "VALUES (?, ?)";

        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, region.getId());
            statement.setString(2, region.getName());

            statement.executeUpdate();

            logger.info(query);

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void delete(int regionId) throws SQLException {
        String query = "DELETE FROM regions WHERE region_id = ? ";

        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, regionId);

            int deletedRecords = statement.executeUpdate();

            logger.info(query);
            logger.info("Deleted records: " + deletedRecords);

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void update(Region region) throws SQLException {

        String query = "UPDATE regions " +
                "SET region_name = ? WHERE region_id = ?";

        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, region.getName());
            statement.setInt(2, region.getId());

            int updatedRecords = statement.executeUpdate();

            logger.info(query);
            logger.info("Updated records: " + updatedRecords);

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void saveBatch(List<Region> regions) throws SQLException {
        String query = "INSERT INTO regions(region_id, region_name)  " +
                "VALUES (?, ?)";

        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            for (Region region : regions) {
                statement.setInt(1, region.getId());
                statement.setString(2, region.getName());

                statement.addBatch();
            }

            statement.executeBatch();
        } catch (SQLException exception) {
            logger.error(exception.getMessage(), exception);
        }
    }

    public void deleteBatch(List<Region> regions) throws SQLException {

        String query = "DELETE FROM regions WHERE region_id = ? ";

        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            for (Region region : regions) {
                statement.setInt(1, region.getId());

                statement.addBatch();
            }

            int[] deletedRecords = statement.executeBatch();

            logger.info(query);
            logger.info("Deleted records: " + deletedRecords);

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
