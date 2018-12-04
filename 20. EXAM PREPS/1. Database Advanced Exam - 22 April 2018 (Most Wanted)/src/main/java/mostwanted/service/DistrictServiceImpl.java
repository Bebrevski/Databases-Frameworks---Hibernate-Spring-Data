package mostwanted.service;

import mostwanted.repository.DistrictRepository;
import mostwanted.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DistrictServiceImpl implements DistrictService{

    private static final String DISTRICTS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/districts.json";

    private final DistrictRepository districtRepository;
    private final FileUtil fileUtil;

    @Autowired
    public DistrictServiceImpl(DistrictRepository districtRepository, FileUtil fileUtil) {
        this.districtRepository = districtRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public Boolean districtsAreImported() {
        return this.districtRepository.count() > 0;
    }

    @Override
    public String readDistrictsJsonFile() throws IOException {
        return this.fileUtil.readFile(DISTRICTS_JSON_FILE_PATH);
    }

    @Override
    public String importDistricts(String districtsFileContent) {
        return null;
    }
}
