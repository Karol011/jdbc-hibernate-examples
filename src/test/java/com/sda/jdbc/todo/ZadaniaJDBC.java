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
        Employee employeeToSave = new Employee();
        employeeToSave.setEmployeeId(250);
        employeeToSave.setFirstName("Leo");
        employeeToSave.setLastName("Messi");
        employeeToSave.setEmail("leo.messi@wp.pl");
        employeeToSave.setSalary(50000.0);
        employeeToSave.setHireDate(LocalDate.now());
        employeeToSave.setCommissionPct(0.0);
        employeeToSave.setJobId("ST_MAN");

        employeesDAO.save(employeeToSave);

        Employee savedEmployee = employeesDAO.findById(250);
        Assert.assertNotNull(savedEmployee);
    }

    @Test(testName = "zad. 4 - Usuń pracownika na podstawie dowolnych parametrów, np. identyfikator", dependsOnMethods = "shouldUpdateEmployeeEmail")
    public void shouldDeleteEmployee() {
        Employee employee = new Employee();
        employee.setEmployeeId(250);

        employeesDAO.delete(employee);

        Employee deletedEmployee = employeesDAO.findById(250);

        Assert.assertNull(deletedEmployee);
    }

    @Test(testName = "zad. 5 - Zmień dane pracownika np. e-mail", dependsOnMethods = "shouldSaveEmployee")
    public void shouldUpdateEmployeeEmail() {
        Employee employeeBeforeUpdate = employeesDAO.findById(250);

        String oldEmailValue = employeeBeforeUpdate.getEmail();

        employeeBeforeUpdate.setEmail("leo.messi1988@gmail.com");
        employeesDAO.update(employeeBeforeUpdate);

        Employee employeeAfterUpdate = employeesDAO.findById(250);

        Assert.assertNotNull(employeeBeforeUpdate);
        Assert.assertNotNull(employeeAfterUpdate);
        Assert.assertNotEquals(employeeAfterUpdate.getEmail(), oldEmailValue);
    }

    @Test(testName = "zad. 6 - Znajdź wszystkich pracowników")
    public void shouldFindAllEmployees() {
        List<Employee> result = employeesDAO.findAll();

        Assert.assertNotNull(result);
    }

    @Test(testName = "zad. 7 - Dodaj wielu pracowników przy pomocy jednego zapytania")
    public void shouldSaveBatchEmployees() {
        Employee firstEmployee = Employee.builder().employeeId(300)
                .firstName("Robert").lastName("Lewandowski").email("lewy@wp.pl")
                .salary(50000.0)
                .hireDate(LocalDate.now())
                .commissionPct(0.0)
                .jobId("ST_MAN")
                .build();

        Employee secondEmployee = Employee.builder().employeeId(400)
                .firstName("Cristiano").lastName("Ronaldo").email("cristiano@wp.pl")
                .salary(60000.0)
                .hireDate(LocalDate.now())
                .commissionPct(0.0)
                .jobId("ST_MAN")
                .build();

        employeesDAO.saveBatch(asList(firstEmployee, secondEmployee));

        List<Integer> employeeIds = employeesDAO.findAll().stream()
                .map(Employee::getEmployeeId)
                .collect(Collectors.toList());

        Assert.assertTrue(employeeIds.contains(firstEmployee.getEmployeeId()));
        Assert.assertTrue(employeeIds.contains(secondEmployee.getEmployeeId()));
    }

    @Test(testName = "zad. 8 - Usuń wielu pracowników przy pomocy jednego zapytania", dependsOnMethods = "shouldSaveBatchEmployees")
    public void shouldDeleteBatchEmployees() {
        Employee firstEmployee = Employee.builder().employeeId(300)
                .firstName("Robert").lastName("Lewandowski").email("lewy@wp.pl")
                .salary(50000.0)
                .hireDate(LocalDate.now())
                .commissionPct(0.0)
                .jobId("ST_MAN")
                .build();

        Employee secondEmployee = Employee.builder().employeeId(400)
                .firstName("Cristiano").lastName("Ronaldo").email("cristiano@wp.pl")
                .salary(60000.0)
                .hireDate(LocalDate.now())
                .commissionPct(0.0)
                .jobId("ST_MAN")
                .build();

        employeesDAO.deleteBatch(asList(firstEmployee, secondEmployee));

        List<Integer> employeeIds = employeesDAO.findAll().stream()
                .map(Employee::getEmployeeId)
                .collect(Collectors.toList());

        Assert.assertFalse(employeeIds.contains(firstEmployee.getEmployeeId()));
        Assert.assertFalse(employeeIds.contains(secondEmployee.getEmployeeId()));
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
