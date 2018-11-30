package app.ccb.services;

import app.ccb.domain.dtos.EmployeeImportDto;
import app.ccb.domain.entities.Branch;
import app.ccb.domain.entities.Client;
import app.ccb.domain.entities.Employee;
import app.ccb.repositories.BranchRepository;
import app.ccb.repositories.EmployeeRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final String EMPLOYEES_JSON_FILE_PATH = "E:\\SoftUni\\Databases Frameworks - Hibernate & Spring Data\\18. WORKSHOP – MVC PROJECT SPRING MVC + SPRING DATA\\src\\main\\resources\\files\\json\\employees.json";

    private final EmployeeRepository employeeRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final BranchRepository branchRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, FileUtil fileUtil, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper, BranchRepository branchRepository) {
        this.employeeRepository = employeeRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.branchRepository = branchRepository;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() != 0;
    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        return this.fileUtil.readFile(EMPLOYEES_JSON_FILE_PATH);
    }

    @Override
    public String importEmployees(String employees) {
        StringBuilder sb = new StringBuilder();

        EmployeeImportDto[] employeeImportDtos = this.gson
                .fromJson(employees, EmployeeImportDto[].class);

        for (EmployeeImportDto employeeImportDto : employeeImportDtos) {
            if (!this.validationUtil.isValid(employeeImportDto)) {
                sb.append("Incorrect Data!").append(System.lineSeparator());
                continue;
            }

            Branch branchEntity = this.branchRepository
                    .findByName(employeeImportDto.getBranchName())
                    .orElse(null);

            if (branchEntity == null) {
                sb.append("Incorrect Data!").append(System.lineSeparator());
                continue;
            }

            Employee entity = this.modelMapper.map(employeeImportDto, Employee.class);
            entity.setFirstName(employeeImportDto.getFullName().split("\\s+")[0]);
            entity.setLastName(employeeImportDto.getFullName().split("\\s+")[1]);
            entity.setStartedOn(LocalDate.parse(employeeImportDto.getStartedOn())); // If necessary ... ,DateTimeFormatter.ofPattern("yyyy-MM-dd");
            entity.setBranch(branchEntity);

            this.employeeRepository.saveAndFlush(entity);

            sb
                    .append(String.format("Successfully imported Employee- %s %s"
                            , entity.getFirstName()
                            , entity.getLastName()))
                    .append(System.lineSeparator());

        }

        return sb.toString();
    }

    @Override
    public String exportTopEmployees() {
        List<Employee> employeesEntities = this.employeeRepository.extractTopEmployees();

        StringBuilder sb = new StringBuilder();

        for (Employee employeesEntity : employeesEntities) {
            sb
                    .append(String.format("Full Name: %s %s"
                            , employeesEntity.getFirstName()
                            , employeesEntity.getLastName()))
                    .append(System.lineSeparator())
                    .append(String.format("Salary: %.2f", employeesEntity.getSalary()))
                    .append(System.lineSeparator())
                    .append(String.format("Started On: %s", String.valueOf(employeesEntity.getStartedOn())))
                    .append(System.lineSeparator())
                    .append("Clients: ")
                    .append(System.lineSeparator());

            for (Client client : employeesEntity.getClients()) {
                sb
                        .append(String.format("\t%s", client.getFullName()))
                        .append(System.lineSeparator());
            }
        }

        return sb.toString();
    }
}
