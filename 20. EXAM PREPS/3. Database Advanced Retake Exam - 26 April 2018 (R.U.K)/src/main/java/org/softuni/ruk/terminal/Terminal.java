package org.softuni.ruk.terminal;

import org.softuni.ruk.service.interfaces.BranchService;
import org.softuni.ruk.service.interfaces.ClientService;
import org.softuni.ruk.service.interfaces.EmployeeService;
import org.softuni.ruk.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class Terminal implements CommandLineRunner {

    private static final String BRANCHES_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/json/input/branches.json";
    private static final String EMPLOYEES_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/json/input/employees.json";
    private static final String CLIENTS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/json/input/clients.json";

    private BranchService branchService;
    private EmployeeService employeeService;
    private ClientService clientService;

    private final FileUtil fileUtil;

    @Autowired
    public Terminal(BranchService branchService, EmployeeService employeeService, ClientService clientService, FileUtil fileUtil) {
        this.branchService = branchService;
        this.employeeService = employeeService;
        this.clientService = clientService;
        this.fileUtil = fileUtil;
    }

    @Override
    public void run(String... args) throws Exception {
//        String branchFileContent = this.fileUtil.readFile(BRANCHES_JSON_FILE_PATH);
//        System.out.println(this.branchService.importBranches(branchFileContent));

//        String employeeFileContent = this.fileUtil.readFile(EMPLOYEES_JSON_FILE_PATH);
//        System.out.println(this.employeeService.importEmployees(employeeFileContent));

//        String clientFileContent = this.fileUtil.readFile(CLIENTS_JSON_FILE_PATH);
//        System.out.println(this.clientService.importClients(clientFileContent));

    }
}
