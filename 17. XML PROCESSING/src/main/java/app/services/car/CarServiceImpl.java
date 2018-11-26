package app.services.car;

import app.domain.dtos.car.CarImportDto;
import app.domain.dtos.car.CarImportRootDto;
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
