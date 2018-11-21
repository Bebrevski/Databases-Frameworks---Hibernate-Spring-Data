package app.dto;

import app.entities.Employee;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

public class EmployeeDTO {
    private String firstName;
    private String lastName;
    private BigDecimal salary;

    public EmployeeDTO() {
    }

    public EmployeeDTO(String firstName, String lastName, BigDecimal salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getSalary() {
        return this.salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public static void initMaps(ModelMapper mapper) {
        mapper.createTypeMap(Employee.class, EmployeeDTO.class)
                .addMapping(
                        Employee::getFirstName,
                        EmployeeDTO::setFirstName)
                .addMapping(
                        Employee::getLastName,
                        EmployeeDTO::setLastName
                )
                .addMapping(
                        Employee::getSalary,
                        EmployeeDTO::setSalary
                );
    }
}
