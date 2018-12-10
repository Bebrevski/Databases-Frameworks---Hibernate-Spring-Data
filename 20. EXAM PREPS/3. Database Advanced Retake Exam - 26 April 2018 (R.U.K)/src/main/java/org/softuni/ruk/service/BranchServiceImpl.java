package org.softuni.ruk.service;

import org.softuni.ruk.repository.BranchRepository;
import org.softuni.ruk.service.interfaces.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public String importBranches(String branchesFileContent) {
        return null;
    }
}
