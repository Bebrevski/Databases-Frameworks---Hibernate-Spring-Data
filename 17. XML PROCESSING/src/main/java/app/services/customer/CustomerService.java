package app.services.customer;

import app.domain.dtos.cutomer.CustomerImportRootDto;

public interface CustomerService {
    void importCustomers(CustomerImportRootDto customerImportRootDto);
}
