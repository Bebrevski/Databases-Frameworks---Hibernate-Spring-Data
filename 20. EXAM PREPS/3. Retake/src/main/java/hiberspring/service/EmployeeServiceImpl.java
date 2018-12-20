package hiberspring.service;

import hiberspring.common.Constants;
import hiberspring.domain.dtos.employee.EmployeeImportDto;
import hiberspring.domain.dtos.employee.EmployeeImportRootDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Employee;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.repository.EmployeeRepository;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final String EMPLOYEE_XML_FILE_PATH = Constants.PATH_TO_FILES + "employees.xml";

    private final EmployeeRepository employeeRepository;
    private final EmployeeCardRepository employeeCardRepository;
    private final BranchRepository branchRepository;
    private final FileUtil fileUtil;

    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeCardRepository employeeCardRepository, BranchRepository branchRepository, FileUtil fileUtil, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeCardRepository = employeeCardRepository;
        this.branchRepository = branchRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return this.fileUtil.readFile(EMPLOYEE_XML_FILE_PATH);
    }

    @Override
    public String importEmployees() throws JAXBException {
        StringBuilder importResult = new StringBuilder();

        EmployeeImportRootDto employeeImportRootDto = this.xmlParser
                .parseXml(EmployeeImportRootDto.class, EMPLOYEE_XML_FILE_PATH);

        for (EmployeeImportDto employeeImportDto : employeeImportRootDto.getEmployeeImportDtos()) {
            if (!this.validationUtil.isValid(employeeImportDto)) {
                importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            EmployeeCard employeeCardEntity = this.employeeCardRepository
                    .findByNumber(employeeImportDto.getCard())
                    .orElse(null);

            Branch branchEntity = this.branchRepository
                    .findByName(employeeImportDto.getBranch())
                    .orElse(null);

            if (employeeCardEntity == null || branchEntity == null) {
                importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Employee employeeEntity = this.modelMapper.map(employeeImportDto, Employee.class);
            employeeEntity.setEmployeeCard(employeeCardEntity);
            employeeEntity.setBranch(branchEntity);

            this.employeeRepository.saveAndFlush(employeeEntity);

            importResult
                    .append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE
                            , employeeEntity.getClass().getSimpleName()
                            , String.format("%s %s"
                                    , employeeEntity.getFirstName()
                                    , employeeEntity.getFirstName())))
                    .append(System.lineSeparator());
        }

        return importResult.toString();
    }

    @Override
    public String exportProductiveEmployees() {
        List<Employee> employees = this.employeeRepository.productiveEmployees();

        StringBuilder exportResult = new StringBuilder();

        for (Employee employee : employees) {
            exportResult
                    .append(String.format("Name: %s %s"
                            , employee.getFirstName()
                            , employee.getLastName()
                            , employee.getEmployeeCard()))
                    .append(System.lineSeparator())
                    .append(String.format("Position: %s", employee.getPosition()))
                    .append(System.lineSeparator())
                    .append(String.format("Card Number: %s", employee.getEmployeeCard().getNumber()))
                    .append(System.lineSeparator())
                    .append("-------------------------")
                    .append(System.lineSeparator());
        }

        return exportResult.toString();
    }
}
