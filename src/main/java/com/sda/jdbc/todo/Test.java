package com.sda.jdbc.todo;

import com.sda.jdbc.commons.entity.Employee;

import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {
        EmployeesDAO employeesDAO = new EmployeesDAO();
        Employee employee = Employee.builder()
                .employeeId(666)
                .firstName("testName")
                .lastName("testLastName")
                .email("testEmaiul123")
                .hireDate(LocalDate.of(2001, 3, 2))
                .salary(6000D)
                .commissionPct(0.2)
                .departmentId(50)
                .jobId("AD_VP")
                .managerId(120)
                .phoneNumber("666.404.6667")
                .build();
        //employeesDAO.save(employee);
       // employeesDAO.findById(employee.getEmployeeId());

        Employee employee2 = Employee.builder()
                .employeeId(666)
                .firstName("testName")
                .lastName("testLastName")
                .email("changedEmail")
                .hireDate(LocalDate.of(2001, 3, 2))
                .salary(6000D)
                .commissionPct(0.2)
                .departmentId(50)
                .jobId("AD_VP")
                .managerId(120)
                .phoneNumber("666.404.6667")
                .build();
        employeesDAO.update(employee2);
     //   System.out.println(employeesDAO.findById(employee2.getEmployeeId()).getEmail());

    }
}
