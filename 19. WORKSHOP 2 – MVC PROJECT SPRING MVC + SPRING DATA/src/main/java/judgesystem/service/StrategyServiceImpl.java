package judgesystem.service;

import com.google.gson.Gson;
import judgesystem.domain.dto.StrategyImportDto;
import judgesystem.domain.entities.Strategy;
import judgesystem.repository.StrategyRepository;
import judgesystem.util.FileUtil;
import judgesystem.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class StrategyServiceImpl implements StrategyService {

    private final StrategyRepository strategyRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public StrategyServiceImpl(StrategyRepository strategyRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.strategyRepository = strategyRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public String importStrategies(String strategiesFilePath) throws IOException {
        StringBuilder sb = new StringBuilder();

        String strategyFileContent = this.fileUtil.readFile(strategiesFilePath);

        StrategyImportDto[] strategyImportDtos = this.gson.fromJson(strategyFileContent,StrategyImportDto[].class);

        for (StrategyImportDto strategyImportDto : strategyImportDtos) {
            if (!this.validationUtil.isValid(strategyImportDto)) {
                sb.append("Invalid data!").append(System.lineSeparator());
                continue;
            }

            Strategy entity = this.modelMapper.map(strategyImportDto, Strategy.class);

            this.strategyRepository.saveAndFlush(entity);
        }

        return sb.toString();
    }
}
