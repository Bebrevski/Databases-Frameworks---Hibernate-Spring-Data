package mostwanted.service;

import mostwanted.repository.RaceRepository;
import mostwanted.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RaceServiceImpl implements RaceService{

    private static final String RACES_XML_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/races.xml";

    private final RaceRepository raceRepository;
    private final FileUtil fileUtil;

    @Autowired
    public RaceServiceImpl(RaceRepository raceRepository, FileUtil fileUtil) {
        this.raceRepository = raceRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public Boolean racesAreImported() {
        return this.raceRepository.count() > 0;
    }

    @Override
    public String readRacesXmlFile() throws IOException {
        return this.fileUtil.readFile(RACES_XML_FILE_PATH);
    }

    @Override
    public String importRaces() {
        return null;
    }
}
