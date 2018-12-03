package judgesystem.service;

import com.google.gson.Gson;
import judgesystem.domain.dto.ContestImportDto;
import judgesystem.domain.entities.Category;
import judgesystem.domain.entities.Contest;
import judgesystem.repository.ContestRepository;
import judgesystem.util.FileUtil;
import judgesystem.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.io.IOException;

@Service
public class ContestServiceImpl implements ContestService{

    private final ContestRepository contestRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public ContestServiceImpl(ContestRepository contestRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.contestRepository = contestRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public String importContests(String contestsFilePath) throws IOException {
        String contestsFileContent = this.fileUtil.readFile(contestsFilePath);

        ContestImportDto[] contestImportDtos = this.gson.fromJson(contestsFileContent, ContestImportDto[].class);

        StringBuilder sb = new StringBuilder();

        for (ContestImportDto contestImportDto : contestImportDtos) {
            if (!this.validationUtil.isValid(contestImportDto)) {
                for (ConstraintViolation<ContestImportDto> violation : this.validationUtil.violations(contestImportDto)) {
                    sb
                            .append(violation.getMessage())
                            .append(System.lineSeparator());
                }
                sb.append("Invalid data!").append(System.lineSeparator());
                continue;
            }

            Contest entity = this.modelMapper.map(contestImportDto, Contest.class);

            //Strategies found in contest.json. Not described what to do with them.
        }

        return sb.toString().trim();
    }
}
