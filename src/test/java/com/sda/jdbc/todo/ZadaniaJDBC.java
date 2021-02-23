package com.sda.jdbc.todo;

import com.sda.jdbc.commons.connection.CustomConnection;
import com.sda.jdbc.commons.connection.MySqlConnector;
import com.sda.jdbc.commons.entity.Department;
import com.sda.jdbc.commons.entity.Employee;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class ZadaniaJDBC {

    private EmployeesDAO employeesDAO;
    private DepartmentsDAO departmentsDAO;

    public ZadaniaJDBC() {
        this.employeesDAO = new EmployeesDAO();
        this.departmentsDAO = new DepartmentsDAO();
    }

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
        Employee testEmployee = Employee.builder()
                .employeeId(250)
                .firstName("testName")
                .lastName("testLastName")
                .email("testEmail123")
                .hireDate(LocalDate.of(2001, 03, 02))
                .salary(6000D)
                .commissionPct(0.2)
                .departmentId(50)
                .jobId("AD_VP")
                .managerId(120)
                .phoneNumber("666.404.6667")
                .build();

        employeesDAO.save(testEmployee);

        Employee savedEmployee = employeesDAO.findById(250);
        Assert.assertNotNull(savedEmployee);
    }

    @Test(testName = "zad. 4 - Usuń pracownika na podstawie dowolnych parametrów, np. identyfikator")

    public void shouldDeleteEmployee() {
        Employee employee = Employee.builder()
                .employeeId(250)
                .build();

        employeesDAO.delete(employee);

        Assert.assertNull(employeesDAO.findById(employee.getEmployeeId()));
    }

    @Test(testName = "zad. 5 - Zmień dane pracownika np. e-mail")
    public void shouldUpdateEmployeeEmail() {
        Employee employeeBeforeUpdate = employeesDAO.findById(250);

        employeesDAO.update(employeeBeforeUpdate);

        Employee employeeAfterUpdate = employeesDAO.findById(250);

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
        Assert.assertEquals(1, 0);
    }

    @Test(testName = "zad. 8 - Usuń wielu pracowników przy pomocy jednego zapytania")
    public void shouldDeleteBatchEmployees() {
        Assert.assertEquals(1, 0);
    }

    @Test(testName = "zad. 9 - Policz pracowników danego działu na podstawie jego identyfikatora")
    public void shouldCountEmployeesByDepartmentId() {
        int employeesCount = departmentsDAO.countEmployeesByDepartmentId(50);

        Assert.assertEquals(employeesCount, 45);
    }

    @Test(testName = "zad. 10 - Znajdź dane kierownika działu na podstawie identyfikatora działu")
    public void shouldFindManagerByDepartmentId() {
        Employee departmentManager = departmentsDAO.findManagerByDepartmentId(50);

        Assert.assertNotNull(departmentManager);
    }

    @Test(testName = "zad. 11 - Znajdź szczegółowe informacje o dziale (w tym lokalizacja, kraj ,kontynent) na podstawie jego idenftyfikatora")
    public void shouldFindDetailsAboutDepartmentById() {
        Department detailsAboutDepartmentById = departmentsDAO.findDetailsAboutDepartmentById(50);

        Assert.assertNotNull(detailsAboutDepartmentById);
    }
}
