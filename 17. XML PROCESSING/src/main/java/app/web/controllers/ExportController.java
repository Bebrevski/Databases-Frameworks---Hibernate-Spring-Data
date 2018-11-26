package app.web.controllers;

import app.domain.dtos.car.CarExportRootDto;
import app.services.car.CarService;
import app.util.XmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.xml.bind.JAXBException;

@Controller
public class ExportController {
    private static final String CAR_EXPORT_XML_PATH = "E:\\SoftUni\\Databases Frameworks - Hibernate & Spring Data\\17. XML PROCESSING\\src\\main\\resources\\files\\output\\cars-and-parts.xml";
    private final CarService carService;
    private final XmlParser xmlParser;

    @Autowired
    public ExportController(CarService carService, XmlParser xmlParser) {
        this.carService = carService;
        this.xmlParser = xmlParser;
    }

    public String exportCars() throws JAXBException {
        CarExportRootDto carExportRootDto = this.carService.exportCars();

        this.xmlParser.exportXml(carExportRootDto, CarExportRootDto.class, CAR_EXPORT_XML_PATH);

        return "Exported successfully";
    }
}
