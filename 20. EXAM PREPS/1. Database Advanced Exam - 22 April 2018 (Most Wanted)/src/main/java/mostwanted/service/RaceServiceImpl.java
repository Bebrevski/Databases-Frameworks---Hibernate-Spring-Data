package mostwanted.service;

import mostwanted.common.Constants;
import mostwanted.domain.dtos.RacerImportDto;
import mostwanted.domain.dtos.races.EntryImportDto;
import mostwanted.domain.dtos.races.RaceImportDto;
import mostwanted.domain.dtos.races.RaceImportRootDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Race;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RaceRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class RaceServiceImpl implements RaceService {

    private static final String RACES_XML_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/races.xml";

    private final RaceRepository raceRepository;
    private final FileUtil fileUtil;

    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    private final DistrictRepository districtRepository;
    private final RaceEntryRepository raceEntryRepository;

    @Autowired
    public RaceServiceImpl(RaceRepository raceRepository, FileUtil fileUtil, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper, DistrictRepository districtRepository, RaceEntryRepository raceEntryRepository) {
        this.raceRepository = raceRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.districtRepository = districtRepository;
        this.raceEntryRepository = raceEntryRepository;
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
    public String importRaces() throws JAXBException {
        StringBuilder importResult = new StringBuilder();

        RaceImportRootDto raceImportRootDto = this.xmlParser
                .parseXml(RaceImportRootDto.class, RACES_XML_FILE_PATH);

        for (RaceImportDto raceImportDto : raceImportRootDto.getRaceImportDtos()) {
            District districtEntity = this.districtRepository
                    .findByName(raceImportDto.getDistrictName())
                    .orElse(null);

            if (!this.validationUtil.isValid(raceImportDto) || districtEntity == null) {
                importResult.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Race raceEntity = this.modelMapper.map(raceImportDto, Race.class);
            raceEntity.setDistrict(districtEntity);

            Set<RaceEntry> raceEntries = new HashSet<>();
            for (EntryImportDto entryImportDto : raceImportDto.getEntryImportRootDto().getEntryImportDtos()) {
                RaceEntry raceEntryEntity = this.raceEntryRepository
                        .findById(entryImportDto.getId())
                        .orElse(null);

                if (raceEntryEntity == null) {
                    continue;
                }

                raceEntryEntity.setRace(raceEntity);
                raceEntries.add(raceEntryEntity);
            }

            Race race = this.raceRepository.saveAndFlush(raceEntity);
            this.raceEntryRepository.saveAll(raceEntries);

            importResult
                    .append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE
                            , race.getClass().getSimpleName()
                            , race.getId()))
                    .append(System.lineSeparator());
        }

        return importResult.toString().trim();
    }
}
