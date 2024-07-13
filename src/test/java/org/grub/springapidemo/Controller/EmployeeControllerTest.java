package org.grub.springapidemo.Controller;


import org.grub.springapidemo.domain.Employee;
import org.grub.springapidemo.repository.EmployeeRepository;
import org.grub.springapidemo.repository.EmployeeRepositoryTestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeRepositoryTestUtil testUtil;


    @BeforeEach
    void setUp() {
        // inicializar BD
        employeeRepository.save(new Employee("Bilbo Baggins", "burglar"));
        employeeRepository.save(new Employee("Frodo Baggins", "thief"));

    }

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
        testUtil.resetIdentity();

    }

    @Test
    void getAllEmployeesTestOkResponse()  {

        ResponseEntity<EntityModel> response =  restTemplate.getForEntity("http://localhost:" + port +
                "/employees", EntityModel.class);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }


    @Test
    void getEmployeeNotFoundStsatusCode404()  {

        ResponseEntity<String> response =  restTemplate.getForEntity("http://localhost:" + port +
                "/employees/400", String.class);
        assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
    }

    @Test
    void getEmployeeFoundStsatusCode200()  {

        ResponseEntity<EntityModel> response =  restTemplate.getForEntity("http://localhost:" + port +
                "/employees/1", EntityModel.class);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }

}
