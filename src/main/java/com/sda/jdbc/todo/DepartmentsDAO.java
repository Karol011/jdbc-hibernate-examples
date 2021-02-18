package com.sda.jdbc.todo;


import com.sda.jdbc.commons.connection.CustomConnection;
import com.sda.jdbc.commons.connection.MySqlConnector;
import com.sda.jdbc.commons.entity.*;
import lombok.extern.log4j.Log4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Log4j
public class DepartmentsDAO {

    private CustomConnection mySqlConnection;

    public DepartmentsDAO() {
        this.mySqlConnection = new MySqlConnector();
    }

    public Department findById(int departmentId) {
        return null;
    }

    public void delete(Department department) {
    }

    public void save(Department department) {
    }

    public void update(Department department) {
    }

    public List<Department> findAll() {
        return null;
    }

    public void saveBatch(List<Department> departments) {
    }

    public void deleteBatch(List<Department> departments) {
    }

    public int countEmployeesByDepartmentId(int departmentId) {
        String query = "SELECT COUNT(employee_id) FROM employees WHERE department_id = ?";

        try (Connection connection = mySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, departmentId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException exception) {
            log.error(exception.getMessage());
        }

        return 0;
    }

    public Employee findManagerByDepartmentId(int departmentId) {
        String query = "SELECT * FROM departments d " +
                "JOIN employees e ON e.employee_id = d.manager_id " +
                "WHERE d.department_id = ?";

        try (Connection connection = mySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, departmentId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return EmployeesDAO.toEntity(resultSet);
            }
        } catch (SQLException exception) {
            log.error(exception.getMessage());
        }

        return null;
    }

    public Department findDetailsAboutDepartmentById(int departmentId) {
        String query = "SELECT * FROM departments d " +
                "JOIN locations l ON d.location_id = l.location_id " +
                "JOIN countries c ON l.country_id = c.country_id " +
                "JOIN regions r ON r.region_id = c.region_id " +
                "WHERE d.department_id = ?";

        try (Connection connection = mySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, departmentId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Department department = new Department();

                department.setDepartmentId(resultSet.getInt("DEPARTMENT_ID"));
                department.setDepartmentName(resultSet.getString("DEPARTMENT_NAME"));
                department.setManagerId(resultSet.getInt("MANAGER_ID"));
                department.setLocationId(resultSet.getInt("LOCATION_ID"));

                Location location = new Location();
                location.setCity(resultSet.getString("CITY"));

                Country country = new Country();
                country.setCountryName(resultSet.getString("COUNTRY_NAME"));

                Region region = new Region();
                region.setName(resultSet.getString("REGION_NAME"));

                country.setRegion(region);
                location.setCountry(country);
                department.setLocation(location);

                return department;
            }
        } catch (SQLException exception) {
            log.error(exception.getMessage());
        }

        return null;
    }
}
