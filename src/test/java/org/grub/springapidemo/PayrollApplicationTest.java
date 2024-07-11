package org.grub.springapidemo;

import org.grub.springapidemo.controller.EmployeeController;
import org.grub.springapidemo.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PayrollApplicationTest {

    @Autowired
    private EmployeeController controller;

    @Test
    void contextLoads() {
        assertNotNull(controller);
    }



}
