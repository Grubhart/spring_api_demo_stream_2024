package org.grub.springapidemo.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EmployeeTest {


    /*
    Dado que tengo un objeto Employee con name "Juan Perez"
    Cuando ejecuto el metodo getFirstName
    entonces devuelve la cadena "Juan"
     */
    @Test
    public void testEmployeeGetFirstName() {

        Employee employee = new Employee("Juan Perez", null);

        String firstNameObtained = employee.getFirstName();

        String firstNameExpected = "Juan";
        assertEquals(firstNameExpected, firstNameObtained);

    }


    /*
   Dado que tengo un objeto Employee con name "Juan Perez"
   Cuando ejecuto el metodo getLastName
   entonces devuelve la cadena "Perez"
    */
    @Test
    public void testEmployeeGetLastName() {

        Employee employee = new Employee("Juan Perez", null);

        String lastNameObtained = employee.getLastName();
        String lastNameExpected = "Perez";

        assertEquals(lastNameExpected, lastNameObtained);
    }

}
