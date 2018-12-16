package org.softuni.ruk.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.softuni.ruk.domain.dto.BranchImportJsonDto;
import org.softuni.ruk.domain.entity.Branch;
import org.softuni.ruk.repository.BranchRepository;
import org.softuni.ruk.service.interfaces.BranchService;
import org.softuni.ruk.util.FileUtil;
import org.softuni.ruk.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.branchRepository = branchRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public String importBranches(String branchesFileContent) {
        StringBuilder importResult = new StringBuilder();

        BranchImportJsonDto[] branchImportJsonDtos = this.gson.fromJson(branchesFileContent, BranchImportJsonDto[].class);

        for (BranchImportJsonDto branchImportJsonDto : branchImportJsonDtos) {
            if (!this.validationUtil.isValid(branchImportJsonDto)) {
                importResult.append("Error: Invalid data!").append(System.lineSeparator());
                continue;
            }

            Branch branchEntity = this.modelMapper.map(branchImportJsonDto, Branch.class);

            this.branchRepository.saveAndFlush(branchEntity);

            importResult.append(String.format("Successfully imported %s - %s"
                    , branchEntity.getClass().getSimpleName()
                    , branchEntity.getName()))
                    .append(System.lineSeparator());
        }

        return importResult.toString();
    }
}
