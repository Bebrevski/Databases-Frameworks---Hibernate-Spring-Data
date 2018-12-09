package alararestaurant.service;

import alararestaurant.constants.Constants;
import alararestaurant.domain.dtos.employees.EmployeeImportJsonDto;
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

        EmployeeImportJsonDto[] employeeImportJsonDto = this.gson.fromJson(employees, EmployeeImportJsonDto[].class);

        for (EmployeeImportJsonDto importJsonDto : employeeImportJsonDto) {
            if (!this.validationUtil.isValid(importJsonDto)) {
                importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Position positionEntity = this.positionRepository
                    .findByName(importJsonDto.getPosition())
                    .orElse(null);

            if (positionEntity == null) {
                positionEntity = new Position();
                positionEntity.setName(importJsonDto.getPosition());
                positionEntity = this.positionRepository.saveAndFlush(positionEntity);
            }

            Employee employeeEntity = this.modelMapper.map(importJsonDto, Employee.class);
            employeeEntity.setPosition(positionEntity);

            this.employeeRepository.saveAndFlush(employeeEntity);

            importResult
                    .append(String.format("Record %s successfully imported."
                            , employeeEntity.getName()))
                    .append(System.lineSeparator());
        }

        return importResult.toString();
    }
}
