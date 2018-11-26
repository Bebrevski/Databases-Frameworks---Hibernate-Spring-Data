package app.services.part;

import app.domain.dtos.part.PartImportDto;
import app.domain.dtos.part.PartImportRootDto;
import app.domain.entities.Part;
import app.domain.entities.Supplier;
import app.repositories.PartRepository;
import app.repositories.SupplierRepository;
import app.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.Random;

@Service
public class PartServiceImpl implements PartService{
    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    private final SupplierRepository supplierRepository;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper, ValidationUtil validationUtil, SupplierRepository supplierRepository) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public void importParts(PartImportRootDto partImportRootDto) throws JAXBException, FileNotFoundException {
        for (PartImportDto partImportDto : partImportRootDto.getPartImportDtos()) {
            if (!this.validationUtil.isValid(partImportDto)) {
                System.out.println("Not valid data!");

                continue;
            }

            Part entity = this.modelMapper.map(partImportDto, Part.class);
            entity.setSupplier(this.getRandomSupplier());

            this.partRepository.saveAndFlush(entity);
        }
    }

    private Supplier getRandomSupplier(){
        Random random = new Random();

        return this.supplierRepository
                .findById((long) (random.nextInt((int) (this.supplierRepository.count() - 1)) + 1))
                .orElse(null);
    }
}
