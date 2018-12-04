package mostwanted.service;

import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TownServiceImpl implements TownService {

    private static final String TOWNS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/towns.json";

    private final TownRepository townRepository;
    private final FileUtil fileUtil;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, FileUtil fileUtil) {
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public Boolean townsAreImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return this.fileUtil.readFile(TOWNS_JSON_FILE_PATH);
    }

    @Override
    public String importTowns(String townsFileContent) {
        return null;
    }

    @Override
    public String exportRacingTowns() {
        return null;
    }
}
