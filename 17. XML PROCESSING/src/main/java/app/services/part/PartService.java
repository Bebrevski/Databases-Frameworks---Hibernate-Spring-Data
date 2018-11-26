package app.services.part;

import app.domain.dtos.part.PartImportRootDto;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface PartService {
    void importParts(PartImportRootDto partImportRootDto) throws JAXBException, FileNotFoundException;
}
