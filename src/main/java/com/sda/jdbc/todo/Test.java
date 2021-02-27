package com.sda.jdbc.todo;

import com.sda.jdbc.commons.entity.Employee;

import java.time.LocalDate;
import java.util.List;

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
                .lastName("surname")
                .email("ttttt")
                .hireDate(LocalDate.of(2001, 3, 2))
                .salary(6000D)
                .commissionPct(0.2)
                .departmentId(50)
                .jobId("AD_VP")
                .managerId(120)
                .phoneNumber("666.404.6667")
                .build();

        Employee employee3 = Employee.builder()
                .employeeId(565)
                .firstName("yuuu")
                .lastName("surname")
                .email("564gg")
                .hireDate(LocalDate.of(2001, 3, 2))
                .salary(6000D)
                .commissionPct(0.2)
                .departmentId(50)
                .jobId("AD_VP")
                .managerId(120)
                .phoneNumber("666.404.6667")
                .build();

        Employee employee4 = Employee.builder()
                .employeeId(767)
                .firstName("tetttt")
                .lastName("surname")
                .email("yuyu")
                .hireDate(LocalDate.of(2001, 3, 2))
                .salary(6000D)
                .commissionPct(0.2)
                .departmentId(50)
                .jobId("AD_VP")
                .managerId(120)
                .phoneNumber("666.404.6667")
                .build();
       // employeesDAO.update(employee2);
       // System.out.println(employeesDAO.findById(employee2.getEmployeeId()).getEmail());
        //employeesDAO.findAll();
       // System.out.println(employeesDAO.findAll());
  //      employeesDAO.saveBatch(List.of(employee3,employee4));
        //employeesDAO.deleteBatch(List.of(employee2,employee3,employee4));

    }
}
