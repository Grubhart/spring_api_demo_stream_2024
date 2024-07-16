package org.grub.springapidemo.Controller;


import org.grub.springapidemo.domain.Employee;
import org.grub.springapidemo.repository.EmployeeRepository;
import org.grub.springapidemo.repository.EmployeeRepositoryTestUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

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


    static  DockerImageName mysqlImage = DockerImageName.parse("container-registry.oracle.com/mysql/community-server:latest").asCompatibleSubstituteFor("mysql");


    static MySQLContainer<?> mySQL = new MySQLContainer<>(mysqlImage);

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        mySQL.start();

    }

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

    @AfterAll
    static void tearDownAll() {
        mySQL.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQL::getJdbcUrl);
        registry.add("spring.datasource.username", mySQL::getUsername);
        registry.add("spring.datasource.password", mySQL::getPassword);
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
