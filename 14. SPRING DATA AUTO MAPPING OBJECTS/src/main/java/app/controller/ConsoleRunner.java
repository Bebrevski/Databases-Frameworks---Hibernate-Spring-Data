package app.controller;

import app.services.addresses.AddressesService;
import app.services.employees.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class ConsoleRunner implements CommandLineRunner {
    private final EmployeeService employeeService;
    private final AddressesService addressesService;
    private final Scanner scanner;

    @Autowired
    public ConsoleRunner(EmployeeService employeeService, AddressesService addressesService, Scanner scanner) {
        this.employeeService = employeeService;
        this.addressesService = addressesService;
        this.scanner = scanner;
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
