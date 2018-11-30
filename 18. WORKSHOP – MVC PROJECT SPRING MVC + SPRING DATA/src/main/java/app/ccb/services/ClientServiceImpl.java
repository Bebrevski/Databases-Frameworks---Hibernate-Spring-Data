package app.ccb.services;

import app.ccb.domain.dtos.ClientImportDto;
import app.ccb.domain.entities.Card;
import app.ccb.domain.entities.Client;
import app.ccb.domain.entities.Employee;
import app.ccb.repositories.ClientRepository;
import app.ccb.repositories.EmployeeRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private static final String CLIENTS_JSON_FILE_PATH = "E:\\SoftUni\\Databases Frameworks - Hibernate & Spring Data\\18. WORKSHOP – MVC PROJECT SPRING MVC + SPRING DATA\\src\\main\\resources\\files\\json\\clients.json";

    private final ClientRepository clientRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, FileUtil fileUtil, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper, EmployeeRepository employeeRepository) {
        this.clientRepository = clientRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Boolean clientsAreImported() {
        return this.clientRepository.count() != 0;
    }

    @Override
    public String readClientsJsonFile() throws IOException {
        return this.fileUtil.readFile(CLIENTS_JSON_FILE_PATH);
    }

    @Override
    public String importClients(String clients) {
        StringBuilder sb = new StringBuilder();

        ClientImportDto[] clientImportDtos = this.gson
                .fromJson(clients, ClientImportDto[].class);

        for (ClientImportDto clientImportDto : clientImportDtos) {
            if (!this.validationUtil.isValid(clientImportDto)) {
                sb.append("Incorrect data!").append(System.lineSeparator());
                continue;
            }

            Employee employeeEntity = this.employeeRepository
                    .findByFullName(clientImportDto.getAppointedEmployee())
                    .orElse(null);

            if (employeeEntity == null) {
                sb.append("Incorrect data!").append(System.lineSeparator());
                continue;
            }

            Client clientCheck = this.clientRepository.findByFullName(String.format("%s %s"
                    , clientImportDto.getFirstName()
                    , clientImportDto.getLastName()))
                    .orElse(null);

            if (clientCheck != null) {
                sb.append("Incorrect data!").append(System.lineSeparator());
                continue;
            }

            Client clientEntity = this.modelMapper.map(clientImportDto, Client.class);
            clientEntity.setFullName(String.format("%s %s"
                    , clientImportDto.getFirstName()
                    , clientImportDto.getLastName()));
            clientEntity.getEmployees().add(employeeEntity);

            this.clientRepository.saveAndFlush(clientEntity);

            sb
                    .append(String.format("Successfully imported Client - %s"
                            , clientEntity.getFullName()))
                    .append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public String exportFamilyGuy() {
        Client clientEntity = this.clientRepository
                .exportFamilyGuy()
                .stream()
                .findFirst()
                .orElse(null);

        StringBuilder sb = new StringBuilder();

        sb
                .append(String.format("Full Name: %s", clientEntity.getFullName()))
                .append(System.lineSeparator())
                .append(String.format("Age: %d", clientEntity.getAge()))
                .append(System.lineSeparator())
                .append(String.format("Bank Account: %s", clientEntity.getBankAccount().getAccountNumber()))
                .append(System.lineSeparator());

        for (Card card : clientEntity.getBankAccount().getCards()) {
            sb
                    .append(String.format("\tCard Number: %s", card.getCardNumber()))
                    .append(System.lineSeparator())
                    .append(String.format("\tCard Status: %s", card.getCardStatus()))
                    .append(System.lineSeparator());
        }

        return sb.toString();
    }
}
