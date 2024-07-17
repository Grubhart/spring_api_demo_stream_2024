package org.grub.springapidemo;

import org.grub.springapidemo.controller.EmployeeController;
import org.grub.springapidemo.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PayrollApplicationTest {

    @Autowired
    private EmployeeController controller;

    static DockerImageName mysqlImage = DockerImageName.parse("container-registry.oracle.com/mysql/community-server:latest").asCompatibleSubstituteFor("mysql");


    static MySQLContainer<?> mySQL = new MySQLContainer<>(mysqlImage);

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        mySQL.start();

    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQL::getJdbcUrl);
        registry.add("spring.datasource.username", mySQL::getUsername);
        registry.add("spring.datasource.password", mySQL::getPassword);
    }

    @AfterAll
    static void tearDownAll() {
        mySQL.stop();
    }

    @Test
    void contextLoads() {
        assertNotNull(controller);
    }



}
