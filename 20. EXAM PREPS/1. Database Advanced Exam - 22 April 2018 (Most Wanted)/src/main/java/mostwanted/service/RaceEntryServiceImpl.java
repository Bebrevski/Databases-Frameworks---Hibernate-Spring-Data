package mostwanted.service;

import mostwanted.common.Constants;
import mostwanted.domain.dtos.raceEntries.RaceEntryImportDto;
import mostwanted.domain.dtos.raceEntries.RaceEntryImportRootDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class RaceEntryServiceImpl implements RaceEntryService {

    private static final String RACE_ENTRIES_XML_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/races-entries.xml";

    private final RaceEntryRepository raceEntryRepository;
    private final FileUtil fileUtil;

    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    private final CarRepository carRepository;
    private final RacerRepository racerRepository;

    @Autowired
    public RaceEntryServiceImpl(RaceEntryRepository raceEntryRepository, FileUtil fileUtil, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper, CarRepository carRepository, RacerRepository racerRepository) {
        this.raceEntryRepository = raceEntryRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.carRepository = carRepository;
        this.racerRepository = racerRepository;
    }

    @Override
    public Boolean raceEntriesAreImported() {
        return this.raceEntryRepository.count() > 0;
    }

    @Override
    public String readRaceEntriesXmlFile() throws IOException {
        return this.fileUtil.readFile(RACE_ENTRIES_XML_FILE_PATH);
    }

    @Override
    public String importRaceEntries() throws JAXBException {
        StringBuilder importResult = new StringBuilder();

        RaceEntryImportRootDto raceEntryImportRootDto = this.xmlParser
                .parseXml(RaceEntryImportRootDto.class, RACE_ENTRIES_XML_FILE_PATH);

        for (RaceEntryImportDto raceEntryImportDto : raceEntryImportRootDto.getRaceEntryImportDtos()) {
            Car carEntry = this.carRepository
                    .findById((long) raceEntryImportDto.getCarId())
                    .orElse(null);

            Racer racerEntity = this.racerRepository
                    .findByName(raceEntryImportDto.getRacer())
                    .orElse(null);

            if (!this.validationUtil.isValid(raceEntryImportDto) || racerEntity == null || carEntry == null) {
                importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            RaceEntry raceEntryEntity = this.modelMapper.map(raceEntryImportDto, RaceEntry.class);
            raceEntryEntity.setCar(carEntry);
            raceEntryEntity.setRacer(racerEntity);
            raceEntryEntity.setRace(null);

            raceEntryEntity = this.raceEntryRepository.saveAndFlush(raceEntryEntity);

            importResult
                    .append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE
                            , raceEntryEntity.getClass().getSimpleName()
                            , raceEntryEntity.getId()))
                    .append(System.lineSeparator());
        }

        return importResult.toString();
    }
}
