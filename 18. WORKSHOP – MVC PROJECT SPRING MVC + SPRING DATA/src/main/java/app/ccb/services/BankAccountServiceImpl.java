package app.ccb.services;

import app.ccb.repositories.BankAccountRepository;
import app.ccb.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private static final String BANK_ACCOUNT_XML_FILE_PATH = "E:\\SoftUni\\Databases Frameworks - Hibernate & Spring Data\\18. WORKSHOP – MVC PROJECT SPRING MVC + SPRING DATA\\src\\main\\resources\\files\\xml\\bank-accounts.xml";

    private final BankAccountRepository bankAccountRepository;
    private final FileUtil fileUtil;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, FileUtil fileUtil) {
        this.bankAccountRepository = bankAccountRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public Boolean bankAccountsAreImported() {
        return this.bankAccountRepository.count() != 0;
    }

    @Override
    public String readBankAccountsXmlFile() throws IOException {
        return this.fileUtil.readFile(BANK_ACCOUNT_XML_FILE_PATH);
    }

    @Override
    public String importBankAccounts() {
        // TODO : Implement Me
        return null;
    }
}
