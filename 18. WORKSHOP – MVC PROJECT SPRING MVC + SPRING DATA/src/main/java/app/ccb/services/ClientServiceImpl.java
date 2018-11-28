package app.ccb.services;

import app.ccb.repositories.ClientRepository;
import app.ccb.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ClientServiceImpl implements ClientService {

    private static final String CLIENTS_JSON_FILE_PATH = "E:\\SoftUni\\Databases Frameworks - Hibernate & Spring Data\\18. WORKSHOP – MVC PROJECT SPRING MVC + SPRING DATA\\src\\main\\resources\\files\\json\\clients.json";

    private final ClientRepository clientRepository;
    private final FileUtil fileUtil;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, FileUtil fileUtil) {
        this.clientRepository = clientRepository;
        this.fileUtil = fileUtil;
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
        // TODO : Implement Me
        return null;
    }

    @Override
    public String exportFamilyGuy() {
        // TODO : Implement Me
        return null;
    }
}
