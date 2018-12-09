package alararestaurant.service;

import alararestaurant.constants.Constants;
import alararestaurant.domain.dtos.EmployeeImportJsonDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Position;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.PositionRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final String EMPLOYEES_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/employees.json";

    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final FileUtil fileUtil;

    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PositionRepository positionRepository, FileUtil fileUtil, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        return this.fileUtil.readFile(EMPLOYEES_JSON_FILE_PATH);
    }

    @Override
    public String importEmployees(String employees) {
        StringBuilder importResult = new StringBuilder();

        EmployeeImportJsonDto[] employeeImportJsonDtos = this.gson.fromJson(employees, EmployeeImportJsonDto[].class);

        for (EmployeeImportJsonDto employeeImportJsonDto : employeeImportJsonDtos) {
            Position position = this.positionRepository
                    .findByName(employeeImportJsonDto.getPosition())
                    .orElse(null);

            if (position == null) {
                position = new Position();
                position.setName(employeeImportJsonDto.getPosition());
            }

            if (!this.validationUtil.isValid(employeeImportJsonDto) || !this.validationUtil.isValid(position)) {
                importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            if (this.positionRepository
                    .findByName(position.getName())
                    .orElse(null) == null) {
                position = this.positionRepository.saveAndFlush(position);
            }

            Employee employeeEntity = this.modelMapper.map(employeeImportJsonDto, Employee.class);
            employeeEntity.setPosition(position);

            employeeEntity = this.employeeRepository.saveAndFlush(employeeEntity);

            //Useless
            //position.getEmployees().add(employeeEntity);
            //this.positionRepository.saveAll(position.getEmployees());

            importResult
                    .append(String.format(Constants.SUCCESSFULLY_IMPORTED__EMPLOYEE_MESSAGE, employeeEntity.getName()))
                    .append(System.lineSeparator());
        }

        return importResult.toString();
    }
}
