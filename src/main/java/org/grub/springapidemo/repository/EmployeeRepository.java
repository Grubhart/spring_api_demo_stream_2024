package org.grub.springapidemo.repository;

import org.grub.springapidemo.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;



public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
