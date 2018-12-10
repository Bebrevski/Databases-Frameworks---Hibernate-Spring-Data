package org.softuni.ruk.service;

import org.softuni.ruk.repository.EmployeeRepository;
import org.softuni.ruk.service.interfaces.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeesServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeesServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public String importEmployees(String employeesFileContent) {
        return null;
    }
}
