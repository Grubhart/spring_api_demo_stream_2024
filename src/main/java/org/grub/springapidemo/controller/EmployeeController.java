package org.grub.springapidemo.controller;


import org.apache.coyote.Response;
import org.grub.springapidemo.domain.Employee;
import org.grub.springapidemo.error.EmployeeNotFoundException;
import org.grub.springapidemo.repository.EmployeeRepository;
import org.grub.springapidemo.service.EmployeeService;
import org.grub.springapidemo.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeModelAssembler employeeModelAssembler;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeModelAssembler employeeModelAssembler1) {
        this.employeeService = employeeService;
        this.employeeModelAssembler = employeeModelAssembler1;
    }

    @GetMapping("/employees")
    CollectionModel<EntityModel<Employee>> all() {
        List<EntityModel<Employee>> employees = employeeService.getAllEmployees()
                .stream().map(employeeModelAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }

    @PostMapping("/employees")
    ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) {

        EntityModel<Employee> employeeModel = employeeModelAssembler.toModel(employeeService.saveEmployee(newEmployee));

        return  ResponseEntity.created(employeeModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(employeeModel);

    }

    // Single item

    @GetMapping("/employees/{id}")
    EntityModel<Employee> one(@PathVariable Long id) {

        Employee employee = employeeService.getEmployeeById(id);

        return employeeModelAssembler.toModel(employee);
    }

    @PutMapping("/employees/{id}")
    ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {


        Employee employee = employeeService.replaceEmployee(newEmployee, id);

        EntityModel<Employee> employeeModel = employeeModelAssembler.toModel(employee);

        return ResponseEntity.created(employeeModel.getRequiredLink(IanaLinkRelations.SELF).
                toUri()).body(employeeModel);
    }

    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
       employeeService.deleteEmployee(id);
    }
}