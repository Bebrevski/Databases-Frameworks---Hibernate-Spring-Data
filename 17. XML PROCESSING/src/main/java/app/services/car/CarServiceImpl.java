package app.services.car;

import app.domain.dtos.car.CarExportDto;
import app.domain.dtos.car.CarExportRootDto;
import app.domain.dtos.car.CarImportDto;
import app.domain.dtos.car.CarImportRootDto;
import app.domain.dtos.part.PartExportDto;
import app.domain.dtos.part.PartExportRootDto;
import app.domain.entities.Car;
import app.domain.entities.Part;
import app.repositories.CarRepository;
import app.repositories.PartRepository;
import app.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final PartRepository partRepository;

    public CarServiceImpl(CarRepository carRepository, ValidationUtil validationUtil, ModelMapper modelMapper, PartRepository partRepository) {
        this.carRepository = carRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.partRepository = partRepository;
    }

    @Override
    public void importCars(CarImportRootDto carImportRootDto) {
        for (CarImportDto carImportDto : carImportRootDto.getCarImportDtos()) {
            if (!this.validationUtil.isValid(carImportDto)) {
                System.out.println("Invalid data!");

                continue;
            }

            Car entity = this.modelMapper.map(carImportDto, Car.class);

            entity.setParts(this.getRandomParts());

            this.carRepository.saveAndFlush(entity);
        }
    }

    @Override
    public CarExportRootDto exportCars() {
        List<Car> carEntities = this.carRepository.findAll();
        List<CarExportDto> carExportDtos = new ArrayList<>();

        for (Car carEntity : carEntities) {
            CarExportDto carExportDto = this.modelMapper.map(carEntity, CarExportDto.class);

            List<PartExportDto> partExportDtos = new ArrayList<>();

            for (Part part : carEntity.getParts()) {
                PartExportDto partExportDto = this.modelMapper.map(part, PartExportDto.class);

                partExportDtos.add(partExportDto);
            }

            PartExportRootDto partExportRootDto = new PartExportRootDto();
            partExportRootDto.setPartExportDtos(partExportDtos);

            carExportDto.setPartExportRootDto(partExportRootDto);

            carExportDtos.add(carExportDto);
        }

        CarExportRootDto carExportRootDto = new CarExportRootDto();
        carExportRootDto.setCarExportDtos(carExportDtos);
        return carExportRootDto;
    }

    private List<Part> getRandomParts() {
        List<Part> parts = new ArrayList<>();
        Random random = new Random();

        List<Part> partEntities = this.partRepository.findAll();

        int length = random.nextInt(10) + 10;
        for (int i = 0; i < length; i++) {
            parts.add(partEntities.get(random.nextInt(partEntities.size() - 1) + 1));
        }

        return parts;
    }
}
