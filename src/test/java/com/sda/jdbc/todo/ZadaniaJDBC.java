package com.sda.jdbc.todo;

import com.sda.jdbc.commons.connection.CustomConnection;
import com.sda.jdbc.commons.connection.MySqlConnector;
import com.sda.jdbc.commons.entity.Department;
import com.sda.jdbc.commons.entity.Employee;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static java.util.Arrays.asList;

public class ZadaniaJDBC {

    private EmployeesDAO employeesDAO;
    private DepartmentsDAO departmentsDAO;

    @Test(testName = "zad. 1 - Połącz się ze swoim serwerem bazy danych MySQL")
    public void shouldConnectToDatabase() throws SQLException {
        CustomConnection mySqlConnection = new MySqlConnector();
        Connection connection = mySqlConnection.getConnection();

        Assert.assertNotNull(connection);
        Assert.assertNotNull(connection.getMetaData().getURL());
    }

    @Test(testName = "zad. 2 - Wyszukaj pracownika po identyfikatorze")
    public void shouldFindEmployeeById() {
        Employee result = employeesDAO.findById(100);

        Assert.assertNotNull(result);
    }

    @Test(testName = "zad. 3 - Dodaj nowego pracownika")
    public void shouldSaveEmployee() {
        Employee employeeToSave = new Employee();

        employeesDAO.save(employeeToSave);
    }

    @Test(testName = "zad. 4 - Usuń pracownika na podstawie dowolnych parametrów, np. identyfikator")
    public void shouldDeleteEmployee() {
        Employee employee = new Employee();

        employeesDAO.delete(employee);

        Employee deletedEmployee = employeesDAO.findById(0);

        Assert.assertNull(deletedEmployee);
    }

    @Test(testName = "zad. 5 - Zmień dane pracownika np. e-mail")
    public void shouldUpdateEmployeeEmail() {
        Employee employeeBeforeUpdate = employeesDAO.findById(100);

        employeesDAO.update(employeeBeforeUpdate);

        Employee employeeAfterUpdate = employeesDAO.findById(100);

        Assert.assertNotNull(employeeBeforeUpdate);
        Assert.assertNotNull(employeeAfterUpdate);
    }

    @Test(testName = "zad. 6 - Znajdź wszystkich pracowników")
    public void shouldFindAllEmployees() {
        List<Employee> result = employeesDAO.findAll();

        Assert.assertNotNull(result);
    }

    @Test(testName = "zad. 7 - Dodaj wielu pracowników przy pomocy jednego zapytania")
    public void shouldSaveBatchEmployees() {
        Employee firstEmployee = new Employee();
        Employee secondEmployee = new Employee();

        employeesDAO.saveBatch(asList(firstEmployee, secondEmployee));
    }

    @Test(testName = "zad. 8 - Usuń wielu pracowników przy pomocy jednego zapytania")
    public void shouldDeleteBatchEmployees() {
        Employee firstEmployee = new Employee();
        Employee secondEmployee = new Employee();

        employeesDAO.deleteBatch(asList(firstEmployee, secondEmployee));
    }

    @Test(testName = "zad. 9 - Policz pracowników danego działu na podstawie jego identyfikatora")
    public void shouldCountEmployeesByDepartmentId() {
        int employeesCount = departmentsDAO.countEmployeesByDepartmentId(0);
    }

    @Test(testName = "zad. 10 - Znajdź dane kierownika działu na podstawie identyfikatora działu")
    public void shouldFindManagerByDepartmentId() {
        Employee departmentManager = departmentsDAO.findManagerByDepartmentId(0);
    }

    @Test(testName = "zad. 11 - Znajdź szczegółowe informacje o dziale (w tym lokalizacja, kraj ,kontynent) na podstawie jego idenftyfikatora")
    public void shouldFindDetailsAboutDepartmentById() {
        Department detailsAboutDepartmentById = departmentsDAO.findDetailsAboutDepartmentById(0);
    }
}
