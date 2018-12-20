package hiberspring.service;

import com.google.gson.Gson;
import hiberspring.common.Constants;
import hiberspring.domain.dtos.EmployeeCardImportDto;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmployeeCardServiceImpl implements EmployeeCardService {

    private static final String EMPLOYEE_CARD_JSON_FILE_PATH = Constants.PATH_TO_FILES + "employee-cards.json";

    private final EmployeeCardRepository employeeCardRepository;
    private final FileUtil fileUtil;

    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeCardServiceImpl(EmployeeCardRepository employeeCardRepository, FileUtil fileUtil, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.employeeCardRepository = employeeCardRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean employeeCardsAreImported() {
        return this.employeeCardRepository.count() > 0;
    }

    @Override
    public String readEmployeeCardsJsonFile() throws IOException {
        return this.fileUtil.readFile(EMPLOYEE_CARD_JSON_FILE_PATH);
    }

    @Override
    public String importEmployeeCards(String employeeCardsFileContent) {
        StringBuilder importResult = new StringBuilder();

        EmployeeCardImportDto[] employeeCardImportDtos = this.gson.fromJson(employeeCardsFileContent, EmployeeCardImportDto[].class);

        for (EmployeeCardImportDto employeeCardImportDto : employeeCardImportDtos) {
            if (!this.validationUtil.isValid(employeeCardImportDto)) {
                importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            EmployeeCard employeeCardEntity = this.employeeCardRepository
                    .findByNumber(employeeCardImportDto.getNumber())
                    .orElse(null);

            if (employeeCardEntity != null) {
                importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            employeeCardEntity = this.modelMapper.map(employeeCardImportDto, EmployeeCard.class);

            this.employeeCardRepository.saveAndFlush(employeeCardEntity);

            importResult
                    .append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE
                            , "Employee Card"
                            , employeeCardEntity.getNumber()))
                    .append(System.lineSeparator());
        }

        return importResult.toString();
    }
}
