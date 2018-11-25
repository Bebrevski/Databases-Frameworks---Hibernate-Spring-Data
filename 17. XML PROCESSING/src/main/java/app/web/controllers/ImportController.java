package app.web.controllers;

import app.domain.dtos.supplier.SupplierImportRootDto;
import app.services.SupplierService;
import app.util.XmlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

@Controller
public class ImportController {

    private final static String SUPPLIER_XML_FILE_PATH = "E:\\SoftUni\\Databases Frameworks - Hibernate & Spring Data\\17. XML PROCESSING\\src\\main\\resources\\files\\suppliers.xml";

    private final SupplierService supplierService;
    private final XmlParser xmlParser;

    @Autowired
    public ImportController(SupplierService supplierService, XmlParser xmlParser) {
        this.supplierService = supplierService;
        this.xmlParser = xmlParser;
    }

    public String importSuppliers() throws JAXBException, FileNotFoundException {
        SupplierImportRootDto supplierImportRootDto = this.xmlParser.parseXml(SupplierImportRootDto.class, SUPPLIER_XML_FILE_PATH);


        return "Imported Suppliers";
    }
}
