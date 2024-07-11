package org.grub.springapidemo.service;

import org.grub.springapidemo.domain.Employee;

import java.util.List;

public interface EmployeeService {

    public Employee getEmployeeById(long id);
    public List<Employee> getAllEmployees();
    public Employee saveEmployee(Employee employee);


    public Employee replaceEmployee(Employee newEmployee, Long id);
    public void deleteEmployee(long id);
}
