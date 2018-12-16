package org.softuni.ruk.service;

import com.google.gson.Gson;
import org.softuni.ruk.domain.dto.ClientImportJsonDto;
import org.softuni.ruk.domain.entity.Client;
import org.softuni.ruk.domain.entity.Employee;
import org.softuni.ruk.repository.ClientRepository;
import org.softuni.ruk.repository.EmployeeRepository;
import org.softuni.ruk.service.interfaces.ClientService;
import org.softuni.ruk.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, EmployeeRepository employeeRepository, Gson gson, ValidationUtil validationUtil) {
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public String importClients(String clientsFileContent) {
        StringBuilder importResult = new StringBuilder();

        ClientImportJsonDto[] clientImportJsonDtos = this.gson.fromJson(clientsFileContent, ClientImportJsonDto[].class);

        for (ClientImportJsonDto clientImportJsonDto : clientImportJsonDtos) {

            Employee employeeEntity = this.employeeRepository
                    .findByFirstNameAndLastName(
                            clientImportJsonDto.getAppointedEmployee().split("\\s+")[0],
                            clientImportJsonDto.getAppointedEmployee().split("\\s+")[1])
                    .orElse(null);

            if (!this.validationUtil.isValid(clientImportJsonDto) || employeeEntity == null) {
                importResult.append("Error: Invalid data!").append(System.lineSeparator());
                continue;
            }

            Client clientEntity = new Client();
            clientEntity.setFullName(clientImportJsonDto.getFirstName() + " " + clientImportJsonDto.getLastName());
            clientEntity.setAge(clientImportJsonDto.getAge());

            clientEntity = this.clientRepository.saveAndFlush(clientEntity);

            employeeEntity.getClients().add(clientEntity);

            this.employeeRepository.save(employeeEntity);

            importResult.append(String.format("Successfully imported %s - %s"
                    , clientEntity.getClass().getSimpleName()
                    , clientEntity.getFullName()))
                    .append(System.lineSeparator());
        }

        return importResult.toString();
    }
}
