package org.softuni.ruk.service;

import org.softuni.ruk.repository.ClientRepository;
import org.softuni.ruk.service.interfaces.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public String importClients(String clientsFileContent) {
        return null;
    }
}
