package mostwanted.service;

import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RacerServiceImpl implements RacerService{

    private static final String RACERS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/racers.json";

    private final RacerRepository racerRepository;
    private final FileUtil fileUtil;

    @Autowired
    public RacerServiceImpl(RacerRepository racerRepository, FileUtil fileUtil) {
        this.racerRepository = racerRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public Boolean racersAreImported() {
        return this.racerRepository.count() > 0;
    }

    @Override
    public String readRacersJsonFile() throws IOException {
        return this.fileUtil.readFile(RACERS_JSON_FILE_PATH);
    }

    @Override
    public String importRacers(String racersFileContent) {
        return null;
    }

    @Override
    public String exportRacingCars() {
        return null;
    }
}
