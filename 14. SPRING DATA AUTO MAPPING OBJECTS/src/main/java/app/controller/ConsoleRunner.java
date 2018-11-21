package app.controller;

import app.entities.Employee;
import app.services.addresses.AddressesService;
import app.services.employees.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class ConsoleRunner implements CommandLineRunner {
    private final EmployeeService employeeService;
    private final AddressesService addressesService;
    private final Scanner scanner;
    private final ModelMapper mapper;

    @Autowired
    public ConsoleRunner(EmployeeService employeeService, AddressesService addressesService, Scanner scanner, ModelMapper mapper) {
        this.employeeService = employeeService;
        this.addressesService = addressesService;
        this.scanner = scanner;
        this.mapper = mapper;
    }

    @Override
    public void run(String... args) throws Exception {
        this.employeeService.seedEmployees();
        this.addressesService.seedAddresses();

        this.employeeService.getAllEmployees()
                .forEach(System.out::println);


    }
}
