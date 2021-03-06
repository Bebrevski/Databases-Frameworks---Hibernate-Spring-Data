package app.services.car;

import app.domain.dtos.car.CarExportRootDto;
import app.domain.dtos.car.CarImportRootDto;

public interface CarService {
    void importCars(CarImportRootDto carImportRootDto);

    CarExportRootDto exportCars();
}
