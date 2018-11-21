package app.services.employees;

import app.dto.EmployeeDTO;
import app.entities.Employee;
import app.repositories.EmployeeRepository;
import app.util.FileUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    private static final String EMPLOYEE_FILE_PATH = "E:\\SoftUni\\Databases Frameworks - Hibernate & Spring Data\\14. SPRING DATA AUTO MAPPING OBJECTS\\src\\main\\resources\\files\\employees";

    private final EmployeeRepository employeeRepository;
    private final FileUtil fileUtil;
    private final ModelMapper mapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, FileUtil fileUtil, ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.fileUtil = fileUtil;
        this.mapper = mapper;
    }

    @Override
    public void seedEmployees() throws IOException {
        if (this.employeeRepository.count() != 0) {
            return;
        }

        String[] authorFileContent = this.fileUtil.getFileContent(EMPLOYEE_FILE_PATH);
        for (String line : authorFileContent) {
            String[] names = line.split("\\s+");

            Employee employee = new Employee();
            employee.setFirstName(names[0]);
            employee.setLastName(names[1]);
            employee.setSalary(new BigDecimal(names[2]));

            this.employeeRepository.saveAndFlush(employee);
        }
    }

    @Override
    public List<String> getAllEmployees() {
        return this.employeeRepository.findAllBy()
                .stream()
                .map(Employee::getFirstName)
                .collect(Collectors.toList());
    }
}
