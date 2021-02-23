package com.sda.jdbc.todo;


import com.sda.jdbc.commons.connection.CustomConnection;
import com.sda.jdbc.commons.connection.MySqlConnector;
import com.sda.jdbc.commons.entity.Employee;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;

@Log4j
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
        String query = "DELETE FROM employees WHERE employee_id = ?";
        try (Connection connection = mySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, employee.getEmployeeId());
            preparedStatement.executeUpdate();

            //  resultSet.close();

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void save(Employee employee) {
        String query = "INSERT INTO employees (employee_id, first_name, last_name, email, phone_number, hire_date, " +
                "job_id, salary, commission_pct, manager_id, department_id) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection connection = mySqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            fromEntity(preparedStatement, employee);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
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

//first Name
        if (employee.getFirstName() == null)
            statement.setNull(2, Types.VARCHAR);
        else
            statement.setString(2, employee.getFirstName());
//lastName
        if (employee.getLastName() == null)
            statement.setNull(3, Types.VARCHAR);
        else
            statement.setString(3, employee.getLastName());
//email
        if (employee.getEmail() == null)
            statement.setNull(4, Types.VARCHAR);
        else
            statement.setString(5, employee.getPhoneNumber());
//phoneNumber
        if (employee.getPhoneNumber() == null)
            statement.setNull(5, Types.VARCHAR);
        else
            statement.setString(5, employee.getPhoneNumber());
//hireDate
        if (employee.getHireDate() == null)
            statement.setNull(6, Types.DATE);
        else
            statement.setDate(6, Date.valueOf(employee.getHireDate()));
//managerID
        if (employee.getManagerId() == null)
            statement.setNull(10, Types.INTEGER);
        else
            statement.setInt(10, employee.getManagerId());
//departmentID
        if (employee.getDepartmentId() == null)
            statement.setNull(11, Types.INTEGER);
        else
            statement.setInt(11, employee.getDepartmentId());


        return statement;
    }
}
