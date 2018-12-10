package org.softuni.ruk.service;

import org.softuni.ruk.repository.BankAccountRepository;
import org.softuni.ruk.service.interfaces.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public String importBankAccounts(String bankAccountsFileContent) {
        return null;
    }
}
