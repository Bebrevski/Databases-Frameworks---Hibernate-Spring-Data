package app.services.supplier;

import app.domain.dtos.supplier.SupplierImportDto;
import app.domain.dtos.supplier.SupplierImportRootDto;
import app.domain.entities.Supplier;
import app.repositories.SupplierRepository;
import app.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void importSuppliers(SupplierImportRootDto supplierImportRootDto) {
        for (SupplierImportDto supplierImportDto : supplierImportRootDto.getSupplierImportDtos()) {
            if (!this.validationUtil.isValid(supplierImportDto)) {
                System.out.println("Not valid data!");

                continue;
            }

            Supplier entity = this.modelMapper.map(supplierImportDto, Supplier.class);

            this.supplierRepository.saveAndFlush(entity);
        }
    }
}
