package com.sda.jdbc.todo;


import com.sda.jdbc.commons.connection.CustomConnection;
import com.sda.jdbc.commons.connection.MySqlConnector;
import com.sda.jdbc.commons.entity.Employee;
import com.sda.jdbc.commons.entity.Region;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j
public class EmployeesDAO {

    private CustomConnection mySqlConnection;

    public EmployeesDAO() {
        this.mySqlConnection = new MySqlConnector();
    }

    public Employee findById(int employeeId) {
        String query = "SELECT * FROM employees WHERE employee_id = ?";

        try (Connection connection = mySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, employeeId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return toEntity(resultSet);
            }
        } catch (SQLException exception) {
            log.error(exception.getMessage());
        }

        return null;
    }

    public void delete(Employee employee) {
        String query = "DELETE FROM employees WHERE employee_id = ?";

        try (Connection connection = mySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, employee.getEmployeeId());

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            log.error(exception.getMessage());
        }
    }

    public void save(Employee employee) {
        String query = "INSERT INTO employees (employee_id, first_name, last_name, email, phone_number, hire_date, " +
                "job_id, salary, commission_pct, manager_id, department_id) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection connection = mySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            fromEntity(preparedStatement, employee);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            log.error(exception.getMessage());
        }
    }

    public void update(Employee employee) {
        String query = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, phone_number = ?, salary = ? WHERE employee_id = ?";

        try (Connection connection = mySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.setString(4, employee.getPhoneNumber());
            preparedStatement.setDouble(5, employee.getSalary());
            preparedStatement.setInt(6, employee.getEmployeeId());

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            log.error(exception.getMessage());
        }
    }

    public List<Employee> findAll() {
        List<Employee> result = new ArrayList<>();

        String query = "SELECT * FROM employees";

        try (Connection connection = mySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(toEntity(resultSet));
            }
        } catch (SQLException exception) {
            log.error(exception.getMessage());
        }

        return result;
    }

    public void saveBatch(List<Employee> employees) {
        String query = "INSERT INTO employees (employee_id, first_name, last_name, email, phone_number, hire_date, " +
                "job_id, salary, commission_pct, manager_id, department_id) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection connection = mySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (Employee employee : employees) {
                fromEntity(preparedStatement, employee);

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (SQLException exception) {
            log.error(exception.getMessage());
        }
    }

    public void deleteBatch(List<Employee> employees) {
        String query = "DELETE FROM employees WHERE employee_id = ? ";

        try (Connection connection = mySqlConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            for (Employee employee : employees) {
                statement.setInt(1, employee.getEmployeeId());

                statement.addBatch();
            }

            int[] deletedRecords = statement.executeBatch();

            log.info(query);
            log.info("Deleted records: " + Arrays.toString(deletedRecords));

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static Employee toEntity(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployeeId(resultSet.getInt("EMPLOYEE_ID"));
        employee.setFirstName(resultSet.getString("FIRST_NAME"));
        employee.setLastName(resultSet.getString("LAST_NAME"));
        employee.setEmail(resultSet.getString("EMAIL"));
        employee.setPhoneNumber(resultSet.getString("PHONE_NUMBER"));
        employee.setHireDate(resultSet.getDate("HIRE_DATE").toLocalDate());
        employee.setJobId(resultSet.getString("JOB_ID"));
        employee.setSalary(resultSet.getDouble("SALARY"));
        employee.setCommissionPct(resultSet.getDouble("COMMISSION_PCT"));
        employee.setManagerId(resultSet.getInt("MANAGER_ID"));
        employee.setDepartmentId(resultSet.getInt("DEPARTMENT_ID"));

        return employee;
    }

    private PreparedStatement fromEntity(PreparedStatement statement, Employee employee) throws SQLException {
        statement.setInt(1, employee.getEmployeeId());
        statement.setString(2, employee.getFirstName());
        statement.setString(3, employee.getLastName());
        statement.setString(4, employee.getEmail());
        statement.setString(5, employee.getPhoneNumber());
        statement.setDate(6, Date.valueOf(employee.getHireDate()));
        statement.setString(7, employee.getJobId());
        statement.setDouble(8, employee.getSalary());
        statement.setDouble(9, employee.getCommissionPct());

        if (employee.getManagerId() == null)
            statement.setNull(10, Types.INTEGER);
        else
            statement.setInt(10, employee.getManagerId());

        if (employee.getDepartmentId() == null)
            statement.setNull(11, Types.INTEGER);
        else
            statement.setInt(11, employee.getDepartmentId());

        return statement;
    }
}
