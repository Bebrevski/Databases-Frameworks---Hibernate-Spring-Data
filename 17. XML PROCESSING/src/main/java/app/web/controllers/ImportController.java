package app.web.controllers;

import app.domain.dtos.car.CarImportRootDto;
import app.domain.dtos.cutomer.CustomerImportRootDto;
import app.domain.dtos.part.PartImportRootDto;
import app.domain.dtos.supplier.SupplierImportRootDto;
import app.services.car.CarService;
import app.services.customer.CustomerService;
import app.services.part.PartService;
import app.services.supplier.SupplierService;
import app.util.XmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

@Controller
public class ImportController {

    private final static String SUPPLIER_XML_FILE_PATH = "E:\\SoftUni\\Databases Frameworks - Hibernate & Spring Data\\17. XML PROCESSING\\src\\main\\resources\\files\\suppliers.xml";
    private static final String PART_XML_FILE_PATH = "E:\\SoftUni\\Databases Frameworks - Hibernate & Spring Data\\17. XML PROCESSING\\src\\main\\resources\\files\\parts.xml";
    private static final String CAR_XML_FILE_PATH = "E:\\SoftUni\\Databases Frameworks - Hibernate & Spring Data\\17. XML PROCESSING\\src\\main\\resources\\files\\cars.xml";
    private static final String CUSTOMER_XML_FILE_PATH = "E:\\SoftUni\\Databases Frameworks - Hibernate & Spring Data\\17. XML PROCESSING\\src\\main\\resources\\files\\customers.xml";

    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final XmlParser xmlParser;

    @Autowired
    public ImportController(SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, XmlParser xmlParser) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.xmlParser = xmlParser;
    }

    public String importSuppliers() throws JAXBException, FileNotFoundException {
        SupplierImportRootDto supplierImportRootDto = this.xmlParser
                .parseXml(SupplierImportRootDto.class, SUPPLIER_XML_FILE_PATH);

        this.supplierService.importSuppliers(supplierImportRootDto);

        return "Imported Suppliers";
    }

    public String importParts() throws JAXBException, FileNotFoundException {
        PartImportRootDto partImportRootDto = this.xmlParser
                .parseXml(PartImportRootDto.class, PART_XML_FILE_PATH);

        this.partService.importParts(partImportRootDto);

        return "Imported Parts";
    }

    public String importCars() throws JAXBException, FileNotFoundException {
        CarImportRootDto carImportRootDto = this.xmlParser
                .parseXml(CarImportRootDto.class, CAR_XML_FILE_PATH);

        this.carService.importCars(carImportRootDto);

        return "Imported Suppliers";
    }

    public String importCustomers() throws JAXBException, FileNotFoundException {
        CustomerImportRootDto customerImportRootDto = this.xmlParser
                .parseXml(CustomerImportRootDto.class, CUSTOMER_XML_FILE_PATH);

        this.customerService.importCustomers(customerImportRootDto);

        return "Imported Customers";
    }
}
