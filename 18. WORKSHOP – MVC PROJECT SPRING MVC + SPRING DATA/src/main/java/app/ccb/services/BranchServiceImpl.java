package app.ccb.services;

import app.ccb.domain.dtos.BranchImportDto;
import app.ccb.domain.entities.Branch;
import app.ccb.repositories.BranchRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BranchServiceImpl implements BranchService {

    private static final String BRANCHES_JSON_FILE_PATH = "E:\\SoftUni\\Databases Frameworks - Hibernate & Spring Data\\18. WORKSHOP – MVC PROJECT SPRING MVC + SPRING DATA\\src\\main\\resources\\files\\json\\branches.json";

    private final BranchRepository branchRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository, FileUtil fileUtil, ValidationUtil validationUtil, Gson gson, ModelMapper modelMapper) {
        this.branchRepository = branchRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean branchesAreImported() {
        return this.branchRepository.count() != 0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        String branchesFileContent = this.fileUtil.readFile(BRANCHES_JSON_FILE_PATH);

        return branchesFileContent;
    }

    @Override
    public String importBranches(String branchesJson) {
        StringBuilder sb = new StringBuilder();

        BranchImportDto[] branchImportDtos = this.gson
                .fromJson(branchesJson, BranchImportDto[].class);

        for (BranchImportDto branchImportDto : branchImportDtos) {
            if (!this.validationUtil.isValid(branchImportDto)) {
                sb.append("Incorrect Data!").append(System.lineSeparator());

                continue;
            }

            Branch entity = this.modelMapper.map(branchImportDto, Branch.class);
            this.branchRepository.saveAndFlush(entity);

            sb
                    .append(String.format("Successfully imported Branch - %s"
                            , entity.getName()))
                    .append(System.lineSeparator());
        }


        return sb.toString().trim();
    }
}
