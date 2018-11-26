package app.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController implements CommandLineRunner {

    private final ImportController importController;
    private final ExportController exportController;

    @Autowired
    protected BaseController(ImportController importController, ExportController exportController) {
        this.importController = importController;
        this.exportController = exportController;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(this.importController.importSuppliers());
        System.out.println(this.importController.importParts());
        System.out.println(this.importController.importCars());
        System.out.println(this.importController.importCustomers());

        System.out.println(this.exportController.exportCars());
    }
}
