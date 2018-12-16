package org.softuni.ruk.service;

import com.google.gson.Gson;
import org.softuni.ruk.domain.dto.EmployeeImportJsonDto;
import org.softuni.ruk.domain.entity.Branch;
import org.softuni.ruk.domain.entity.Employee;
import org.softuni.ruk.repository.BranchRepository;
import org.softuni.ruk.repository.EmployeeRepository;
import org.softuni.ruk.service.interfaces.EmployeeService;
import org.softuni.ruk.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, BranchRepository branchRepository, Gson gson, ValidationUtil validationUtil) {
        this.employeeRepository = employeeRepository;
        this.branchRepository = branchRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public String importEmployees(String employeesFileContent) {
        StringBuilder importResult = new StringBuilder();

        EmployeeImportJsonDto[] employeeImportJsonDtos = this.gson.fromJson(employeesFileContent, EmployeeImportJsonDto[].class);

        for (EmployeeImportJsonDto employeeImportJsonDto : employeeImportJsonDtos) {

            Branch branchEntity = this.branchRepository
                    .findByName(employeeImportJsonDto.getBranchName())
                    .orElse(null);

            if (!this.validationUtil.isValid(employeeImportJsonDto) || branchEntity == null) {
                importResult.append("Error: Invalid data!").append(System.lineSeparator());
                continue;
            }

            Employee employeeEntity = new Employee();
            employeeEntity.setFirstName(employeeImportJsonDto.getFullName().split("\\s+")[0]);
            employeeEntity.setLastName(employeeImportJsonDto.getFullName().split("\\s+")[1]);
            employeeEntity.setSalary(employeeImportJsonDto.getSalary());
            employeeEntity.setStartedOn(LocalDate.parse(employeeImportJsonDto.getStartedOn(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            employeeEntity.setBranch(branchEntity);

            this.employeeRepository.saveAndFlush(employeeEntity);

            importResult.append(String.format("Successfully imported %s - %s"
                    , employeeEntity.getClass().getSimpleName()
                    , employeeImportJsonDto.getFullName()))
                    .append(System.lineSeparator());
        }

        return importResult.toString();
    }
}
