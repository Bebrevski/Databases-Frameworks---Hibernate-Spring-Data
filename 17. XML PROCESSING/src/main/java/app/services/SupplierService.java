package app.services;

import app.domain.dtos.supplier.SupplierImportRootDto;

public interface SupplierService {

    void importSuppliers(SupplierImportRootDto supplierImportRootDto);
}
