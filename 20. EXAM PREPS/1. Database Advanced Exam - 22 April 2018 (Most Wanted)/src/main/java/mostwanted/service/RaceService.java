package mostwanted.service;

import java.io.IOException;

public interface RaceService {

    Boolean racesAreImported();

    String readRacesXmlFile() throws IOException;

    String importRaces();
}
