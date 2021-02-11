package com.sda.jdbc.examples;

import java.sql.*;

/*
    Prosty przykład połączenia z bazą danych HR przy pomocy JDBC
*/
public class HelloJDBC {

    // URL do bazy danych
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/hr?useUnicode=true&" +
            "useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&" +
            "  serverTimezone=UTC&verifyServerCertificate=false&useSSL=true&requireSSL=true";

    // Dane do logowania - użytkownik i hasło
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //1: Tworzymy połaczenie
            System.out.println("Łączenie z bazą danych...");
            conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);

            //2: Tworzenie i wykonanie zapytania
            System.out.println("Tworzenie zapytania...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT EMPLOYEE_ID, FIRST_NAME, LAST_NAME, SALARY FROM employees";
            ResultSet rs = stmt.executeQuery(sql);

            //3: Przetwarzamy rezultat zapytania - ResultSet
            while (rs.next()) {
                //Wyciągamy dane z poszczególnych kolumn
                int id = rs.getInt("EMPLOYEE_ID");
                int salary = rs.getInt("SALARY");
                String firstName = rs.getString("FIRST_NAME");
                String lastName = rs.getString("LAST_NAME");

                //Wyświetlamy wartości
                System.out.print("Id: " + id);
                System.out.print(", Imię: " + firstName);
                System.out.println(", Nazwisko: " + lastName);
                System.out.println(", Wynagrodzenie: " + salary);
            }
            //4: Sprzątanie środowiska
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception se) {
            //Przechwytywanie wyjątków JDBC
            se.printStackTrace();
        }
        finally {
            //zwolnienie zasobów związanych z połączeniem
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException ignored) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Koniec!");
    }
}
