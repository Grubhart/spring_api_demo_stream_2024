package org.grub.springapidemo.Controller;


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
