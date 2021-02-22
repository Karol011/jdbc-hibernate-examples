package com.sda.jdbc.todo;


import com.sda.jdbc.commons.connection.CustomConnection;
import com.sda.jdbc.commons.connection.MySqlConnector;
import com.sda.jdbc.commons.entity.Employee;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmployeesDAO {
    private final CustomConnection mySqlConnection;
    private static final Logger logger = Logger.getLogger(EmployeesDAO.class);

    public EmployeesDAO() {
        this.mySqlConnection = new MySqlConnector();
    }

    public Employee findById(int employeeId) {
        String query = "SELECT * FROM employees WHERE employee_id = ?";
        try (Connection connection = mySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info(query);

            if (resultSet.next()) {
                return toEntity(resultSet);
            }
            resultSet.close();

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public void delete(Employee employee) {
    }

    public void save(Employee employee) {
    }

    public void update(Employee employee) {
    }

    public List<Employee> findAll() {
        return null;
    }

    public void saveBatch(List<Employee> employees) {
    }

    public void deleteBatch(List<Employee> employees) {
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

}
