package hiberspring.service;

import com.google.gson.Gson;
import hiberspring.common.Constants;
import hiberspring.domain.dtos.BranchImportDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Town;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.TownRepository;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BranchServiceImpl implements BranchService {

    private static final String BRANCHES_JSON_FILE_PATH = Constants.PATH_TO_FILES + "branches.json";

    private final BranchRepository branchRepository;
    private final TownRepository townRepository;
    private final FileUtil fileUtil;

    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository, TownRepository townRepository, FileUtil fileUtil, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.branchRepository = branchRepository;
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean branchesAreImported() {
        return this.branchRepository.count() > 0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return this.fileUtil.readFile(BRANCHES_JSON_FILE_PATH);
    }

    @Override
    public String importBranches(String branchesFileContent) {
        StringBuilder importResult = new StringBuilder();

        BranchImportDto[] branchImportDtos = this.gson.fromJson(branchesFileContent, BranchImportDto[].class);


        for (BranchImportDto branchImportDto : branchImportDtos) {
            if (!this.validationUtil.isValid(branchImportDto)) {
                importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Town townEntity = this.townRepository
                    .findByName(branchImportDto.getTown())
                    .orElse(null);

            if(townEntity == null) {
                importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Branch branchEntity = new Branch();
            branchEntity.setName(branchImportDto.getName());
            branchEntity.setTown(townEntity);

            this.branchRepository.saveAndFlush(branchEntity);

            importResult
                    .append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE
                            , branchEntity.getClass().getSimpleName()
                            , branchEntity.getName()))
                    .append(System.lineSeparator());
        }

        return importResult.toString();
    }
}
